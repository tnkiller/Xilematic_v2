package dao;

import java.util.List;

import model.Showtime;

interface IShowtimeDAO {

    public String getNameCinemaGroupByShowTimeID(int ma_lich_chieu);

    public String getCinemaNameByShowtimeID(int ma_lich_chieu);

    public Showtime getShowtimeInformationByID(int ma_lich_chieu);
    
    public String getMoiveNameByShowtimeID(int ma_lich_chieu);
    public List<Showtime> getAllShowtimes();
}
