package service;

import dao.BookingDAO;
import java.util.List;
import model.Booking;
import model.CumRap;
import model.HeThongRap;
import model.LichChieu;
import model.RapPhim;

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
    public List<LichChieu> getLichChieu( int maPhim,int maRap, String ngayChieu) {
        return bookingDAO.getLichChieu( maPhim,maRap, ngayChieu);
    }
    public List<LichChieu> getLichChieuByRapPhimAndPhim(int maRap, int maPhim){
    return  bookingDAO.getLichChieuByRapPhimAndPhim(maRap, maPhim);
            }

}
