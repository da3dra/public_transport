package ua.provectus.public_transport.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.*;

@JsonIgnoreProperties(value = {"id", "segmentId"})
public class Point implements Comparable<Point> {

    @JsonProperty
    private double lat;
    @JsonProperty
    private double lng;
    @JsonProperty
    private int position;

    public Point() {
    }

    public Point(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public static List<Point> sortSegmentPoints(List<Point> points) {
        double bestDistance = Point.getSegmentDistance(points);
        List<Point> currentSolution = new ArrayList<>();
        currentSolution.addAll(points);
        Map<Point,Set<Point>>combinations = new HashMap<>();
        currentSolution.forEach(point -> combinations.put(point,new HashSet<>()));
        Random rnd = new Random();
        int a = 0;
        int b = 0;
        Point x = currentSolution.get(a);
        Point y = currentSolution.get(b);
        while (combinations.get(x).contains(y)) {
            a = rnd.nextInt(currentSolution.size());
            b = rnd.nextInt(currentSolution.size());
        }
        x = currentSolution.get(a);
        y = currentSolution.get(b);
        combinations.get(x).add(y);
        combinations.get(y).add(x);
        currentSolution.set(a,y);
        currentSolution.set(b,x);
        double currentDistance = Point.getSegmentDistance(currentSolution);
        if (currentDistance < bestDistance) {
            bestDistance = currentDistance;
            points = currentSolution;
        }
        return points;
    }
    public double measureDistance( Point point2) {
        double lat1 = this.getLat();
        double lat2 = point2.getLat();
        double lon1 = this.getLng();
        double lon2 = point2.getLng();
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

    public static double getSegmentDistance(List<Point>points){
        double distance = 0;
        for(int i = 0;i<points.size()-1;i++){
            distance+=points.get(i).measureDistance(points.get(i+1));
        }
        return distance;
    }

    @Override
    public String toString() {
        return
                "lat=" + lat +
                        ", lng=" + lng +
                        '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.lat) ^ (Double.doubleToLongBits(this.lat) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.lng) ^ (Double.doubleToLongBits(this.lng) >>> 32));
        hash = 37 * hash + this.position;
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
        final Point other = (Point) obj;
        if (Double.doubleToLongBits(this.lat) != Double.doubleToLongBits(other.lat)) {
            return false;
        }
        if (Double.doubleToLongBits(this.lng) != Double.doubleToLongBits(other.lng)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Point p) {
        return Double.compare(this.lat, p.lat) + Double.compare(this.lng, p.lng);
    }

}
