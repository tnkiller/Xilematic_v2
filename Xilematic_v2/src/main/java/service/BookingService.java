package service;

import dao.BookingDAO;
import model.Booking;

public class BookingService implements IBookingService {

    private BookingDAO bookingDAO = new BookingDAO();

    @Override
    public void addNewBooking(Booking b) {
        bookingDAO.addNewBooking(b);
    }

}
