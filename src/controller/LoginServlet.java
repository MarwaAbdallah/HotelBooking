package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import dao.BedDao;
import dao.HotelDao;
import dao.RoomDao;
import dao.UserDao;
import model.Role;

import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao UserDaoUtil;
    /**
     * @see HttpServlet#HttpServlet()
     */
	@Resource(name="jdbc/HotelBookingDB")
	private DataSource dataSource;	
       
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		// create an instance of userado and pass in the pool
		try {

			UserDaoUtil  = new UserDao(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		String other = request.getParameter("name");
		String email = request.getParameter("email");

		if (action == null) {
			action="REQUEST_SIGNIN"; // default behavior
		}
		switch(action) {
			case "REQUEST_SIGNIN":
				getSignInPage(request,response);
				break;
			case "SIGNIN":
				doSignInUser(request,response);
				break;
			case "SIGNOUT":
				doSignOutUser(request,response);
				break;
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void doSignOutUser(HttpServletRequest request, 
			HttpServletResponse response)  throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Authenticate, then sends to page depending to role and user

		//String email = request.getRemoteUser();
		//HttpSession session = request.getSession(false);
		System.out.println("Inside Logout");
		HttpSession session = request.getSession(false);
		if (session!=null) {
			session.invalidate();
		}
		request.logout();
		RequestDispatcher dispatcher = request.getRequestDispatcher("/HotelManagementSystem");
		dispatcher.forward(request,response);
	}
	
	private void doSignInUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String pwd = request.getParameter("password");

		try {

			System.out.println("In doSignInuser, user/ password : "+
					email+" / "+pwd);
			request.login(email, pwd);
			String userPrincipal = request.getUserPrincipal().getName();
			HttpSession session = request.getSession();
			session.setAttribute("usrPrincipal", userPrincipal);
			
			Role role = UserDaoUtil.getUserRole(userPrincipal);
			String rolename = role.getRole();
			System.out.println("user / role"+userPrincipal+" / "+rolename);
			switch (rolename) {
				case "Admin":
					session.setAttribute("role", "Admin");
					forwardToRoleBasedServlet(request,response);
					break;
				case "HotelAgent":
					session.setAttribute("role", "HotelAgent");
					forwardToRoleBasedServlet(request,response);
					break;
				case "Customer":
					session.setAttribute("role", "Customer");
					// if user was sent to login from checkout page, send him back after login
					// otherwise home page
					String from= request.getParameter("from");
					if (from != null && !from.isEmpty()) {
						System.out.println("supposed to : response.sendRedirect(from)");
						ReservationControllerServlet.getCheckoutPage(request, response);
					} else {
						forwardToRoleBasedServlet(request, response);
					}
					break;
			}
			
		} catch (ServletException e) {
            //perhaps redirect to another page to login failure
            throw new ServletException(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
		}

		
	
	

	private void getSignInPage(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url="signin.jsp";
		String from= request.getParameter("fromb");
		if((request.getSession(false)!= null)&(request.getRemoteUser()!=null)) {
			System.out.println("User already signed in !");
			System.out.println("User is !"+request.getRemoteUser());
			forwardToRoleBasedServlet(request,response);
			
		} else {
			if(from != null) {
				request.setAttribute("from",from);
			}
	
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
			requestDispatcher.forward(request, response);
		}
		
	}
	private void forwardToRoleBasedServlet(HttpServletRequest request, 
			HttpServletResponse response) {
		//User user = UserDaoUtil
		String url = "";
		User user;
		
		try {
			user = UserDaoUtil.getUserfromPrincipal(request.getRemoteUser());
			
			switch(user.getRole()) {
			case "Admin":
				url="/AdminServlet";
				break;
			case "HotelAgent":
				url="/AgentServlet";
				break;
			case "Customer":
				url="/CustomerServlet";
				break;
		}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("forward to : "+url);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		try {
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
