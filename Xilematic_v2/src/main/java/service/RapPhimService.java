package service;

import dao.RapPhimDAO;
import java.util.List;
import model.RapPhim;

public class RapPhimService implements IRapPhimService {

    private RapPhimDAO rapPhimDAO = new RapPhimDAO();

    @Override
    public void addNewRapPhim(RapPhim r) {
        rapPhimDAO.addNewRapPhim(r);
    }

    @Override
    public boolean deleteRapPhim(int ma_rap) {
        return rapPhimDAO.deleteRapPhim(ma_rap);
    }

    @Override
    public boolean updateRapPhim(RapPhim r) {
        return rapPhimDAO.updateRapPhim(r);
    }

    @Override
    public List<RapPhim> searchRapPhim(String userSearch) {
        return rapPhimDAO.searchRapPhim(userSearch);
    }

    @Override
    public RapPhim selectRapPhimByID(int ma_rap) {
        return rapPhimDAO.selectRapPhimByID(ma_rap);
    }

    @Override
    public List<RapPhim> selectAllRapPhimByMaCumRap(int ma_cum_rap) {
        return rapPhimDAO.selectAllRapPhimByMaCumRap(ma_cum_rap);
    }

    @Override
    public List<RapPhim> getRapPhimForPage(int currentPage, int pageSize) {
        return rapPhimDAO.getRapPhimForPage(currentPage, pageSize);
    }

    @Override
    public int getTotalRapPhim() {
        return rapPhimDAO.getTotalRapPhim();
    }

    @Override
    public List<RapPhim> getAllRapPhim() {
        return rapPhimDAO.getAllRapPhim();
    }

}
