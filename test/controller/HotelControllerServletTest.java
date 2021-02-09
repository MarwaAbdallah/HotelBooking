package controller;
import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import controller.HotelControllerServlet;

class HotelControllerServletTest {
	private HotelControllerServlet servlet;
	@BeforeClass
	public void setUpBeforeClass() {
		servlet = new HotelControllerServlet();
	}
	@Test
	void testGet() {
		//fail("Not yet implemented");
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
	}

}
