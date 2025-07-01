package service;

import dao.SeatDAO;
import java.util.List;
import model.Seat;

public class SeatService implements ISeatService {

    private SeatDAO seatDAO = new SeatDAO();

    @Override
    public List<Seat> getSeatsByCinemaId(int ma_phim) {
        return seatDAO.selectSeatsByCinemaId(ma_phim);
    }

    @Override
    public void addSeatIntoCinema(int ma_rap, int so_luong_ghe, String ten_ghe_vip) {
        seatDAO.addSeatIntoCinema(ma_rap, so_luong_ghe, ten_ghe_vip);
    }

    @Override
    public boolean deleteSeat(int ma_ghe) {
        return seatDAO.deleteSeat(ma_ghe);
    }

    @Override
    public boolean updateSeat(Seat ghe) {
        return seatDAO.updateSeat(ghe);
    }

    @Override
    public int getTotalSeatInCinema(int ma_rap) {
        return seatDAO.getTotalSeatInCinema(ma_rap);
    }

    @Override
    public List<Seat> getSeatForPage(int ma_rap, int currentPage, int pageSize) {
        return seatDAO.getSeatForPage(ma_rap, currentPage, pageSize);
    }

    @Override
    public Seat selectSeatByID(int ma_ghe) {
        return seatDAO.selectSeatByID(ma_ghe);
    }

}
