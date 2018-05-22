/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.model.stops;

import java.util.*;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = StoppingInfo.COLLECTION_NAME)
public class StoppingInfo {
    public static final String COLLECTION_NAME = "stoppings";
    
    private int id;
    private double lat;
    private double lng;
    private String title;
    private Collection<Route> routes = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Collection<Route> routes) {
        this.routes = routes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.id;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.lat) ^ (Double.doubleToLongBits(this.lat) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.lng) ^ (Double.doubleToLongBits(this.lng) >>> 32));
        hash = 71 * hash + Objects.hashCode(this.title);
        hash = 71 * hash + Objects.hashCode(this.routes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StoppingInfo other = (StoppingInfo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.lat) != Double.doubleToLongBits(other.lat)) {
            return false;
        }
        if (Double.doubleToLongBits(this.lng) != Double.doubleToLongBits(other.lng)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.routes, other.routes)) {
            return false;
        }
        return true;
    }
    
    
    
}
