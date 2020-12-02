package util;

public class RoomInfo {

	private int numBeds;
	private int guestCapacity;
	
	public RoomInfo(int numBeds, int guestCapacity) {
		this.numBeds = numBeds;
		this.guestCapacity = guestCapacity;
	}

	public int getNumBeds() {
		return numBeds;
	}

	public void setNumBeds(int numBeds) {
		this.numBeds = numBeds;
	}

	public int getGuestCapacity() {
		return guestCapacity;
	}

	public void setGuestCapacity(int guestCapacity) {
		this.guestCapacity = guestCapacity;
	}
	
	
}
