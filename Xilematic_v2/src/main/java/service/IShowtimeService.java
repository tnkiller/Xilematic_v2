package service;

import model.Showtime;

public interface IShowtimeService {

    public String getNameCinemaGroupByShowTimeID(int ma_lich_chieu);

    public String getCinemaNameByShowtimeID(int ma_lich_chieu);

    public Showtime getShowtimeInformationByID(int ma_lich_chieu);
    
}
