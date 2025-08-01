package service;

import java.util.List;

import dao.ShowtimeDAO;
import model.Showtime;

public class ShowtimeService implements IShowtimeService {

    private ShowtimeDAO showtimeDAO = new ShowtimeDAO();

    @Override
    public Showtime getShowtimeInformationByID(int ma_lich_chieu) {
        return showtimeDAO.getShowtimeInformationByID(ma_lich_chieu);
    }

    @Override
    public String getNameCinemaGroupByShowTimeID(int ma_lich_chieu) {
        return showtimeDAO.getNameCinemaGroupByShowTimeID(ma_lich_chieu);
    }

    @Override
    public String getCinemaNameByShowtimeID(int ma_lich_chieu) {
        return showtimeDAO.getCinemaNameByShowtimeID(ma_lich_chieu);
    }

    @Override
    public String getMoiveNameByShowtimeID(int ma_lich_chieu) {
        return showtimeDAO.getMoiveNameByShowtimeID(ma_lich_chieu);
    }
    @Override
     public List<Showtime> getAllShowtimes() {
        return showtimeDAO.getAllShowtimes();
    }
}
