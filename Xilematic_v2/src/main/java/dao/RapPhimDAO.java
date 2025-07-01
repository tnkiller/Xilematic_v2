package dao;

import context.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.RapPhim;

public class RapPhimDAO implements IRapPhimDAO {
    private static final String SELECT_ALL_RAP_PHIM = "SELECT * FROM RapPhim";
    private static final String SELECT_RAP_PHIM_BY_ID = "SELECT * FROM RapPhim WHERE ma_rap = ?";
    private static final String SELECT_ALL_RAP_PHIM_BY_MA_CUM_RAP = "SELECT * FROM RapPhim WHERE ma_cum_rap = ?";
    private static final String INSERT_NEW_RAP_PHIM = "INSERT INTO RapPhim (ten_rap, ma_cum_rap) VALUES (?, ?)";
    private static final String DELETE_RAP_PHIM = "DELETE RapPhim WHERE ma_rap = ?";
    private static final String UPDATE_RAP_PHIM = "UPDATE RapPhim\n"
            + "SET ten_rap = ?\n"
            + "WHERE ma_rap = ?";
    private static final String SEARCH_RAP_PHIM = "SELECT * \n"
            + "FROM RapPhim \n"
            + "WHERE ten_rap LIKE '%' + ? + '%';";
    private static final String PAGING_RAP_PHIM = "SELECT * FROM RapPhim ORDER BY ma_rap OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SELECT_TOTAL_RAP_PHIM = "SELECT COUNT(*) FROM RapPhim";

    @Override
    public void addNewRapPhim(RapPhim r) {
        PreparedStatement ptm = null;
        try (Connection con = DBConnection.getConnection()) {
            String sql = INSERT_NEW_RAP_PHIM;
            ptm = con.prepareStatement(sql);
            ptm.setString(1, r.getTenRap());
            ptm.setInt(2, r.getMaCumRap());
            ptm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteRapPhim(int ma_rap) {
        boolean rowDeleted = false;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DELETE_RAP_PHIM);
            ps.setInt(1, ma_rap);
            rowDeleted = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    @Override
    public boolean updateRapPhim(RapPhim r) {
        boolean rowUpdated = false;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(UPDATE_RAP_PHIM);
            ps.setString(1, r.getTenRap());
            ps.setInt(2, r.getMaRap());
            rowUpdated = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public List<RapPhim> searchRapPhim(String userSearch) {
        List<RapPhim> result = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SEARCH_RAP_PHIM);
            ps.setString(1, userSearch);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new RapPhim(rs.getInt("ma_rap"), rs.getString("ten_rap"), rs.getInt("ma_cum_rap")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public RapPhim selectRapPhimByID(int ma_rap) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_RAP_PHIM_BY_ID);
            ps.setInt(1, ma_rap);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new RapPhim(rs.getInt("ma_rap"), rs.getString("ten_rap"), rs.getInt("ma_cum_rap"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RapPhim> selectAllRapPhimByMaCumRap(int ma_cum_rap) {
        List<RapPhim> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_ALL_RAP_PHIM_BY_MA_CUM_RAP);
            ps.setInt(1, ma_cum_rap);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new RapPhim(rs.getInt("ma_rap"), rs.getString("ten_rap"), rs.getInt("ma_cum_rap")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<RapPhim> getRapPhimForPage(int currentPage, int pageSize) {
        List<RapPhim> result = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(PAGING_RAP_PHIM);
            ps.setInt(1, (currentPage - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new RapPhim(rs.getInt("ma_rap"), rs.getString("ten_rap"), rs.getInt("ma_cum_rap")));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public int getTotalRapPhim() {
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            ptm = con.prepareStatement(SELECT_TOTAL_RAP_PHIM);
            rs = ptm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<RapPhim> getAllRapPhim() {
        List<RapPhim> list = new ArrayList<>();
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            ptm = con.prepareStatement(SELECT_ALL_RAP_PHIM);
            rs = ptm.executeQuery();
            while (rs.next()) {
                int maRap = rs.getInt("ma_rap");
                String tenRap = rs.getString("ten_rap");
                int maCumRap = rs.getInt("ma_cum_rap");
                list.add(new RapPhim(maRap, tenRap, maCumRap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static void main(String[] args) {
        RapPhimDAO r = new RapPhimDAO();
        for (RapPhim rapPhim : r.getAllRapPhim()) {
            System.out.println(rapPhim.toString());
        }
    }
}
