/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import ua.provectus.public_transport.model.Stopping;
import ua.provectus.public_transport.model.stops.StoppingInfo;

@Repository
public class StoppingsDao {
    @Autowired MongoOperations mongoOperations;
    
    public void save(StoppingInfo stopping){
        mongoOperations.save(stopping);
    }
    
    public void saveAll(Collection<StoppingInfo> stoppings){
        stoppings.forEach((t) -> {
            save(t);
        });
    }
    
    public List<StoppingInfo> findAll(){
        return mongoOperations.findAll(StoppingInfo.class);
    }
    
    public void deleteAll(){
        mongoOperations.dropCollection(StoppingInfo.COLLECTION_NAME);
    }
}
