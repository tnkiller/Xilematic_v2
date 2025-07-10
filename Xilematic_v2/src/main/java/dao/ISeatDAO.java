
package dao;

import java.util.List;
import model.Seat;

public interface ISeatDAO {

    public List<Seat> selectSeatsByCinemaId(int cinemaId);
    
}
