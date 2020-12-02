package model;

import java.io.Serializable;

import javax.persistence.*;

public class Location implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String city;
	@Id
	private String country;
	
	public Location() {}
	public Location (String city, String country) {
		this.city = city;
		this.country = country;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "(city :"+city+", country: "+country+")";
	}
	

}
