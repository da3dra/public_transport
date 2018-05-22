package ua.provectus.public_transport.parser;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.codehaus.jackson.map.ObjectMapper;
import ua.provectus.public_transport.exceptions.NoVehiclesOnThisRouteException;

import ua.provectus.public_transport.model.*;

import ua.provectus.public_transport.repository.VehicleDao;
public class Parser {

    private final static String routesURL = "http://transport.odessa.ua/php/LoadingListRoutes.php";
    private static String routeURL = "http://transport.odessa.ua/php/LoadingRoute.php";
    private static String stoppingURL = "http://transport.odessa.ua/php/LoadingStopping.php";
    private static String googleDirectsAPI = "https://maps.googleapis.com/maps/api/directions/json";
    private static final String getStateURL = "http://transport.odessa.ua/php/getState.php?";
    private static String googleAPIkey = "AIzaSyC_b4fVb9r_Uhxxcnwvbvk2n4riMnkul_s";

    public static List<Route> getRouteList() throws IOException, InterruptedException {
        Response response = makeResponse(routesURL);
        List<Route> routeList = response.getList();
        String urlParams = "";
        for (Route route : routeList) {
            System.err.println("-------------------GETTING ROUTE "+route.getNumber()+" "+route.getType()+"----------------------");
            urlParams = "type=" + route.getType() + "&number=" + route.getNumber() + "&language=ru";
            FullResponse fullRes = makeFullResponse(routeURL, urlParams);
            List<Segment> segments = getSegmentsWithStoppings(fullRes);
            route.setSegments(segments);
            LinkedList<Point>points0 =new LinkedList<>();
            LinkedList<Point>points1 = new LinkedList<>();
            for (Segment segment:segments) {
                int direction = segment.getDirection();
                int position = segment.getPosition();
                if(segment.getPoints().size()>2){
                    segment.setPoints(Point.sortSegmentPoints(segment.getPoints()));
                }
                List<Point> segmPoints = segment.getPoints();
                if(direction==-1&&position==-1){
                    route.setFirstStop(segment.getStopping().getTitle());
                    points1.addAll(segmPoints);
                }else if(direction==-1&&position==0){
                    route.setLastStop(segment.getStopping().getTitle());
                    points0.addAll(segmPoints);
                }else if(direction==0){
                    points0.addAll(segmPoints);
                }else if(direction==1){
                    points1.addAll(segmPoints);
                }
            }
            if (!route.getType().equals("tram")&&points0.size()>0) {
                points1.addAll(getPointsFromGoogleDirects(points1.getLast(), points0.getFirst()));
                points0.addAll(getPointsFromGoogleDirects(points0.getLast(), points1.getFirst()));
            }
            route.setDirection0(points0);
            route.setDirection1(points1);
        }
        System.err.println("-------------------------COMPLETED--------------------------------");
        return routeList;
    }

    public static List<Parking> getParkings() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ParkingJson obj = mapper.readValue(new File("parkings1.json"), ParkingJson.class);
        List<Parking> parkings = obj.getParkings();
        obj = mapper.readValue(new File("parkings2.json"), ParkingJson.class);
        parkings.addAll(obj.getParkings());
        obj = mapper.readValue(new File("parkings3.json"), ParkingJson.class);
        parkings.addAll(obj.getParkings());
        return parkings;
    }

    private static List<Segment> getSegmentsWithStoppings(FullResponse fullRes) throws IOException {
        List<Segment> segments = fullRes.getData().getSegments();
        List<Segment> newSegments = new ArrayList<>();
        for (Segment segment : segments) {
            String urlParams = "stopping[]=" + segment.getStoppingId() + "&language=ru";
            StoppingResponse stopRes = makeStoppingResponse(stoppingURL, urlParams);
            Stopping stopping = stopRes.getStoppings().get(0);
            segment.setStopping(stopping);
            newSegments.add(segment);
        }
        return newSegments;
    }

    private static HttpURLConnection constructRequest(String url, String urlParams) throws IOException {
        URL uri = new URL(url);
        HttpURLConnection huc = (HttpURLConnection) uri.openConnection();
        huc.setDoOutput(true);
        huc.setRequestMethod("POST");
        huc.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        if (!urlParams.isEmpty()) {
            byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);
            huc.setRequestProperty("Content-Length", Integer.toString(postData.length));
            try (DataOutputStream dos = new DataOutputStream(huc.getOutputStream())) {
                dos.write(postData);
                dos.flush();
            }
        }
        return huc;
    }

    private static Response makeResponse(String url) throws IOException {
        HttpURLConnection huc = constructRequest(url, "");
        ObjectMapper mapper = new ObjectMapper();
        Response response = mapper.readValue(huc.getInputStream(), Response.class);
        return response;
    }

    private static FullResponse makeFullResponse(String url, String urlParams) throws IOException {
        HttpURLConnection huc = constructRequest(url, urlParams);
        ObjectMapper mapper = new ObjectMapper();
        FullResponse response = mapper.readValue(huc.getInputStream(), FullResponse.class);
        return response;
    }

    private static StoppingResponse makeStoppingResponse(String url, String urlParams) throws IOException {
        HttpURLConnection huc = constructRequest(url, urlParams);
        ObjectMapper mapper = new ObjectMapper();
        StoppingResponse response = mapper.readValue(huc.getInputStream(), StoppingResponse.class);
        return response;
    }

    public static List<Vehicle> getVehiclesOnRoutes() throws IOException{
        Response response = makeResponse(routesURL);
        List<Route> routeList = response.getList();
        String urlParams = "";
        List<Vehicle> vehicles = new ArrayList<>();
        for (Route route : routeList) {
            urlParams = "type=" + route.getType() + "&number=" + route.getNumber() + "&language=ru";
            FullResponse fullRes = makeFullResponse(routeURL, urlParams);
            vehicles.addAll(fullRes.getData().getTransport());
            for(Vehicle v : vehicles){
                v.setCost(route.getCost());
            }
        }
        return vehicles;
    }

    public static List<VehicleState> getVehiclesState(List<Long> ids, VehicleDao vehicleDao) throws IOException{
        StringBuilder params = new StringBuilder();
        for(Long i : ids){
            params.append("imei[]=" + i + '&');
        }
        HttpURLConnection huc = constructGetRequest(getStateURL, params.toString());
        ObjectMapper mapper = new ObjectMapper();
        List<VehicleState> list = mapper.readValue(huc.getInputStream(),
                mapper.getTypeFactory().constructCollectionType(List.class, VehicleState.class));
        for(VehicleState v : list){
            Vehicle current = vehicleDao.getVehicleByImei(v.getVehicleId());
            v.setSeats(current.getSeats());
            v.setCost(current.getCost());
            v.setRouteId(current.getRouteId());
            v.setType(current.getType());
            v.setInventoryNumber(current.getInventoryNumber());
        }
        if(list.isEmpty()){
            throw new NoVehiclesOnThisRouteException();
        }
        return list;
    }

    public static HttpURLConnection constructGetRequest(String url, String params) throws IOException {
        URL uri = new URL(url + params);
        HttpURLConnection huc = (HttpURLConnection) uri.openConnection();
        huc.setRequestMethod("GET");
        return huc;
    }
    public static List<Point> getPointsFromGoogleDirects(Point p1, Point p2) throws IOException{
        List<Point> newPoints = new ArrayList<>();
        StringBuilder paramBuilder = new StringBuilder("");
        paramBuilder.append("?&mode=transit");
        paramBuilder.append("&origin="+p1.getLat()+","+p1.getLng());
        paramBuilder.append("&destination="+p2.getLat()+","+p2.getLng());
        paramBuilder.append("&key="+googleAPIkey);
        String urlParams  = paramBuilder.toString();
        String URL = googleDirectsAPI+urlParams;
        URL uri = new URL(URL);
        try {
            HttpURLConnection huc = (HttpURLConnection) uri.openConnection();
            huc.setRequestMethod("GET");
            byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);
            huc.setRequestProperty("Content-Length", Integer.toString(postData.length));
            ObjectMapper mapper = new ObjectMapper();
            GoogleDirectionsResponse response = mapper.readValue(huc.getInputStream(), GoogleDirectionsResponse.class);
            int i=0;
            do{
                huc = (HttpURLConnection) uri.openConnection();
                response = mapper.readValue(huc.getInputStream(), GoogleDirectionsResponse.class);
                i+=1;
                System.err.println(i);}
            while(!response.getStatus().equals("OK") && i!=15);
            System.err.println("status: "+response.getStatus().equals("OK"));
            if(!response.getRoutes().isEmpty()){
                String polyline = response.getRoutes().get(0).getOverview_polyline().get("points");
                newPoints = decodePoly(polyline);
            }
            return newPoints;
        } catch (ConnectException e) {
            e.printStackTrace();
            return new ArrayList<Point>();
        }
    }
    private static List<Point> decodePoly(String encoded) {
        List<Point> poly = new ArrayList<Point>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            try {
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
            } catch (StringIndexOutOfBoundsException e) {

            }
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            Point p = new Point( ((double) lat / 1E5),((double) lng / 1E5) );
            poly.add(p);
        }

        return poly;
    }

}
