package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dao.HotelDao;
import dao.UserDao;
import model.Hotel;
import model.Role;
import model.User;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao UserDaoUtil;
	private HotelDao HotelDaoUtil;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	@Resource(name = "jdbc/HotelBookingDB")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
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
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			{
		// TODO Auto-generated method stub
		String command = request.getParameter("command");
		if (command == null) {
			command = "GET_HOME"; // default behavior
		}
		System.out.println("INSIDE adminservlet, command= "+command);
		switch (command) {
			case "GET_HOME" :
				getAdminHomePage(request,response);
				break;
			case "GET_USER_MGMT_ADMIN" :
				getAllUsersMgmt(request,response);
				break;
			case "GET_HOTEL_MGMT_ADMIN" :
				getAllHotelsMgmt(request,response);
				break;
			case "EDIT_HOTEL":
				editHotel(request,response);
				break;
			case "DELETE_HOTEL":
				deleteHotel(request,response);
				break;
			case "GET_EDIT_USER":
				getEditUser(request,response);
				break;
			case "EDIT_USER":
				editUser(request,response);
				break;
			case "DELETE_USER":
				deleteUser(request,response);
				break;
			case "GET_ADD_USER":
				getAddUser(request,response);
				break;
			case "ADD_USER":
				addUser(request,response);
				break;
		}

	}



	private void addUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("In -- ADD User");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		System.out.println("role ");
		System.out.println(role);
		String message;
		try {
			if (!UserDaoUtil.isUserPresentInDb(email)) {
				User user = new User(email,"password",role);
				UserDaoUtil.addUser(user);
				message = "User "+email+" successfully added";
				request.setAttribute("message", message);
				getAllUsersMgmt(request, response);
			} else {
				message = "Email "+email+" is already used,\n"
						+ "Please use another one";
				request.setAttribute("message", message);
				getAddUser(request,response);				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void getAddUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String url = "adminadduser.jsp";
		System.out.println("In get ADD User");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		try {
			List<Role> roles = UserDaoUtil.getAllRoles();
			request.setAttribute("LIST_ROLES", roles);
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void getEditUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String url = "adminuseredit.jsp";
		String email = request.getParameter("email");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		try {
			List<Role> roles = UserDaoUtil.getAllRoles();
			User user = UserDaoUtil.getUserfromPrincipal(email);
			request.setAttribute("LIST_ROLES", roles);
			request.setAttribute("USER", user);
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void editUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String email =request.getParameter("email");
		String role = request.getParameter("role");
		Boolean enabled = Boolean.valueOf(
				request.getParameter("enabled"));
		User user;
		try {
			user = UserDaoUtil.getUserfromPrincipal(email);
			user.setEmail(email);
			user.setRole(role);
			user.setEnabled(enabled);
			UserDaoUtil.editUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getAllUsersMgmt(request, response);
		
		
	}
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String userEmail =request.getParameter("email");
		User user;
		try {
			user = UserDaoUtil.getUserfromPrincipal(userEmail);
			UserDaoUtil.removeUser(user.getId());
			String message = "User "+userEmail+" was\n"
					+ "successfully deleted";
			request.setAttribute("message", message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getAllUsersMgmt(request, response);
	}
	private void deleteHotel(HttpServletRequest request, HttpServletResponse response) {
		int hotelId =Integer.parseInt(request.getParameter("hotelId"));
		HotelDaoUtil.removeHotel(hotelId);
		System.out.println("the Hotel to delete is :"+hotelId);
	}
	private void editHotel(HttpServletRequest request, HttpServletResponse response) {
		String hotelId =request.getParameter("hotelId");
		System.out.println("the Hotel to edit is :"+hotelId);
	}

	private void getAllHotelsMgmt(HttpServletRequest request, HttpServletResponse response) {
		// Get All hotels in a list, + possibility to edit or remove them
		String url="adminhotelmgmt.jsp";
		try {
			List<Hotel> hotels = HotelDaoUtil.getAllHotels();
			request.setAttribute("hotels", hotels);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		try {
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void getAllUsersMgmt(HttpServletRequest request, HttpServletResponse response) {
		// Get All users in a list, + possibility to edit or remove them
		String url="adminusermgmt.jsp";
		Map <User,Role> mapUserRole = new LinkedHashMap<>();
		try {
			List<User> users =UserDaoUtil.getAllUsers();
			Collections.sort(users);
			for (User usr : users) {
				String usrEmail = usr.getEmail();
				Role usrRole = UserDaoUtil.getUserRole(usrEmail);
				mapUserRole.put(usr, usrRole);
			}
			request.setAttribute("mapUserRole", mapUserRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		try {
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	public void getAdminHomePage(HttpServletRequest request, HttpServletResponse response)
			{
		// TODO Auto-generated method stub
		// List all Users, and Roles.
		// List all Hotels
		// Add/modify/remove users, roles and hotels
		System.out.println("INSIDE Admin HomePage");
		String url = "adminhome.jsp";
		int userCount =0;
		int hotelCount = 0;
				
		try {
			userCount = UserDaoUtil.getEnabledUsersCount();
			hotelCount = HotelDaoUtil.getHotelsCount();
		} catch (SQLException e) {
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

}
