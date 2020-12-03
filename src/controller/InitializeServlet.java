package controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dao.LocationDao;
import model.Location;

/**
 * Servlet implementation class InitializeServlet
 */
@WebServlet("/InitializeServlet")
public class InitializeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LocationDao LocationDaoUtil;
//	@Resource(name="jdbc/vmviewer")  	// Define datasource/connection pool have to MATCH META-INF/context.xml
	@Resource(name="jdbc/HotelBookingDB")
	private DataSource dataSource;	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitializeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	//Initialize UserDaoUtil Right CLick -> Source -> Override/Implement methods
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		// create an instance of userado and pass in the pool
		try {
			LocationDaoUtil = new LocationDao(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {

			listLocations(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void listLocations(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get users from DB util
		List<Location> locs = LocationDaoUtil.getAllLocations();		// add users to the request
		request.setAttribute("LOCATION_LIST",locs);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request,response);
		
		
		
	}

}
