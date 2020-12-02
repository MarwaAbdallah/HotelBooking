package model;

import java.io.Serializable;

public class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8479445433690329760L;
	private int id;
	private int hotelId;
	private int roomNo;
	private boolean isBooked;
	private double price;
	
	public Room() {}
	public Room(int id, int hotelId) {
		this.setId(id);
		this.hotelId = hotelId;
	}

	public Room(int id, String hotelId) {
		this.setId(id);
		this.hotelId = Integer.parseInt(hotelId);
	}
	public Room(int id, int hotelId, boolean isBooked) {
		this.setId(id);
		this.hotelId = hotelId;
		this.isBooked = isBooked;
	}
	public Room(int id, int hotelId, boolean isBooked, double price) {
		this.setId(id);
		this.hotelId = hotelId;;
		this.isBooked = isBooked;
		this.price = price;
	}
	public Room(int id, int hotelId, double price) {
		this.setId(id);
		this.hotelId = hotelId;;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public boolean isBooked() {
		return isBooked;
	}
	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}


}
