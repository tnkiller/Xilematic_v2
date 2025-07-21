package service;

import java.util.List;
import model.Booking;
import model.CumRap;
import model.HeThongRap;
import model.LichChieu;
import model.RapPhim;

public interface IBookingService {

    public void addNewBooking(Booking b);

    public List<HeThongRap> getAllHeThongRap();

    public List<CumRap> getCumRapByHeThongRapId(int heThongRapId);

    public List<RapPhim> getRapByCumRapId(int cumRapId);

    public List<LichChieu> getLichChieu(int maPhim, int maRap, String ngayChieu);

    public List<LichChieu> getLichChieuByRapPhimAndPhim(int maRap, int maPhim);

    public List<Booking> getBookingsByUserId(int userId);

    public Booking getBookingById(int bookingId);
}
