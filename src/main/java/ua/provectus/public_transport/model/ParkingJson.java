package ua.provectus.public_transport.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingJson {

    @JsonProperty("features")
    private List<RawParking> parkingList;

    public List<Parking> getParkings(){
        List<Parking> parkings = new ArrayList<>();
        this.parkingList.forEach(rawParking -> parkings.add(rawParking.convertToParking()));
        return parkings;
    }
}


@JsonIgnoreProperties(ignoreUnknown = true)
class RawParking{
    @JsonProperty
    private ParkData properties;
    @JsonProperty
    private Geometry geometry;

    ParkData getProperties() {
        return properties;
    }

    void setProperties(ParkData properties) {
        this.properties = properties;
    }

    Geometry getGeometry() {
        return geometry;
    }

    void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    Parking convertToParking(){

        Parking parking = new Parking();
        String places = this.getProperties().getDescription().substring(this.getProperties().getDescription().indexOf("Кількість машиномісць"));
        places = places.substring(0,places.indexOf("\n")).replaceAll("\\D+","").replaceAll(" ","");;
        String type = this.getProperties().getDescription().substring(this.getProperties().getDescription().indexOf("парковки"));
        type = type.substring(type.indexOf(":")+1).replaceAll(" ","");
        if(!places.isEmpty()){
            parking.setPlaces(Integer.parseInt(places));
        }else  parking.setPlaces(0);
        parking.setType(ParkingType.getParkingType(type));
        parking.setAdress(this.getProperties().getName());
        parking.setLng(this.getGeometry().getCoordinates().get(0));
        parking.setLat(this.getGeometry().getCoordinates().get(1));
        return parking;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ParkData{
    @JsonProperty("Name")
    private String name;
    @JsonProperty
    private String description;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description.replace("<br>","\n");
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Geometry{
    @JsonProperty
    private List<Double> coordinates;

    List<Double> getCoordinates() {
        return coordinates;
    }

    void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

}