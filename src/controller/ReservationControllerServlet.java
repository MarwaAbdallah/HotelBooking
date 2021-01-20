package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

import com.fasterxml.jackson.databind.ObjectMapper;

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
				case "CANCEL_RESERVATION":
					cancelReserve(request,response);
					break;
					
			} 
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}


	private void postCreateReservation(HttpServletRequest req, HttpServletResponse resp,
			int hotelId, Reservation res) throws IOException, InterruptedException {


		String url = "http://localhost:8080/reservation";

		HttpClient httpClient = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_2)
	            .build();

        ObjectMapper objMapper = new ObjectMapper();

				
        Map<String, String> data = new HashMap<>();
        int roomId = res.getBedding().getId();
        
        data.put("fromDate", res.getFromStayDate().toString());
        data.put("toDate", res.getToStayDate().toString());
        data.put("customerEmail", res.getCustomer().getEmail());
        data.put("userId", "1008"); // ID for service account to make bookings
        data.put("roomId", String.valueOf(roomId) ); // ID for service account to make bookings

        
        String requestBody = objMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(data);
        
        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(requestBody))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();
        
        
        System.out.println("\n\n"+request.toString());
        System.out.println("\n\n"+request.uri().getQuery());
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());
		  
		
		
	}
	private void postCancelReservation(HttpServletRequest req, HttpServletResponse resp, long reservationId) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		String param = String.valueOf(reservationId);
		String url = "http://localhost:8080/reservation/"+param;
		HttpClient httpClient = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_2)
	            .build();

		 HttpRequest request = HttpRequest.newBuilder()
		            .uri(URI.create(url))
		            .header("Content-Type", "application/json")
		            .DELETE()
		            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());
		
	}
	
	private void cancelReserve(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
				String url = "/WEB-INF/views/confirmation.jsp";

				long reservationId = Long.parseLong(request.getParameter("reservationId"));
				int roomId = Integer.parseInt(request.getParameter("roomId"));
				String hotelName = request.getParameter("hotelName");
				System.out.println("Hotel name: "+hotelName);
				try {
					ReservationDaoUtil.cancelReservation(reservationId, roomId);

					if(Objects.equals(hotelName , "Obelisk Resort")) {
						System.out.println("Oblesik Resort is the name of the hotel we are cancelling"
								+ "res from");
						postCancelReservation(request, response,reservationId);
						
					} else {
						System.out.println("Oblesik Resort is not hotel name, it was : "
								+ hotelName );
					}
					
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
			if(Objects.equals(hotel.getName(), "Obelisk Resort")) {
				System.out.println("Oblesik Resort is the name of the hotel we are creating"
						+ "res from");
				postCreateReservation(request, response,hotelId, reserv);
			} else {
				System.out.println("Oblesik Resort is not hotel name, it was : "
						+ hotel.getName());
			}
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
