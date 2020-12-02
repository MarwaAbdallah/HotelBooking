package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;


public class Bed implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5634844240647422011L;
	private int capacity;
	private String label;
	private int roomId;
	private int hotelId;
	
	public Bed(int capacity) {
		this.setCapacity(capacity);
		setLabel();
	}


	public String getLabel() {
		return label;
	}

	public void setLabel() {
		if(capacity == 0) {
			label = "";
		} else if (capacity == 1) {
			label = "Single";
		} else {
			label = "Queen";
		}
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public int getRoomId() {
		return roomId;
	}


	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}


	public int getHotelId() {
		return hotelId;
	}


	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}


	public void setLabel(String label) {
		this.label = label;
	}
	
	

}
