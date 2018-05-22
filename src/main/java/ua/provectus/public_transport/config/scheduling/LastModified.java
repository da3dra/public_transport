/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.config.scheduling;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = LastModified.COLLECTION_NAME)
public class LastModified {
    public static final String COLLECTION_NAME = "last_modified";
    
    @Id
    private String id;
    
    private long lastModified; //in ms

    public LastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public LastModified() {
    }
   
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
    
}
