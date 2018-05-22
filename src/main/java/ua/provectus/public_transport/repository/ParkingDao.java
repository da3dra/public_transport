package ua.provectus.public_transport.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import ua.provectus.public_transport.model.Parking;

import java.util.List;

@Repository
public class ParkingDao {
    @Autowired
    private MongoOperations mongoOperations;

    public void save(Parking parking){
        mongoOperations.save(parking);
    }

    public void saveAll(List<Parking> parkings){
        for(Parking parking : parkings){
            save(parking);
        }
    }
    public List<Parking> findAll(){
        return mongoOperations.findAll(Parking.class);
    }
}
