package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dao.UserDao;
import model.User;

class AuthenticationServletTest {
	private AuthenticationServlet authServlet;
	@Mock
	UserDao UserDaoUtil;
	@Mock 
	User user;
	@BeforeEach
	public void setUpBeforeClass() {
		authServlet = new AuthenticationServlet();
	}

	@Test
	void testGet() throws ServletException, IOException {
		
		String url="/WEB-INF/views/signin.jsp";
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
		when(request.getRequestDispatcher(url)).thenReturn(requestDispatcher);;
		when(request.getParameter("action")).thenReturn("REQUEST_SIGNIN");
		
		authServlet.doGet(request, response);
		
		assertAll(
				() -> verify(requestDispatcher).forward(request, response));
		
	}
	@Test
	void testUserSignout() throws ServletException, IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

				
		when(request.getCookies()).thenReturn(null);


		when(request.getRequestDispatcher("/HotelManagementSystem")).thenReturn(requestDispatcher);
		
		System.out.println("Calling the method getSignInPage "
				+ "with non auth user");

		authServlet.doSignOutUser(request, response);
		assertAll(
				() -> verify(requestDispatcher).forward(request, response));
		String result = sw.getBuffer().toString().trim();

		System.out.println("Result: " + result);
		
		
	}
	@Test
	void testgetSignInPageNonAuthenticatedUser() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher requestDispatcher1 = mock(RequestDispatcher.class);
		RequestDispatcher requestDispatcher2 = mock(RequestDispatcher.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		String url="/AdminServlet";
		User adminuser = new User("email@email.com","Admin", true);
				
		when(request.getParameter("action")).thenReturn("REQUEST_SIGNIN");
		when(request.getParameter("fromResource")).thenReturn("bookingPage");
		when(request.getParameter("fromResource")).thenReturn("bookingPage");
		when(request.getParameter("fromResource")).thenReturn("bookingPage");
		when(request.getRemoteUser()).thenReturn("user");
		when(request.getSession(false)).thenReturn(session);
		when(UserDaoUtil.getUserfromPrincipal(request.getRemoteUser())).thenReturn(adminuser);
		when(user.getRole()).thenReturn("Admin");
		when(request.getRequestDispatcher("/WEB-INF/views/signin.jsp")).thenReturn(requestDispatcher1);
		when(request.getRequestDispatcher(url)).thenReturn(requestDispatcher2);
		
		authServlet.getSignInPage(request, response);
		assertAll(
				() -> verify(requestDispatcher1).forward(request, response),
				() -> verify(requestDispatcher2).forward(request, response));
		String result = sw.getBuffer().toString().trim();

		System.out.println("Result: " + result);

		
	}
	@Test
	void testUserGetSignInPage() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);
		
	}
	@Test
	void testForwardToRoleBasedServlet() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);
		
		
	}

}
