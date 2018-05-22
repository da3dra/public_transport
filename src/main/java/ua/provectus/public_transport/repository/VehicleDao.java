package ua.provectus.public_transport.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;
import ua.provectus.public_transport.exceptions.NoVehiclesOnThisRouteException;
import ua.provectus.public_transport.model.Vehicle;

@Repository
public class VehicleDao {

    @Autowired private MongoOperations mongoOperations;


    public void saveAll(List<Vehicle> list){
        list.forEach((t) -> {mongoOperations.save(t);});
    }

    public List<Vehicle> findAll(){
        return mongoOperations.findAll(Vehicle.class);
    }

    public void deleteAll(){
        mongoOperations.dropCollection(Vehicle.COLLECTION_NAME);
    }

    public void delete(Vehicle v){
        mongoOperations.remove(v);
    }
    public List<Vehicle> getVehicleByRouteId(final long id){
        Criteria c = Criteria.where("routeId").is(id);
        Query query = new Query(c);
        List<Vehicle> v = mongoOperations.find(query, Vehicle.class);
        if(v == null){
            throw new NoVehiclesOnThisRouteException();
        }
        return v;
    }
    public Vehicle getVehicleByImei(final long imei){
        Criteria c = Criteria.where("imei").is(imei);
        Query query = new Query(c);
        Vehicle vehicle = mongoOperations.findOne(query, Vehicle.class);
        return vehicle;
    }

    public void updateVehicleByImei(Vehicle v){
        mongoOperations.save(v);
    }

    public void updateAllVehicles(List<Vehicle> vehicles){
        vehicles.forEach((t) -> {updateVehicleByImei(t);});
    }

}
