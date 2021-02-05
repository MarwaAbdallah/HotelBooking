package controller;

import java.io.IOException;
import java.sql.SQLException;
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
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao UserDaoUtil;
	private ReservationDao ReservationDaoUtil;
	private HotelDao HotelDaoUtil;
	@Resource(name = "jdbc/HotelBookingDB")
	private static DataSource dataSource;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		try {

			UserDaoUtil = new UserDao(dataSource);
			ReservationDaoUtil = new ReservationDao(dataSource);
			HotelDaoUtil = new HotelDao(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user;
		String url = "";
		try {
			user = UserDaoUtil.getUserfromPrincipal(request.getRemoteUser());
			
			switch(user.getRole()) {
			case "Admin":
				System.out.println("User Servlet for admin :"+user.getEmail());
				getAdminHome(request,response);
				break;
			case "HotelAgent":
				System.out.println("User Servlet for agnt servlet :"+user.getEmail());
				getAgentHome(request,response);
				break;
			case "Customer":
				System.out.println("User Servlet for Customer :"+user.getEmail());
				getCustomerHome(request,response);
				break;
			}
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
	}

	private void getCustomerHome(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Reservation> reservations = new ArrayList<>();
		String email = request.getRemoteUser();
		System.out.println("userPrincipal is :"+email);
		String url = "/WEB-INF/views/myaccount.jsp";
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
		try {
			dispatcher.forward(request,response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void getAgentHome(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// TODO Auto-generated method stub
		List<Reservation> reservations = new ArrayList<>();
		String email = request.getRemoteUser();
		
		System.out.println("userPrincipal is :"+email);
		String url = "/WEB-INF/views/agenthome.jsp";
		if (email != null) {
			try {
				reservations = ReservationDaoUtil.
						getAllReservationByHotelBasedOnEmployee(email);
				String hotelName = HotelDaoUtil.getHotelNameFromEmployeeEmail(email);
				request.setAttribute("reservations", reservations);
				request.setAttribute("hotelName", hotelName);
			}catch (Exception exc) {
				throw new ServletException(exc);
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		try {
			dispatcher.forward(request,response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void getAdminHome(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("INSIDE Admin HomePage");
		String url = "/WEB-INF/views/adminhome.jsp";
		int userCount =0;
		int hotelCount = 0;
				
		try {
			userCount = UserDaoUtil.getEnabledUsersCount();
			hotelCount = HotelDaoUtil.getHotelsCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
				
		request.setAttribute("EnabledUsersCount", userCount);
		request.setAttribute("hotelCount", hotelCount);
		System.out.println("lets forward to adminhone  now");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		try {
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
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

}
