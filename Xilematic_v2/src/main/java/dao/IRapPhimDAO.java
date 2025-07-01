package dao;

import java.util.List;
import model.RapPhim;

public interface IRapPhimDAO {

    public List<RapPhim> getAllRapPhim();

    public void addNewRapPhim(RapPhim r);

    public boolean deleteRapPhim(int ma_rap);

    public boolean updateRapPhim(RapPhim r);

    public List<RapPhim> searchRapPhim(String userSearch);

    public RapPhim selectRapPhimByID(int ma_rap);

    public List<RapPhim> selectAllRapPhimByMaCumRap(int ma_cum_rap);

    public List<RapPhim> getRapPhimForPage(int currentPage, int pageSize);

    public int getTotalRapPhim();
}
