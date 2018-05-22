/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.controller;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ua.provectus.public_transport.config.scheduling.LastModified;
import ua.provectus.public_transport.exceptions.*;
import ua.provectus.public_transport.model.*;
import ua.provectus.public_transport.model.stops.StoppingInfo;
import ua.provectus.public_transport.parser.Parser;
import ua.provectus.public_transport.repository.*;

@Controller
public class RouteController {

    @Autowired
    RouteDao routeDao;
    @Autowired
    LastModifiedDao lastModifiedDao;
    @Autowired
    VehicleDao vehicleDao;
    @Autowired
    VehicleStateDao vehicleStateDao;
    @Autowired
    StoppingsDao stoppingsDao;
    @Autowired
    ParkingDao parkingDao;

    @RequestMapping(path = "/routes", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Route>> getRouteList(WebRequest wr, HttpServletResponse httpServletResponse) {
        List<Route> routes = routeDao.findAll();
        LastModified lastModified = lastModifiedDao.getLastModified();
        if (routes.isEmpty() || lastModified == null) {
            throw new ServiceIsBusyException();
        }
        httpServletResponse.setHeader("Last-Modified", getLastModified(lastModified).format(DateTimeFormatter.RFC_1123_DATE_TIME));
        if (wr.checkNotModified(lastModified.getLastModified())) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<VehicleState>> getState(@RequestParam("route[]") List<String> requestParams) throws IOException {
        List<Long> routeIds = new ArrayList<>();
        try {
            requestParams.forEach(((t) -> {
                routeIds.add(Long.parseLong(t));
            }));
        } catch (NumberFormatException nfe) {
            throw new BadRequestParametersException();
        }
        List<VehicleState> vehiclesState = vehicleStateDao.getByRouteId(routeIds);
        if (vehiclesState.isEmpty()) {
            throw new NoVehiclesOnThisRouteException();
        }
        return new ResponseEntity<>(vehiclesState, HttpStatus.OK);
    }

    @RequestMapping(path = "/stoppings", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StoppingInfo>> stoppings() {
        List<StoppingInfo> stoppings = stoppingsDao.findAll();
        if(stoppings == null || stoppings.isEmpty()){
            throw new ServiceIsBusyException();
        }
        return new ResponseEntity<>(stoppings,HttpStatus.OK);
    }

    @RequestMapping(path = "/parkings", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Parking>> parkings() throws IOException {
        List<Parking> parkings = parkingDao.findAll();
        if(parkings == null || parkings.isEmpty()){
            parkingDao.saveAll(Parser.getParkings());
        }
        parkings = parkingDao.findAll();
        return new ResponseEntity<>(parkings,HttpStatus.OK);
    }

    public ZonedDateTime getLastModified(LastModified lm) {
        if (lm == null) {
            throw new ServiceIsBusyException();
        }
        ZonedDateTime zdt = ZonedDateTime.ofInstant(new Date(lm.getLastModified()).toInstant(), ZoneId.of("GMT"));
        return zdt;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public @ResponseBody
    String handleMissingParams(MissingServletRequestParameterException ex) {
        return "missing route[] params";
    }
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ClientAbortException.class)
    public @ResponseBody
    String handleClientAbort(ClientAbortException ex){
        return "Client has aborted loading";
    }
}
