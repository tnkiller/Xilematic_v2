package service;

import java.util.List;
import model.Seat;

public interface ISeatService {

    public Seat selectSeatByID(int ma_ghe);

    public void addSeatIntoCinema(int ma_rap, int so_luong_ghe, String ten_ghe_vip);

    public boolean deleteSeat(int ma_ghe);

    public boolean updateSeat(Seat ghe);

    public List<Seat> getSeatsByCinemaId(int ma_phim);

    public int getTotalSeatInCinema(int ma_rap);

    public List<Seat> getSeatForPage(int ma_rap, int currentPage, int pageSize);
}
