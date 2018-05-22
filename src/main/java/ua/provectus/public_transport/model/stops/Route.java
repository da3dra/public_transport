/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.model.stops;

import ua.provectus.public_transport.model.Stopping;

public class Route {
    private int id;
    private String firstStopping;
    private String lastStopping;
    private int number;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstStopping() {
        return firstStopping;
    }

    public void setFirstStopping(String firstStopping) {
        this.firstStopping = firstStopping;
    }

    public String getLastStopping() {
        return lastStopping;
    }

    public void setLastStopping(String lastStopping) {
        this.lastStopping = lastStopping;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;

        Route route = (Route) o;

        if (getNumber() != route.getNumber()) return false;
        return getType().equals(route.getType());
    }

    @Override
    public int hashCode() {
        int result = getNumber();
        result = 31 * result + getType().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
                "number=" + number +
                ", type='" + type + '\'' +
                '}';
    }
}
