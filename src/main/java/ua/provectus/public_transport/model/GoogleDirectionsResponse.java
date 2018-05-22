package ua.provectus.public_transport.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDirectionsResponse {

    @JsonProperty
    private String status;
    @JsonProperty
    private List<GoogleRoute> routes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GoogleRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<GoogleRoute> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return "GoogleDirectionsResponse{" +
                "status='" + status + '\'' +
                ", routes=" + routes +
                '}';
    }
}
