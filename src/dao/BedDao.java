package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import db.DBManager;
import model.Hotel;

public class BedDao {
	private DataSource datasource;
	
	public BedDao(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public int countBedsForRoom(int roomId, int hotelId) throws Exception {
		int count = 0;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Hotel hotel = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select count(*) from beds where hotel_id = ? and room_id = ?";

			myStmt = myCon.prepareStatement(sql);
			myStmt.setInt(1,hotelId);
			myStmt.setInt(2,roomId);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				count = myRs.getInt("count");
			}
		} finally {
			DBManager.close(myCon,myStmt,myRs);
		}
		return count;
	}
	
	public int countBedCapacityForRoom(int roomId, int hotelId) throws Exception {
		int count = 0;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Hotel hotel = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select sum(bdcapacity) from beds where hotel_id = ? and room_id = ?";

			myStmt = myCon.prepareStatement(sql);
			myStmt.setInt(1,hotelId);
			myStmt.setInt(2,roomId);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				count = myRs.getInt("sum");
			}
		} finally {
			DBManager.close(myCon,myStmt,myRs);
		}
		return count;
	}
}
