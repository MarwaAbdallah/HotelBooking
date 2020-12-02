package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import db.DBManager;
import model.Location;
public class LocationDao {
//	@Resource(name="jdbc/HotelBookingDB")
	private DataSource datasource;
	
	public LocationDao(DataSource datasource) {
		this.datasource = datasource;
	}
	public Location getLocationPerCityAndCountry(Location location) throws Exception {
		Location loc = null;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
	
		
		try {
			myCon = datasource.getConnection();
			String sql = "SELECT * FROM locations"+
					"WHERE city=? AND country=?";
			myStmt= myCon.prepareStatement(sql);
			myStmt.setString(1,location.getCity());
			myStmt.setString(2, location.getCountry());
			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				String city = myRs.getString("city");
				String country = myRs.getString("country");
				loc = new Location(city,country);
			}
			else {
				throw new Exception("Could not find location { city: "+
						location.getCity()+
						", country: "+location.getCountry()+"} ");
			}
			return loc;
		} finally {
			//close(myCon, myStmt, myRs); // close JDBC objects
			DBManager.close(myCon,myStmt,myRs);
		}
	}
	
	public List<Location> getAllLocations() throws Exception {
		List<Location> locs = new ArrayList<>();
		
		Connection myCon = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "SELECT * FROM locations";
			myStmt = myCon.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				String city = myRs.getString("city");
				String country = myRs.getString("country");
				Location loc = new Location(city, country);
				locs.add(loc);
			}
			return locs;
		} finally {
			//close(myCon,myStmt,myRs); // close JDBC objects
			DBManager.close(myCon,myStmt,myRs);
		}
		
	}
	public List<Location> getLocationsPerCountry(String country) throws Exception {
		List<Location> locs = new ArrayList<>();
		
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myCon = datasource.getConnection();
			String sql = "SELECT * FROM locations"+
					"WHERE country=?";
			myStmt = myCon.prepareStatement(sql);
			myStmt.setString(1,country);
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				String city = myRs.getString("city");
				Location loc = new Location(city, country);
				locs.add(loc);
			}
			return locs;
		} finally {
			//close(myCon,myStmt,myRs); // close JDBC objects
			DBManager.close(myCon,myStmt,myRs);
		}
		
	}
	
	

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if(myRs!=null) {
				myRs.close();
			}
			if(myStmt!=null) {
				myStmt.close();
			}
			if(myConn!=null) {
				myConn.close();  //doesn't really close it, put it back in connection pool so its available for someone else to use
			}
			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
	}
		

}
