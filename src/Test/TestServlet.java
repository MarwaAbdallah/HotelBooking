package Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.ConnectionPool;

import db.DBManager;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Define datasource / connection pool   have to MATCH META-INF/context.xml
	@Resource(name="jdbc/HotelBookingDB")

	private DataSource datasource ;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// 1) Setup printwriter to send data to browser
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		// 2) Connect to DB
		Connection myConn = null;
		Statement myStmt =null;
		ResultSet myRs = null;
		try {
			myConn= datasource.getConnection();
		// 3 Create SQL statements
			String sql = "select * from hotellocation;";
			myStmt=myConn.createStatement();
		
		// 4) execute SQL query
			out.println("Executing : "+sql);
			myRs = myStmt.executeQuery(sql);
		
		//5) Process result set
			while(myRs.next()) {
				String city = myRs.getString("city");
				String country = myRs.getString("country");
				out.println(city+" "+country);
			}
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
