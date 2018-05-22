package ua.provectus.public_transport.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Step {
    @JsonProperty
    private Map<String,String> distance;

    @JsonProperty
    private Map<String,Double> end_location;

    @JsonProperty
    private Map<String,Double> start_location;

    @JsonProperty
    private Map<String,String> polyline;


    public Map<String, String> getDistance() {
        return distance;
    }

    public void setDistance(Map<String, String> distance) {
        this.distance = distance;
    }

    public Map<String, Double> getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Map<String, Double> end_location) {
        this.end_location = end_location;
    }

    public Map<String, Double> getStart_location() {
        return start_location;
    }

    public void setStart_location(Map<String, Double> start_location) {
        this.start_location = start_location;
    }

    public Map<String, String> getPolyline() {
        return polyline;
    }

    public void setPolyline(Map<String, String> polyline) {
        this.polyline = polyline;
    }

    @Override
    public String toString() {
        return "Step{" +
                "distance=" + distance +
                ", end_location=" + end_location +
                ", start_location=" + start_location +
                '}';
    }
}
