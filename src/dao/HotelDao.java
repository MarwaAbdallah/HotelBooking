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
import model.Hotel;

public class HotelDao {
	private DataSource datasource;
	
	public HotelDao(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public Hotel getHotel(int hotelId) throws Exception{
				// so far, no constraints on reservation dates

				Connection myCon = null;
				PreparedStatement myStmt = null;
				ResultSet myRs = null;
				Hotel hotel = null;
				
				try {
					myCon = datasource.getConnection();
					String sql = "select * from hotels WHERE id = ?";

					myStmt = myCon.prepareStatement(sql);
					myStmt.setInt(1,hotelId);
					myRs = myStmt.executeQuery();
					
					while (myRs.next()) {
						int id = myRs.getInt("id");
						String name = myRs.getString("hotel_name");
						int numStars = myRs.getInt("numStars");
						String city = myRs.getString("city_id");
						String country = myRs.getString("country_id");
						hotel = new Hotel(id,name,city,country,numStars);

					}
				} finally {
					//close(myCon,myStmt,myRs);
					DBManager.close(myCon,myStmt,myRs);
				}
				return hotel;
				
			}
	public List<Hotel> getAllHotels() throws Exception{
		List<Hotel> myHotels = new ArrayList<>();
		Connection myCon = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		
		try {
			myCon = datasource.getConnection();
			String sql = "select * from hotels";

			myStmt = myCon.createStatement();

			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				int id = myRs.getInt("id");
				String name = myRs.getString("hotel_name");
				int numStars = myRs.getInt("numStars");
				String city = myRs.getString("city_id");;
				String country = myRs.getString("country_id");;
				Hotel hotel = new Hotel(id,name,city,country,numStars);
				myHotels.add(hotel);
			}
		} finally {
			//close(myCon,myStmt,myRs);
			DBManager.close(myCon,myStmt,myRs);
		}
		return myHotels;
		
	}

	public List<Hotel> getHotelsPerLocation(String city, String country) 
	throws Exception{
		// so far, no constraints on reservation dates
		List<Hotel> myHotels = new ArrayList<>();
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		System.out.println("city: "+city+", country: "+country);
		
		try {
			myCon = datasource.getConnection();
			String sql = "select * from hotels WHERE city_id = ? AND country_id = ?";

			myStmt = myCon.prepareStatement(sql);
			myStmt.setString(1,city);
			myStmt.setString(2,country);
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				int id = myRs.getInt("id");
				String name = myRs.getString("hotel_name");
				int numStars = myRs.getInt("numStars");
				Hotel hotel = new Hotel(id,name,city,country,numStars);
				myHotels.add(hotel);
			}
		} finally {
			//close(myCon,myStmt,myRs);
			DBManager.close(myCon,myStmt,myRs);
		}
		return myHotels;
		
	}
	
	

	public int getHotelsCount() throws SQLException {
		// TODO Auto-generated method stub
		int count =0;
		Connection myCon = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "select count(*) from hotels;";
			myStmt = myCon.createStatement();
			myRs = myStmt.executeQuery(sql);
			if(myRs.next()) {
				count = myRs.getInt("count");
			}
		} finally {
			DBManager.close(myCon, myStmt, myRs);
		}
		return count;
	}

	public void removeHotel(int hotelId) {
		// TODO Auto-generated method stub
		
	}
}
