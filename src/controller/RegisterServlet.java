package controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import dao.UserDao;
import model.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao UserDaoUtil;   
    @Resource(name="jdbc/HotelBookingDB")
	private DataSource dataSource;	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String action = request.getParameter("action");
		if (action == null) {
			action="REQUEST_SIGNUP"; // default behavior
		}
		switch(action) {
			case "REQUEST_SIGNUP":
				getRegisterPage(request,response);
				break;
			case "REGISTER":
				doRegisterUser(request,response);
				break;
		}
	}

	private void doRegisterUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url="";
		String email = request.getParameter("email");
		String pwd = request.getParameter("password");
		// verify if email already exist. if it does resend to sign up page with indication of pb
		try {
			if (!UserDaoUtil.isUserPresentInDb(email)) {
				User user = new User(email,pwd,"Customer");
				UserDaoUtil.addUser(user);
				url ="/WEB-INF/views/myaccount.jsp";
				request.login(email, pwd);
				
				HttpSession session = request.getSession();
				session.setAttribute("usrPrincipal", email);
				session.setAttribute("user", email);
			} else {
				url = "/WEB-INF/views/signup.jsp";
				String message = "Email "+email+" is already used,"
						+ "Please use another one";
				request.setAttribute("message", message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		requestDispatcher.forward(request, response);
	}

	private void getRegisterPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url="/WEB-INF/views/signup.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
