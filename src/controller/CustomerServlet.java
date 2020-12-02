package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dao.HotelDao;
import dao.ReservationDao;
import dao.UserDao;
import model.Reservation;
import model.User;

/**
 * Servlet implementation class AgentServlet
 */
@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao UserDaoUtil;
	private HotelDao HotelDaoUtil;
	private ReservationDao ReservationDaoUtil;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	@Resource(name = "jdbc/HotelBookingDB")
	private DataSource dataSource;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		// create an instance of userado and pass in the pool
		try {

			UserDaoUtil = new UserDao(dataSource);
			HotelDaoUtil = new HotelDao(dataSource);
			ReservationDaoUtil = new ReservationDao(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String command = request.getParameter("command");
		System.out.println("INSIDE Customer servlet, command= "+command);
		if (command == null) {
			command = "GET_HOME_CUSTOMER"; // default behavior
		}
		switch (command) {
			case "GET_HOME_CUSTOMER" :
				getCustomerHomePage(request,response);
				break;
			case "EDIT_ACCOUNT" :
			//	doReserve(request,response);
				break;
			case "CANCEL_RESERVATION" :
			//	doReserve(request,response);
				break;
		}
	}

	private void getCustomerHomePage(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Reservation> reservations = new ArrayList<>();
		String email = request.getRemoteUser();
		System.out.println("userPrincipal is :"+email);
		String url = "myaccount.jsp";
		if (email != null) {
			try {
				User user = UserDaoUtil.getUserfromPrincipal(email);
				reservations = ReservationDaoUtil.getAllReservationByUser(user);
				request.setAttribute("reservations", reservations);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request,response);
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


		

}
