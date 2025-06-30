package service;

import dao.CumRapDAO;
import dao.ICumRapDAO;
import java.util.List;
import model.CumRap;

public class CumRapService implements ICumRapService {

    private ICumRapDAO cumRapDAO = new CumRapDAO();

    @Override
    public List<CumRap> getAllCumRap() {
        return cumRapDAO.getAllCumRap();
    }

}
