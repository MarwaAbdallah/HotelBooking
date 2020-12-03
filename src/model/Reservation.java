package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;



public class Reservation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -154536396756969294L;
	private long id;
	private LocalDate fromStayDate;
	private LocalDate toStayDate;
	
	private User customer;
	private Hotel hotel;
	private Room bedding;
	private double price ; // sum of all rooms price

	
	public Reservation(long id, LocalDate fromStayDate, LocalDate toStayDate, User customer, 
			Hotel hotel, Room room) {
		this.id = id;
		this.setFromStayDate(fromStayDate);
		this.setToStayDate(toStayDate);

		this.customer = customer;
		this.hotel = hotel;
		this.setBedding(room);
	}
	public Reservation(LocalDate fromStayDate, LocalDate toStayDate, User customer, 
			Hotel hotel, Room room) {
		this.setFromStayDate(fromStayDate);
		this.setToStayDate(toStayDate);
		this.customer = customer;
		this.hotel = hotel;
		this.setBedding(room);
		this.setPrice(room.getPrice());
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}



	public Room getBedding() {
		return bedding;
	}

	public void setBedding(Room bedding) {
		this.bedding = bedding;
	}

	public LocalDate getFromStayDate() {
		return fromStayDate;
	}

	public void setFromStayDate(LocalDate fromStayDate) {
		this.fromStayDate = fromStayDate;
	}

	public LocalDate getToStayDate() {
		return toStayDate;
	}

	public void setToStayDate(LocalDate toStayDate) {
		this.toStayDate = toStayDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
}
