package controller;

import java.io.IOException;
import java.time.LocalDate;
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
import javax.sql.DataSource;

import dao.BedDao;
import dao.HotelDao;
import dao.LocationDao;
import dao.RoomDao;
import model.Hotel;
import model.Location;
import model.Room;
import util.HotelInfo;
import util.RoomInfo;




/**
 * Servlet implementation class HotelControllerServlet
 */
@WebServlet("/HotelControllerServlet")
public class HotelControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HotelDao HotelDaoUtil;
	private RoomDao RoomDaoUtil;
	private BedDao BedDaoUtil;
	
	@Resource(name="jdbc/HotelBookingDB")
	private DataSource dataSource;	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelControllerServlet() {
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

			HotelDaoUtil = new HotelDao(dataSource);
			RoomDaoUtil = new RoomDao(dataSource);
			BedDaoUtil = new BedDao(dataSource);
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
		String theCommand = request.getParameter("command");
		System.out.println("command is : "+theCommand);
		try {
			if (theCommand == null) {
				theCommand="LIST_HOTELS";
			}
			
			switch (theCommand) {
				case "LIST_HOTELS" :
					getListHotelsAvailableRoomCount(request,response);
					break;
				case "LIST_ROOMS" :
					getListRoomsAvailableinHotel(request,response);
					break;
					
			} 
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			
		

	}

	private void getListRoomsAvailableinHotel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
		// TODO Auto-generated method stub
		String url="/WEB-INF/views/reserve.jsp";
		int hotelId = Integer.parseInt(request.getParameter("hotelId"));
		List<Room> rooms = RoomDaoUtil.getAvailableRoomsForHotel(hotelId);
		Hotel hotel = HotelDaoUtil.getHotel(hotelId);
		Map<Room,RoomInfo> map = new HashMap<>();
		
		if((hotel!= null)&&(rooms != null)) {
			for (Room room : rooms) {
				int roomId= room.getId();
				int guestCapacity = BedDaoUtil.countBedCapacityForRoom(roomId, hotelId);
				int numBeds= BedDaoUtil.countBedsForRoom(roomId, hotelId);
				RoomInfo rInf = new RoomInfo(numBeds,guestCapacity);
				if (numBeds !=0) {
					map.put(room, rInf);
				}
			}
			request.setAttribute("hotel", hotel);
			request.setAttribute("ROOM_MAP", map);
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
	
	private void getListHotelsAvailableRoomCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		String checkIn = request.getParameter("checkIn");
		String checkOut = request.getParameter("checkOut");
		String[] cityCountry = request.getParameter("location").split(",");
		String city=cityCountry[0];
		String country=cityCountry[1];
		Map<Hotel, HotelInfo> map = new HashMap<>();
		List<Hotel> hotels = HotelDaoUtil.getHotelsPerLocation(city, country);
		for (Hotel h : hotels) {
			int hotelId = h.getId();
			int count = RoomDaoUtil.getAvailableRoomCountForHotel(hotelId);
			double minPrice = RoomDaoUtil.getMinPriceForHotel(hotelId);
			HotelInfo hotelInfos = new HotelInfo(count,minPrice);
			map.put(h, hotelInfos);
		}

		Map<String, String> newCook = new HashMap<>();
		newCook.put("city",city);
		newCook.put("country",country);
		newCook.put("checkIn",checkIn);
		newCook.put("checkOut",checkOut);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			System.out.println("Cookie not null");
		    for (int i = 1; i < cookies.length; i++) {
		    	String name = cookies[i].getName();
		    	System.out.println("Cookie :"+name+", :"+cookies[i].getValue());
		    	if (newCook.containsKey(name)) {
		    		String val = newCook.get(name);
		    		cookies[i].setValue(val);
		    		response.addCookie(cookies[i]);
		    	} else {
		    		
		    	}
		    }
	    } else {
	    	System.out.println("Cookie not null");
			Cookie[] choiceCookie = new Cookie[4];
	
			choiceCookie[0] = new Cookie("city",city);
			choiceCookie[1] = new Cookie("country",country);
			choiceCookie[2] = new Cookie("checkIn",checkIn);
			choiceCookie[3] = new Cookie("checkOut",checkOut);
	
			response.addCookie(choiceCookie[0]);
			response.addCookie(choiceCookie[1]);
			response.addCookie(choiceCookie[2]);
			response.addCookie(choiceCookie[3]);
	    }
		request.setAttribute("HOTELMAP",map);
		String url="/WEB-INF/views/choosestaying.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request,response);
		
	}
	private void erasePreferenceCookie(HttpServletRequest req, HttpServletResponse resp) {
	    Cookie[] cookies = req.getCookies();
	    if (cookies != null)
	        for (Cookie cookie : cookies) {
	            cookie.setValue("");
	            cookie.setPath("/");
	            cookie.setMaxAge(0);
	            resp.addCookie(cookie);
	        }
	}


}
