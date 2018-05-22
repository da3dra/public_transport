/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.codehaus.jackson.annotate.JsonIgnore;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties(value = {"title", "color", "eng", "last_update","vehicles"})
@Document(collection = Route.COLLECTION_NAME)
public class Route {
    public static final String COLLECTION_NAME = "route";
    @Id
    @JsonProperty("id")
    private int id;
    @JsonProperty("Number")
    private int number;
    @Indexed
    @JsonProperty("Type")
    private String type;
    @JsonProperty("distance")
    private String distance;
    @JsonProperty("segments")
    private List<Segment> segments;
    @JsonProperty("cost")
    private int cost;
    private String firstStop;
    private String lastStop;
    private List<Point> direction0;
    private List<Point> direction1;

    public List<Point> getDirection0() {
        return direction0;
    }

    public void setDirection0(List<Point> direction0) {
        this.direction0 = direction0;
    }

    public List<Point> getDirection1() {
        return direction1;
    }

    public void setDirection1(List<Point> direction1) {
        this.direction1 = direction1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public String getFirstStop() {
        return firstStop;
    }

    public void setFirstStop(String firstStop) {
        this.firstStop = firstStop;
    }

    public String getLastStop() {
        return lastStop;
    }

    public void setLastStop(String lastStop) {
        this.lastStop = lastStop;
    }

    @Override
    public String toString() {
        return "Route [id=" + id + ", number=" + number + ", type=" + type + ", distance=" + distance + ", segments="
                + segments + "]";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + this.number;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.distance);
        hash = 97 * hash + Objects.hashCode(this.segments);
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
        final Route other = (Route) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.distance, other.distance)) {
            return false;
        }
        if (!Objects.equals(this.segments, other.segments)) {
            return false;
        }
        return true;
    }

}
