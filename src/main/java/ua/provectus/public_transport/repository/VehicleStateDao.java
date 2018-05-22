package ua.provectus.public_transport.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ua.provectus.public_transport.exceptions.NoVehiclesOnThisRouteException;
import ua.provectus.public_transport.model.VehicleState;

@Repository
public class VehicleStateDao {

    @Autowired private MongoOperations mongoOperations;

    public void saveOrUpdate(VehicleState vs){
        mongoOperations.save(vs);
    }

    public void delete(VehicleState vs){
        mongoOperations.remove(vs);
    }

    public List<VehicleState> findAll(){
        return mongoOperations.findAll(VehicleState.class);
    }

    public List<VehicleState> getByRouteId(List<Long> routeId){
        Criteria c = Criteria.where("routeId").in(routeId);
        Query q = new Query(c);
        List<VehicleState> vehicles = mongoOperations.find(q,VehicleState.class);
        vehicles.forEach((t) -> {
            System.err.println(t.toString());
        });
        if(vehicles == null){
            throw new NoVehiclesOnThisRouteException();
        }
        return vehicles;
    }
}
