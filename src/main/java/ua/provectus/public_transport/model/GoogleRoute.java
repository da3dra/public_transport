package ua.provectus.public_transport.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleRoute {
    @JsonProperty
    private String summary;

    @JsonProperty
    private List<Legs> legs;

    @JsonProperty
    private Map<String,String> overview_polyline;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Legs> getLegs() {
        return legs;
    }

    public void setLegs(List<Legs> legs) {
        this.legs = legs;
    }

    public Map<String, String> getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(Map<String, String> overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    @Override
    public String toString() {
        return "GoogleRoute{" +
                "summary='" + summary + '\'' +
                ", legs=" + legs +
                '}';
    }
}
