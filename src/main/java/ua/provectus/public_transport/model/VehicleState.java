/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.model;

import java.util.Objects;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = VehicleState.COLLECTION_NAME)
@JsonIgnoreProperties(value = {"ignit","activesim","ts"})
public class VehicleState {
    public static final String COLLECTION_NAME = "vehicleState";
    @Id
    @JsonProperty("imei")
    private long vehicleId;
    @JsonProperty
    private double lat;
    @JsonProperty
    private double lng;
    @JsonProperty
    private int speed;
    @JsonProperty
    private int azimut;
    @JsonProperty
    private String gsmpower;
    @JsonProperty
    private int sats;
    @JsonProperty
    private int seats;
    @JsonProperty
    private int cost;
    private int routeId;
    private String type;
    private int InventoryNumber;

    public int getInventoryNumber() {
        return InventoryNumber;
    }

    public void setInventoryNumber(int inventoryNumber) {
        InventoryNumber = inventoryNumber;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAzimut() {
        return azimut;
    }

    public void setAzimut(int azimut) {
        this.azimut = azimut;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "VehicleState{" + "vehicleId=" + vehicleId + ", lat=" + lat + ", lng=" + lng + ", speed=" + speed + ", azimut=" + azimut + ", gsmpower=" + gsmpower + ", sats=" + sats + ", seats=" + seats + ", cost=" + cost + ", routeId=" + routeId + ", type=" + type + '}';
    }
    
    
    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getGsmpower() {
        return gsmpower;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
    
    
    public void setGsmpower(String gsmpower) {
        this.gsmpower = gsmpower;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    
    public int getSats() {
        return sats;
    }

    public void setSats(int sats) {
        this.sats = sats;
    }

        @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.vehicleId ^ (this.vehicleId >>> 32));
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
        final VehicleState other = (VehicleState) obj;
        if (this.vehicleId != other.vehicleId) {
            return false;
        }
        return true;
    }

    
    
    
    
}
