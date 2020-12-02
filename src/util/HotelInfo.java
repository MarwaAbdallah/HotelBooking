package util;

public class HotelInfo {
	int count; // num of (available) room in hotel;
	double minPrice; // minimum price for hotel
	
	public HotelInfo(int count, double minPrice) {
		this.count = count;
		this.minPrice = minPrice;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	
	

}
