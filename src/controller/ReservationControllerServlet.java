package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


import dao.HotelDao;
import dao.ReservationDao;
import dao.RoomDao;
import dao.UserDao;
import model.Hotel;
import model.Reservation;
import model.Room;
import model.User;
import util.RoomInfo;

@WebServlet("/ReservationControllerServlet")
public class ReservationControllerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private ReservationDao ReservationDaoUtil;
	private HotelDao HotelDaoUtil;
	private UserDao UserDaoUtil;
	private RoomDao RoomDaoUtil ;
	@Resource(name="jdbc/HotelBookingDB")
	private DataSource dataSource;	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationControllerServlet() {
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
			ReservationDaoUtil = new ReservationDao(dataSource);
			RoomDaoUtil = new RoomDao(dataSource);
			HotelDaoUtil = new HotelDao(dataSource);
			UserDaoUtil = new UserDao(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String theCommand = request.getParameter("command");
		System.out.println("command from chekcOUT is : "+theCommand);
		try {
			if (theCommand == null) {
				theCommand="DISPLAY_RESERVATIONS";
			}
			
			switch (theCommand) {
				case "SEND_CHECKOUT" :
					getCheckoutPage(request,response);
					break;
				case "RESERVE_ROOM_CHOSEN":
					doReserve(request,response);
					break;
					
			} 
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	private void postReservation(HttpServletRequest req, HttpServletResponse resp,
			int hotelId, Reservation res) {
		/** 
		 * Parameters userId:1006
			fromDate:2016-08-16
			toDate:2016-08-16
			customerEmail:fcwqfnwqjiiji
		* 	
			json Body : 
			{
			    "booked": false,
			    "roomNo": 5000,
			    "isBooked": false,
			    "price": 340.07
			}
		 * **/
		String json = "{"
				+ "\"booked\": true, "
				+ "\"roomNo\": "+res.getBedding().getId()+", "
				+ "\"isBooked\": false,"
				+ "\"price\": 340.07 }";
		String url = "http://localhost:8080/reservation";
		String param = "?userId=1006&fromDate=2016-08-16&toDate=2016-08-16&customerEmail=ohhellllYeah";
		url = url + param;
		  System.out.println("nSending 'POST' request to URL : " + url);

		try {
			Hotel hotel = HotelDaoUtil.getHotel(hotelId);
			//if (hotel.getName().equals("Obelisk Resort")) { 
				URL obj = new URL(url);
			  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			 
			  // Setting basic post request
			  con.setRequestMethod("POST");
			  con.setRequestProperty("User-Agent", "Mozilla/5.0");
			  con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			  con.setRequestProperty("Content-Type","application/json");

			  // Send post request
			  con.setDoOutput(true);
			  DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			  wr.writeBytes(json);
			  wr.flush();
			  wr.close();
			 
			  int responseCode = con.getResponseCode();
			  System.out.println("nSending 'POST' request to URL : " + url);
			  System.out.println("Post Data : " + json);
			  System.out.println("Response Code : " + responseCode);
			 
			  BufferedReader in = new BufferedReader(
			          new InputStreamReader(con.getInputStream()));
			  String output;
			  StringBuffer response = new StringBuffer();
			 
			  while ((output = in.readLine()) != null) {
			   response.append(output);
			  }
			  in.close();
			  
			  //printing result from response
			  System.out.println(response.toString());
			    //}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void doReserve(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String url = "/WEB-INF/views/confirmation.jsp";
		HttpSession mySession = request.getSession(false);
		
		String userEmail = (String) mySession.getAttribute("user");
		System.out.println("\nUSER: "+userEmail);
		Cookie[] cookies = request.getCookies();
		Map<String, String> myCookie = new HashMap<>();
		for (Cookie c : cookies) {
			myCookie.put(c.getName(), c.getValue());
			System.out.print("cookie : "+c.getName()+" + "+c.getValue());
			System.out.print("myCookie : "+myCookie.get(c.getName()));
		}
		int hotelId = Integer.parseInt(myCookie.get("hotelId"));
		int roomId = Integer.parseInt(myCookie.get("roomId"));
		double roomPrice = Double.parseDouble(myCookie.get("roomPrice"));
		LocalDate checkIn = LocalDate.parse(myCookie.get("checkIn"));
		LocalDate checkOut = LocalDate.parse(myCookie.get("checkOut"));

		try {
			User user = UserDaoUtil.getUserfromPrincipal(userEmail);
			Room room = RoomDaoUtil.getRoom(hotelId, roomId);
			Hotel hotel = HotelDaoUtil.getHotel(hotelId);
			Reservation reserv = new Reservation(checkIn,checkOut,user,hotel,room);
			ReservationDaoUtil.createReservation(reserv);
			
			postReservation(request, response,hotelId, reserv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String data = "sucessfull!";
		request.setAttribute("data",data);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		try {
			requestDispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	 static void getCheckoutPagePostSignIn(HttpServletRequest request, HttpServletResponse response) {
		 String url="/WEB-INF/views/checkoutsignin.jsp";
		 RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 static void getCheckoutPage(HttpServletRequest request, HttpServletResponse response) {
		String url = "";
		//if (request.getRemoteUser()!= null) {
			//url="/WEB-INF/views/checkout.jsp";
		//} else {
			url="/WEB-INF/views/checkoutsignin.jsp";
			String hotelId = request.getParameter("hotelId");
			String  roomId = request.getParameter("roomId");
			String hotelName = request.getParameter("hotelName");
			String roomNo = request.getParameter("roomBedNo");
			String  roomPrice = request.getParameter("roomPrice");
			System.out.println("hotelName = "+hotelName);
			hotelName = hotelName.replaceAll("\\s", "");;
			
			
			Cookie[] cookies = request.getCookies();
//			if (cookies != null) {
				Map<String, String> newCook = new HashMap<>();
				newCook.put("roomId", roomId);
				newCook.put("roomPrice", roomPrice);
				newCook.put("roomBedsNo", roomNo);
				newCook.put("hotelId", hotelId);
				newCook.put("hotelName", hotelName);
				for (String s : newCook.keySet()) {
					int indx =-1;
					for (int j = 0; j< cookies.length; j++) {
						if (s.equals(cookies[j].getName())) {
							indx = j;
						}
					}
					// If cookie present (indx is its index in cookie array
					// modify it. Otherwise, create new one
					if (indx > 0) {
						cookies[indx].setValue(newCook.get(s));
					} else {
						Cookie cookie = new Cookie(s, newCook.get(s));
						response.addCookie(cookie);
						request.setAttribute(s, newCook.get(s)); // to access in same request
					}
				}
		
		
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		try {
			requestDispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
