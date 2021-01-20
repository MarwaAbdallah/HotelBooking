package util;

import java.time.LocalDate;

public class ObeliskReservation {
    private LocalDate fromDate;
    private LocalDate toDate;
    private String customerEmail;
    private long userId;
    
    public ObeliskReservation(String customerEmail, long userId,
    		LocalDate fromDate, LocalDate toDate) {
    	this.customerEmail = customerEmail;
    	this.userId = userId;
    	this.fromDate = fromDate;
    	this.toDate = toDate;
 
    }    
}
