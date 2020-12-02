package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import db.DBManager;
import exception.RoomAlreadyBookedException;
import model.Reservation;
import model.Room;

public class RoomDao {
private DataSource datasource;
	
	public RoomDao(DataSource datasource)  {
		this.datasource = datasource;
	}
	
	public int getAvailableRoomCountForHotel(int hotelId) throws Exception {
		int count = 0;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select count(*) from rooms where hotel_id = ? and is_booked = ?";
			myStmt = myCon.prepareStatement(sql);
			myStmt.setInt(1,hotelId);
			myStmt.setBoolean(2, false);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				count = myRs.getInt("count");
			}
		} finally {
			//close(myCon,myStmt,myRs);
			DBManager.close(myCon,myStmt,myRs);
		}
		return count;
	}
	public double getMinPriceForHotel(int hotelId) throws Exception {
		double count = 0.0;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select min(price) from rooms where hotel_id = ? and is_booked = ?";
			myStmt = myCon.prepareStatement(sql);
			myStmt.setInt(1,hotelId);
			myStmt.setBoolean(2, false);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				count = myRs.getDouble("min");
			}
		} finally {
			//close(myCon,myStmt,myRs);
			DBManager.close(myCon,myStmt,myRs);
		}
		return count;
	}
	
	public Reservation reserveRoom(int roomId, int hotelId, int userid) throws Exception {
		// incomplete, need to add user and create a reservation
		Reservation reservation = null;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select * from rooms where hotel_id = ? and room_id = ?";
			myStmt = myCon.prepareStatement(sql);
			myStmt.setInt(1,hotelId);
			myStmt.setInt(2, roomId);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				// add description stuff here
				boolean alreadyBooked = myRs.getBoolean("is_booked");
				if (alreadyBooked == true) {
					throw new RoomAlreadyBookedException(roomId,hotelId);
				} else {
					
				}
			}
		} finally {
			//close(myCon,myStmt,myRs);
			DBManager.close(myCon,myStmt,myRs);
		}
		return reservation;
	}
	
	public Room getRoom(int hotelId, int roomId) throws Exception{
		//useful when we add room descrption
		Room room = null;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select * from rooms where hotel_id = ? and room_id = ?";
			myStmt = myCon.prepareStatement(sql);
			myStmt.setInt(1,hotelId);
			myStmt.setInt(2, roomId);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				// add description stuff here
				room = new Room(roomId, hotelId);
			}
		} finally {
			//close(myCon,myStmt,myRs);
			DBManager.close(myCon,myStmt,myRs);
		}
		return room;
	}

	
	public List<Room> getAvailableRoomsForHotel (int hotelId) throws Exception {
		List<Room> myRooms = new ArrayList<>();
		
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select * from rooms where hotel_id = ? and is_booked = ?";
			myStmt = myCon.prepareStatement(sql);
			myStmt.setInt(1,hotelId);
			myStmt.setBoolean(2, false);
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				int roomId = myRs.getInt("room_id");
				double price = myRs.getDouble("price");
				Room room = new Room(roomId, hotelId, false, price); // false => see sql we only query available
				myRooms.add(room);
			}
		} finally {
			//close(myCon,myStmt,myRs);
			DBManager.close(myCon,myStmt,myRs);
		}
		return myRooms;
	}
	

	
	
}
