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
import model.Hotel;
import model.Reservation;
import model.User;

/**
 * Servlet implementation class AgentServlet
 */
@WebServlet("/AgentServlet")
public class AgentServlet extends HttpServlet {
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
    public AgentServlet() {
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
		String action = (String) request.getAttribute("action");
		System.out.println("INSIDE agent servlet, action= "+action);
		if (action == null) {
			action = "GET_HOME_AGENT"; // default behavior
		}
		switch (action) {
			case "GET_HOME_AGENT" :
				getAgentHomePage(request,response);
				break;
		}
	}

	private void getAgentHomePage(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException {
		// TODO Auto-generated method stub
		List<Reservation> reservations = new ArrayList<>();
		String email = request.getRemoteUser();
		System.out.println("userPrincipal is :"+email);
		String url = "/WEB-INF/views/agenthome.jsp";
		if (email != null) {
			try {
				reservations = ReservationDaoUtil.
						getAllReservationByHotelBasedOnEmployee(email);
				request.setAttribute("reservations", reservations);
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
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
