package dao;

import java.util.List;
import model.Booking;
import model.CumRap;
import model.HeThongRap;
import model.LichChieu;
import model.RapPhim;

public interface IBookingDAO {

    public void addNewBooking(Booking b);
     public List<HeThongRap> getAllHeThongRap();
    public List<CumRap> getCumRapByHeThongRapId(int heThongRapId);
    public List<RapPhim> getRapByCumRapId(int cumRapId);
    public List<LichChieu> getLichChieu( int maPhim,int maRap, String ngayChieu);
}
