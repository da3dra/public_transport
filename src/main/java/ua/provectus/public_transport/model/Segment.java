package ua.provectus.public_transport.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

@JsonIgnoreProperties(value = { "built"})
public class Segment {
	@JsonProperty
	private int id;
	@JsonProperty
	private int routeId;
	@JsonProperty
	private int direction;
	@JsonProperty
	private double lat;
	@JsonProperty
	private double lng;
	@JsonProperty
	private int position;
	@JsonProperty
	private int stoppingId;
	@JsonProperty
	private List<Point> points;

	private Stopping stopping;


	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Stopping getStopping() {
		return stopping;
	}

	public void setStopping(Stopping stopping) {
		this.stopping = stopping;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public int getStoppingId() {
		return stoppingId;
	}

	public void setStoppingId(int stoppingId) {
		this.stoppingId = stoppingId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Segment{" +
				"id=" + id +
				", routeId=" + routeId +
				", direction=" + direction +
				", position=" + position +
				", stoppingId=" + stoppingId +
				", stopping=" + stopping +
				", points=" + points +
				'}';
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + this.direction;
		hash = 79 * hash + this.position;
		hash = 79 * hash + Objects.hashCode(this.points);
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
		final Segment other = (Segment) obj;
		if (this.direction != other.direction) {
			return false;
		}
		if (this.position != other.position) {
			return false;
		}
		if (!Objects.equals(this.points, other.points)) {
			return false;
		}
		return true;
	}

}
