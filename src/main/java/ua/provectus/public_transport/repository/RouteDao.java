/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import ua.provectus.public_transport.model.Route;

@Repository
public class RouteDao {
    @Autowired private MongoOperations mongoOperations;
    
    public void save(Route route){
        mongoOperations.save(route);
    }
    public void saveAll(List<Route> routes){
        for(Route route : routes){
            save(route);
        }
    }
    public List<Route> findAll(){
        return mongoOperations.findAll(Route.class);
    }
    
    public void deleteAll(){
        mongoOperations.dropCollection("route");
    }
    
    
}
