package dao;
import model.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import db.DBManager;
import model.Hotel;
import model.Location;
import model.Reservation;

import model.User;

public class ReservationDao {

	private DataSource datasource;
	public ReservationDao(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public void createReservation(Reservation r) 
			throws Exception {
		Connection myCon = null;
		Connection myConb = null;
		PreparedStatement myStmt=null;
		PreparedStatement myStmtb=null;
		try {
			// get DB connection
			myCon=datasource.getConnection();
			//create SQL for insert
			String sql1 = "INSERT INTO reservations" + 
			"(user_id, checkin, checkout, room_id, hotel_id, price)" 
						+ " VALUES(?,?,?,?,?,?)";
			
			myStmt=myCon.prepareStatement(sql1);
			// set the param values for the USER (the "?")
			myStmt.setInt(1,r.getCustomer().getId());
			System.out.println("userId"+r.getCustomer().getId());
			
			myStmt.setObject(2, r.getFromStayDate());
			System.out.println("checkin"+r.getFromStayDate());
			
			myStmt.setObject(3, r.getToStayDate());
			System.out.println("checkout"+r.getToStayDate());
			
			myStmt.setInt(4,r.getBedding().getId());
			System.out.println("roomid"+r.getBedding().getId());
			
			myStmt.setInt(5, r.getHotel().getId());
			System.out.println("hotelid"+r.getHotel().getId());
			
			myStmt.setDouble(6,r.getPrice());	
			System.out.println("price"+r.getPrice());
			
			myStmt.execute();
			String sql2 = "update rooms"
					+ " set is_booked = ?"
					+ "where room_id = ?";
					
			myConb = datasource.getConnection();
			myStmtb=myConb.prepareStatement(sql2);
			myStmtb.setBoolean(1, true);
			myStmtb.setInt(2, r.getBedding().getId());
			int changedRows = myStmtb.executeUpdate();
		} 
		catch (SQLException ex) {
            System.out.println(ex.getMessage());
		} 		finally {
				DBManager.close(myCon, myStmt, null);
				DBManager.close(myConb, myStmtb, null);
		}
		
	}
	public void cancelReservation(long reservationID, int roomId) 
			throws Exception {
		Connection myCon = null;
		Connection myConb = null;
		PreparedStatement myStmt=null;
		PreparedStatement myStmtb=null;
		int resId = (int) reservationID;
		try {
			// get DB connection
			myCon=datasource.getConnection();
			//create SQL for insert
			String sql1 = "DELETE FROM reservations" + 
			" where reservation_id = ?";
			
			myStmt=myCon.prepareStatement(sql1);
			// set the param values for the USER (the "?")
			myStmt.setInt(1,resId);

		
			myStmt.execute();
			String sql2 = "update rooms"
					+ " set is_booked = ?"
					+ "where room_id = ?";
					
			myConb = datasource.getConnection();
			myStmtb=myConb.prepareStatement(sql2);
			myStmtb.setBoolean(1, false);
			myStmtb.setInt(2, roomId);
			int changedRows = myStmtb.executeUpdate();
		} 
		catch (SQLException ex) {
            System.out.println(ex.getMessage());
		} 		finally {
				DBManager.close(myCon, myStmt, null);
				DBManager.close(myConb, myStmtb, null);
		}
		
	}

	public List<Reservation> getAllReservationByHotelBasedOnEmployee(String empEmail) 
			throws SQLException {
		List<Reservation> reservations = new ArrayList<>();
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql= "SELECT u.email as customeremail, u.id as customerid," + 
					" res.* FROM reservations res" + 
					" inner join hotels_employees on res.hotel_id = hotels_employees.hotel_id" + 
					" inner join users u on res.user_id = u.id" + 
					" where hotels_employees.employee_email = ? ;";
			
			myStmt = myCon.prepareStatement(sql);
			myStmt.setString(1, empEmail);
			myRs = myStmt.executeQuery();
			while(myRs.next()) {
				long resId = myRs.getInt("reservation_id");
				LocalDate checkIn = myRs.getObject("checkin", LocalDate.class);
				LocalDate checkOut =  myRs.getObject("checkout", LocalDate.class);;
				int hotelId = myRs.getInt("hotel_id");
				int roomId = myRs.getInt("room_id");
				Room room = new Room(roomId,hotelId);
				Hotel hotel = new Hotel(hotelId);
				int userId = myRs.getInt("customerid");
				String usrEmail = myRs.getString("customeremail");
				User user = new User(userId,usrEmail);
				Reservation res = new Reservation(resId, checkIn, checkOut, 
						user, hotel, room);
				reservations.add(res);
			}
			
		} finally {
			DBManager.close(myCon, myStmt, myRs);
		}
		
		
		return reservations;
		
	}
	public List<Reservation> getAllReservationByUser(User user) throws SQLException {
		List<Reservation> reservations = new ArrayList<>();
		int userId = user.getId();
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sqlReservation = "select * from reservations, hotels" + 
					" where reservations.user_id= ?" + 
					" and reservations.hotel_id = hotels.id";
			myStmt = myCon.prepareStatement(sqlReservation);
			myStmt.setInt(1, userId);
			myRs = myStmt.executeQuery();
			while(myRs.next()) {
				long resId = myRs.getInt("reservation_id");
				LocalDate checkIn = myRs.getObject("checkin", LocalDate.class);
				LocalDate checkOut =  myRs.getObject("checkout", LocalDate.class);;
				String hotelName = myRs.getString("hotel_name");
				int hotelId = myRs.getInt("hotel_id");
				String city = myRs.getString("city_id");
				String country = myRs.getString("country_id");
				int numStars = myRs.getInt("numstars");
				int roomId = myRs.getInt("room_id");
				double price = myRs.getDouble("price");
				Room room = new Room(roomId,hotelId);
				Hotel hotel = new Hotel(hotelId, hotelName, city, 
						country, numStars);
				
				Reservation res = new Reservation(resId, checkIn, checkOut, 
						user, hotel, room);
				reservations.add(res);
			}
			
		} finally {
			DBManager.close(myCon, myStmt, myRs);
		}
		
		
		return reservations;
		
	}


	


}
