package ua.provectus.public_transport.model;

import java.util.Objects;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
public class Vehicle {
    public static final String COLLECTION_NAME = "vehicles";
    @Id
    @JsonProperty("id")
    private long imei;
    @JsonProperty("inventoryNumber")
    private int inventoryNumber;
    @JsonProperty("url")
    private String url;
    @JsonProperty("routeId")
    private int routeId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("seats")
    private int seats;
    @JsonProperty("title")
    private String title;
    private int cost;

    @Override
    public String toString() {
        return "Vehicle{" + "id=" + imei + ", inventoryNumber=" + inventoryNumber + ", url=" + url + ", routeId=" + routeId + ", type=" + type + ", seats=" + seats + ", title=" + title + ", cost=" + cost + '}';
    }

    public long getImei() {
        return imei;
    }

    public void setImei(long id) {
        this.imei = id;
    }

    public int getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (this.imei ^ (this.imei >>> 32));
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
        final Vehicle other = (Vehicle) obj;
        if (this.imei != other.imei) {
            return false;
        }
        return true;
    }
    
}
