
package model;

import java.io.Serializable;
import java.util.List;


public class Hotel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4124887478842621046L;
	private int id;
	private String name;
	private Location location;
	private int numStars; // between 0 and 5



	public Hotel(int id, String hotelName, String city, 
			String country, int numStars) {
		this.setId(id);
		this.setName(hotelName);
		this.location = new Location(city,country);
		this.numStars = numStars;
	}


	public Hotel(int hotelId) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}


	public Hotel(int hotelId, String hotelName) {
		// TODO Auto-generated constructor stub
		this.setId(id);
		this.setName(hotelName);
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public int getNumStars() {
		return numStars;
	}


	public void setNumStars(int numStars) {
		this.numStars = numStars;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


}
