package service;

import dao.BookingDAO;
import java.util.List;
import model.Booking;
import model.CumRap;
import model.HeThongRap;
import model.LichChieu;
import model.Movie;
import model.RapPhim;
import model.Showtime;

public class BookingService implements IBookingService {

    private BookingDAO bookingDAO = new BookingDAO();

    @Override
    public void addNewBooking(Booking b) {
        bookingDAO.addNewBooking(b);
    }

    @Override
    public List<HeThongRap> getAllHeThongRap() {
        return bookingDAO.getAllHeThongRap();
    }

    @Override
    public List<CumRap> getCumRapByHeThongRapId(int heThongRapId) {
        return bookingDAO.getCumRapByHeThongRapId(heThongRapId);
    }

    @Override
    public List<RapPhim> getRapByCumRapId(int cumRapId) {
        return bookingDAO.getRapByCumRapId(cumRapId);
    }

    @Override
    public List<LichChieu> getLichChieu(int maPhim, int maRap, String ngayChieu) {
        return bookingDAO.getLichChieu(maPhim, maRap, ngayChieu);
    }

    public List<LichChieu> getLichChieuByRapPhimAndPhim(int maRap, int maPhim) {
        return bookingDAO.getLichChieuByRapPhimAndPhim(maRap, maPhim);
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> res = bookingDAO.getBookingByUserId(userId);
        ShowtimeService showTimeService = new ShowtimeService();
        MovieService movieService = new MovieService();
        RapPhimService rapPhimService = new RapPhimService();
        for (Booking booking : res) {
            Showtime showtime = showTimeService.getShowtimeInformationByID(booking.getMa_lich_chieu());
            RapPhim rp = rapPhimService.selectRapPhimByID(showtime.getMa_rap());
            Movie mv = movieService.getMovie(showtime.getMa_phim());
            showtime.setRapPhim(rp);
            booking.setMovie(mv);
            booking.setShowtime(showtime);
        }
        return res;
    }

    public static void main(String[] args) {
        BookingService bookingService = new BookingService();
        List<Booking> bookings = bookingService.getBookingsByUserId(1);
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

}
