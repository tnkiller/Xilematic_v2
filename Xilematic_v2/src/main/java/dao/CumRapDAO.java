package dao;

import context.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CumRap;

public class CumRapDAO implements ICumRapDAO {

    private final static String SELECT_ALL_CUM_RAP = "SELECT * FROM CumRap";

    @Override
    public List<CumRap> getAllCumRap() {
        List<CumRap> list = new ArrayList<>();
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            String sql = SELECT_ALL_CUM_RAP;
            ptm = con.prepareStatement(sql);
            rs = ptm.executeQuery();
            while (rs.next()) {
                int maCumRap = rs.getInt("ma_cum_rap");
                String tenCumRap = rs.getString("ten_cum_rap");
                String diaChi = rs.getString("dia_chi");
                int maHeThongRap = rs.getInt("ma_he_thong_rap");
                list.add(new CumRap(maCumRap, tenCumRap, diaChi, maHeThongRap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
