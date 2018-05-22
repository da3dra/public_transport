package ua.provectus.public_transport.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Parking.COLLECTION_NAME)
public class Parking {
    public static final String COLLECTION_NAME = "parking";
    private double lat;
    private double lng;
    private int places;
    private String adress;
    private ParkingType type;

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public ParkingType getType() {
        return type;
    }

    public void setType(ParkingType type) {
        this.type = type;
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

}
 enum ParkingType{

     OFFICIAL, SEASON, ALLDAY, UNDEFINED;

     static ParkingType getParkingType(String s){
         switch (s){
             case "цілодобові":
                 return ALLDAY;
             case "службові":
                 return OFFICIAL;
             case "сезонна":
                 return SEASON;
         }
         return UNDEFINED;
     }
 }
