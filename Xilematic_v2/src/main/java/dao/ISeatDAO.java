package dao;

import java.util.List;
import model.Seat;

public interface ISeatDAO {

    public List<Seat> selectSeatsByCinemaId(int cinemaId);

    public Seat selectSeatByID(int ma_ghe);

    public void addSeatIntoCinema(int ma_rap, int so_luong_ghe, String ten_ghe_vip);

    public boolean deleteSeat(int ma_ghe);

    public boolean updateSeat(Seat ghe);

    public List<String> selectSeatnameByCinemaId(int ma_rap);

    public int getTotalSeatInCinema(int ma_rap);

    public List<Seat> getSeatForPage(int ma_rap, int currentPage, int pageSize);
}
