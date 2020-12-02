package db;
import java.sql.*;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DBManager {

	//private String url = "jdbc:postgresql://192.168.0.200/HotelBookingDB";
	//private String user = "postgres";
	//private String password = "Password&11";
	static Connection connection = null;
	
	public static void connect() {
		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/HotelBookingDB");
			connection = ds.getConnection();
			if (connection!= null) {
				System.out.println("Connected successfully to postgres server");
			} else {
				System.out.println("Unsuccesful connection to posgres server");

			}
		} catch (Exception e) {
			e.printStackTrace();
			}

	}

	public static void close(Connection myCon, Statement myStmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if(myRs!=null) {
				myRs.close();
			}
			if(myStmt!=null) {
				myStmt.close();
			}
			if(myCon!=null) {
				myCon.close();  //doesn't really close it, put it back in connection pool so its available for someone else to use
			}
			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
	}

	public static void closeb(Connection myCon, PreparedStatement myStmt1, 
			PreparedStatement myStmt2,ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if(myRs!=null) {
				myRs.close();
			}
			if(myStmt1!=null) {
				myStmt1.close();
			}
			if(myStmt2!=null) {
				myStmt2.close();
			}
			if(myCon!=null) {
				myCon.close();  //doesn't really close it, put it back in connection pool so its available for someone else to use
			}
			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		
	}
	

	
}
