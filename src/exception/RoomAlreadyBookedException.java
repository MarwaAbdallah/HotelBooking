package exception;

public class RoomAlreadyBookedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RoomAlreadyBookedException(int roomId, int hotelId ) {
		super("Room: "+roomId+", from Hotel: "+hotelId+" already Booked");
	}

}
