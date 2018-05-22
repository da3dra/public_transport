/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.config.scheduling;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.provectus.public_transport.model.Route;
import ua.provectus.public_transport.model.Vehicle;
import ua.provectus.public_transport.model.VehicleState;
import ua.provectus.public_transport.model.stops.StoppingInfo;
import ua.provectus.public_transport.parser.Parser;
import ua.provectus.public_transport.repository.LastModifiedDao;
import ua.provectus.public_transport.repository.RouteDao;
import ua.provectus.public_transport.repository.StoppingsDao;
import ua.provectus.public_transport.repository.VehicleDao;
import ua.provectus.public_transport.repository.VehicleStateDao;

@Component
public class CacheScheduling {

    @Value("${force.cache}")
    private boolean forceCache;
    public static final int TTL = 3600 * 48 * 1000; //ttl in ms 
    public static final Logger logger = LoggerFactory.getLogger(CacheScheduling.class.getName());
    
    @Autowired
    RouteDao routeDao;
    @Autowired
    LastModifiedDao lastModifiedDao;
    @Autowired
    VehicleDao vehicleDao;
    @Autowired
    VehicleStateDao vehicleStateDao;
    @Autowired StoppingsDao stoppingsDao;
    
    @Scheduled(fixedDelay = 3600 * 1000) //each hour scheduler checks on ttl expired.
    private final void checkOnTTLExpired() throws InterruptedException {
        if (forceCache) {
            logger.info("Cache is empty. Forcing cache.");
            forceCache = false;
            try {
                updateRoutes(Parser.getRouteList(), new Date());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            logger.info("Cache updated.");
        }
        if (!lastModifiedDao.noLastModifiedDefined()) {
            lastModifiedDao.setLastModified(new Date());
            logger.info("\nLastModified successfully saved.");
            return;
        }
        logger.info("[Routes update started]");
        long lastUpdate = lastModifiedDao.getLastModified().getLastModified();
        Date currentDate = new Date();
        Date expectedDate = countExpectedDate(lastUpdate, TTL);
        
        if (!expectedDate.after(currentDate)) {
            try {
                List<Route> list = Parser.getRouteList();
                List<Route> dbList = routeDao.findAll();
                lastModifiedDao.updateLastModified(currentDate);
                if (list.equals(dbList)) {
                    updateRoutes(list, currentDate);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        logger.info("[Routes update finished successfully]");
    }
    @Scheduled(fixedDelay = 60 * 1000)
    private final void updateVehicleList() throws IOException{
        logger.info("[Updating vehicles list]");
        Collection<Vehicle> currentList = vehicleDao.findAll();
        Collection<Vehicle> actualList = Parser.getVehiclesOnRoutes();
        Collection<Vehicle> difference = checkDifference(currentList, actualList);
        
        logger.info("db " + currentList.size() + " actual " + actualList.size());
        difference.forEach((t) -> {
            logger.info("[Clean outdated vehicle info]");
            vehicleDao.delete(t);
        });
        vehicleDao.updateAllVehicles(Parser.getVehiclesOnRoutes());
        logger.info("[Vehicle list updating finished]");
    }
    @Scheduled(fixedDelay = 20 * 1000)
    private final void updateVehiclesPosition() throws IOException{
        logger.info("[Updating vehicles position]");
        List<Long> imeiList = new ArrayList<>();
        List<Vehicle> vehicles = vehicleDao.findAll();
        vehicles.forEach((t) -> {imeiList.add(t.getImei());});
        List<VehicleState> actualList = Parser.getVehiclesState(imeiList, vehicleDao);
        List<VehicleState> currentList = vehicleStateDao.findAll();
        Collection<VehicleState> result = checkDifference(currentList,actualList);
        
        logger.info("[Database vehicle list size]:" + currentList.size() + " and [Odessa.trasport.ua vehicle list size]:" + actualList.size());
        result.forEach((t) -> {
            vehicleStateDao.delete(t);
            logger.info("[Clean outdated vehicle]: routeId " + t.getRouteId() + ", imei " + t.getVehicleId());
        });
        
        actualList.forEach((t) -> {vehicleStateDao.saveOrUpdate(t);});
        logger.info("[Vehicles position updated]");
    }
    private void updateRoutes(List<Route> routeList, Date lastModified){
        routeDao.deleteAll();
        stoppingsDao.deleteAll();
        routeDao.saveAll(routeList);
        lastModifiedDao.updateLastModified(lastModified);
        HashMap<Integer, StoppingInfo> map = new HashMap<>();
        logger.info("[Stoppings update started.]");
        routeList.forEach((t) -> {
            t.getSegments().forEach((x) -> {
                boolean exist = false;
                StoppingInfo stoppingInfo;
                if(map.containsKey(x.getStopping().getId())){
                    stoppingInfo = map.get(x.getStopping().getId());
                    exist = true;
                } else{
                stoppingInfo = new StoppingInfo();
                stoppingInfo.setId(x.getStopping().getId());
                stoppingInfo.setLat(x.getStopping().getLat());
                stoppingInfo.setLng(x.getStopping().getLng());
                stoppingInfo.setTitle(x.getStopping().getTitle());
                }
                ua.provectus.public_transport.model.stops.Route route = new ua.provectus.public_transport.model.stops.Route();
                route.setId(t.getId());
                route.setFirstStopping(t.getFirstStop());
                route.setLastStopping(t.getLastStop());
                route.setNumber(t.getNumber());
                route.setType(t.getType());
                stoppingInfo.getRoutes().add(route);
                if(!exist)
                map.put(x.getStopping().getId(),stoppingInfo);
            });
        });
        stoppingsDao.saveAll(map.values());
        logger.info("[Stoppings update finished.]");
    }
    
    private Date countExpectedDate(long lastModified, long ttl){
        long time = lastModified + ttl;
        return new Date(time);
    }
    private final <T>Collection<T> checkDifference(Collection<T> a, Collection<T> b){
        Collection<T> intersection = new ArrayList<T>(a);
        intersection.retainAll(b);
        Collection<T> result = new ArrayList<>(a);
        result.removeAll(intersection);
        return result;
    }
}
