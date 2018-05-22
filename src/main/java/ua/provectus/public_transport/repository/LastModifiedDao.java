/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.repository;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ua.provectus.public_transport.config.scheduling.LastModified;

@Repository
public class LastModifiedDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void setMongoOperations(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void updateLastModified(final Date lastModified){
        delete();
        setLastModified(lastModified);
    }
    
    public LastModified getLastModified(){
        Query query = new Query();
        return mongoTemplate.findOne(query, LastModified.class);
    }
    
    public boolean noLastModifiedDefined(){
        return mongoTemplate.exists(new Query(), LastModified.class);
    }
    
    public void setLastModified(final Date date){
        mongoTemplate.save(new LastModified(date.getTime()));
    }
    
    public void delete(){
        mongoTemplate.dropCollection(LastModified.COLLECTION_NAME);
    }
}
