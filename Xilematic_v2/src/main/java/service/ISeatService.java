package service;

import java.util.List;
import model.Seat;

public interface ISeatService {

    public List<Seat> getSeatsByCinemaId(int ma_phim);
}
