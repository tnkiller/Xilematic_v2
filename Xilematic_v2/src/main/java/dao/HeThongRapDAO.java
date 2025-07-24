package dao;

import context.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.CumRap;
import model.HeThongRap;
import model.LichChieu;
import model.RapPhim;

public class HeThongRapDAO implements IHeThongRapDAO {

    private static final String PAGING_HETHONGRAP = "SELECT * FROM HeThongRap ORDER BY ma_he_thong_rap OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String PAGING_CUMRAP_BY_MaHETHONGRAP = "SELECT * FROM CumRap where ma_he_thong_rap = ? ORDER BY ma_cum_rap OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String PAGING_CUMRAP = "SELECT * FROM CumRap ORDER BY ma_cum_rap OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String PAGING_RAP_BY_MACUMRAP = "SELECT * FROM RapPhim where ma_cum_rap = ? ORDER BY ma_rap OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String PAGING_RAP = "SELECT * FROM RapPhim  ORDER BY ma_rap OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String PAGING_LICHCHIEU_BY_MARAP = "SELECT * FROM LichChieu lc join RapPhim r on lc.ma_rap=r.ma_rap  where lc.ma_rap = ? ORDER BY ma_lich_chieu OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String PAGING_LICHCHIEU = "SELECT * FROM LichChieu lc join RapPhim r on lc.ma_rap=r.ma_rap  ORDER BY ma_lich_chieu OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String GET_TOTAL_OF_HETHONGRAP = "SELECT COUNT(*) FROM HeThongRap ";
    private static final String GET_TOTAL_OF_CUMRAP_BYIDHTR = "SELECT COUNT(*) FROM CumRap where ma_he_thong_rap = ? ";
    private static final String GET_TOTAL_OF_CUMRAP = "SELECT COUNT(*) FROM CumRap ";
    private static final String GET_TOTAL_OF_RAP_BYIDCR = "SELECT COUNT(*) FROM RapPhim Where ma_cum_rap=?";
    private static final String GET_TOTAL_OF_RAP = "SELECT COUNT(*) FROM RapPhim";
    private static final String GET_TOTAL_OF_LICHCHIEU_BYIDR = "SELECT COUNT(*) FROM LichChieu Where ma_rap=? ";
    private static final String GET_TOTAL_OF_LICHCHIEU = "SELECT COUNT(*) FROM LichChieu ";
    private static final String GET_ALL_TEN_HE_THONG_RAP = "SELECT ten_he_thong_rap FROM HeThongRap";
    private static final String SQL_MONTH_REVENUE = """
    SELECT
        h.ma_he_thong_rap,
        h.ten_he_thong_rap,
        ISNULL(SUM(dv.gia_ve), 0) AS tong_doanh_thu_thang
    FROM HeThongRap AS h
    LEFT JOIN CumRap    AS cr ON cr.ma_he_thong_rap = h.ma_he_thong_rap
    LEFT JOIN RapPhim   AS rp ON rp.ma_cum_rap      = cr.ma_cum_rap
    LEFT JOIN LichChieu AS lc ON lc.ma_rap          = rp.ma_rap
    LEFT JOIN DatVe     AS dv
           ON dv.ma_lich_chieu = lc.ma_lich_chieu
          AND dv.create_at >= ?      -- ① @firstDay
          AND dv.create_at <  ?      -- ② @nextMonth
    GROUP BY h.ma_he_thong_rap, h.ten_he_thong_rap
    ORDER BY tong_doanh_thu_thang DESC
    """;

    @Override
    public List<HeThongRap> getHeThongRapsForPage(int currentPage, int pageSize) {
        List<HeThongRap> heThongRaps = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(PAGING_HETHONGRAP)) {
            ps.setInt(1, (currentPage - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                heThongRaps.add(new HeThongRap(
                        rs.getInt("ma_he_thong_rap"),
                        rs.getString("ten_he_thong_rap"),
                        rs.getString("logo")
                ));
            }
            return heThongRaps;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    @Override
    public List<CumRap> getCumRapByIDForPage(int currentPage, int pageSize, int heThongRapID) throws SQLException {
        List<CumRap> result = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(PAGING_CUMRAP_BY_MaHETHONGRAP);
            ps.setInt(1, heThongRapID);
            ps.setInt(2, (currentPage - 1) * pageSize);
            ps.setInt(3, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ma_cum_rap = rs.getInt("ma_cum_rap");
                String ten_cum_rap = rs.getString("ten_cum_rap");
                String dia_chi = rs.getString("dia_chi");
                int ma_he_thong_rap = rs.getInt("ma_he_thong_rap");
                result.add(new CumRap(ma_cum_rap, ten_cum_rap, dia_chi, ma_he_thong_rap));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CumRap> getCumRapForPage(int currentPage, int pageSize) throws SQLException {
        List<CumRap> result = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(PAGING_CUMRAP);
            ps.setInt(1, (currentPage - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ma_cum_rap = rs.getInt("ma_cum_rap");
                String ten_cum_rap = rs.getString("ten_cum_rap");
                String dia_chi = rs.getString("dia_chi");
                int ma_he_thong_rap = rs.getInt("ma_he_thong_rap");
                result.add(new CumRap(ma_cum_rap, ten_cum_rap, dia_chi, ma_he_thong_rap));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RapPhim> getRapPhimsByCumRapIDForPage(int currentPage, int pageSize, int cumRapID) throws SQLException {
        List<RapPhim> result = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(PAGING_RAP_BY_MACUMRAP);
            ps.setInt(1, cumRapID);
            ps.setInt(2, (currentPage - 1) * pageSize);
            ps.setInt(3, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ma_rap = rs.getInt("ma_rap");
                String ten_rap = rs.getString("ten_rap");
                int ma_cum_rap = rs.getInt("ma_cum_rap");
                result.add(new RapPhim(ma_rap, ten_rap, ma_cum_rap));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RapPhim> getRapPhimsForPage(int currentPage, int pageSize) throws SQLException {
        List<RapPhim> result = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(PAGING_RAP);
            ps.setInt(1, (currentPage - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ma_rap = rs.getInt("ma_rap");
                String ten_rap = rs.getString("ten_rap");
                int ma_cum_rap = rs.getInt("ma_cum_rap");
                result.add(new RapPhim(ma_rap, ten_rap, ma_cum_rap));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LichChieu> getLichChieusByMaRapForPage(int currentPage, int pageSize, int RapID) throws SQLException {
        List<LichChieu> result = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(PAGING_LICHCHIEU_BY_MARAP);
            ps.setInt(1, RapID);
            ps.setInt(2, (currentPage - 1) * pageSize);
            ps.setInt(3, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ma_lich_chieu = rs.getInt("ma_lich_chieu");
                int ma_rap = rs.getInt("ma_rap");
                int ma_phim = rs.getInt("ma_phim");
                LocalDateTime ngay_gio_chieu = rs.getTimestamp("ngay_gio_chieu").toLocalDateTime();
                result.add(new LichChieu(ma_lich_chieu, ma_rap, ma_phim, ngay_gio_chieu));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LichChieu> getLichChieusForPage(int currentPage, int pageSize) throws SQLException {
        List<LichChieu> result = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(PAGING_LICHCHIEU);
            ps.setInt(1, (currentPage - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ma_lich_chieu = rs.getInt("ma_lich_chieu");
                int ma_rap = rs.getInt("ma_rap");
                int ma_phim = rs.getInt("ma_phim");
                LocalDateTime ngay_gio_chieu = rs.getTimestamp("ngay_gio_chieu").toLocalDateTime();
                result.add(new LichChieu(ma_lich_chieu, ma_rap, ma_phim, ngay_gio_chieu));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void addHeThongRap(HeThongRap heThongRap) {
        String query = "INSERT INTO HeThongRap (ma_he_thong_rap, ten_he_thong_rap, logo) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, heThongRap.getMaHeThongRap());
            ps.setString(2, heThongRap.getTenHeThongRap());
            ps.setString(3, heThongRap.getLogo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateHeThongRap(HeThongRap heThongRap) {
        String query = "UPDATE HeThongRap SET ten_he_thong_rap = ?, logo = ? WHERE ma_he_thong_rap = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, heThongRap.getTenHeThongRap());
            ps.setString(2, heThongRap.getLogo());
            ps.setInt(3, heThongRap.getMaHeThongRap());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteHeThongRap(int maHeThongRap) {
        String query = "DELETE FROM HeThongRap WHERE ma_he_thong_rap = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, maHeThongRap);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CumRap CRUD Operations
    @Override
    public void addCumRap(CumRap cumRap) {
        String query = "INSERT INTO CumRap (ma_cum_rap, ten_cum_rap, dia_chi, ma_he_thong_rap) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, cumRap.getMaCumRap());
            ps.setString(2, cumRap.getTenCumRap());
            ps.setString(3, cumRap.getDiaChi());
            ps.setInt(4, cumRap.getMaHeThongRap());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCumRap(CumRap cumRap) {
        String query = "UPDATE CumRap SET ten_cum_rap = ?, dia_chi = ?, ma_he_thong_rap = ? WHERE ma_cum_rap = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, cumRap.getTenCumRap());
            ps.setString(2, cumRap.getDiaChi());
            ps.setInt(3, cumRap.getMaHeThongRap());
            ps.setInt(4, cumRap.getMaCumRap());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCumRap(int maCumRap) {
        String query = "DELETE FROM CumRap WHERE ma_cum_rap = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, maCumRap);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // RapPhim CRUD Operations
    @Override
    public void addRapPhim(RapPhim rapPhim) {
        String query = "INSERT INTO RapPhim (ma_rap, ten_rap, ma_cum_rap) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, rapPhim.getMaRap());
            ps.setString(2, rapPhim.getTenRap());
            ps.setInt(3, rapPhim.getMaCumRap());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRapPhim(RapPhim rapPhim) {
        String query = "UPDATE RapPhim SET ten_rap = ?, ma_cum_rap = ? WHERE ma_rap = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, rapPhim.getTenRap());
            ps.setInt(2, rapPhim.getMaCumRap());
            ps.setInt(3, rapPhim.getMaRap());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRapPhim(int maRap) {
        String query = "DELETE FROM RapPhim WHERE ma_rap = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, maRap);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // LichChieu CRUD Operations
    @Override
    public void addLichChieu(LichChieu lichChieu) {
        String query = "INSERT INTO LichChieu (ma_rap, ma_phim, ngay_gio_chieu) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, lichChieu.getMaRap());
            ps.setInt(2, lichChieu.getMaPhim());
            ps.setTimestamp(3, Timestamp.valueOf(lichChieu.getNgayGioChieu()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLichChieu(LichChieu lichChieu) {
        String query = "UPDATE LichChieu SET ma_rap = ?, ma_phim = ?, ngay_gio_chieu = ? WHERE ma_lich_chieu = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, lichChieu.getMaRap());
            ps.setInt(2, lichChieu.getMaPhim());
            ps.setTimestamp(3, Timestamp.valueOf(lichChieu.getNgayGioChieu()));
            ps.setInt(4, lichChieu.getMaLichChieu());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLichChieu(int maLichChieu) {
        String query = "DELETE FROM LichChieu WHERE ma_lich_chieu = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, maLichChieu);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getTotalHeThongRap() {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement pt = c.prepareStatement(GET_TOTAL_OF_HETHONGRAP);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getTotalCumRap(int heThongRapID) {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement pt = c.prepareStatement(GET_TOTAL_OF_CUMRAP_BYIDHTR);
            pt.setInt(1, heThongRapID);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getTotalCumRap() {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement pt = c.prepareStatement(GET_TOTAL_OF_CUMRAP);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getTotalRap(int cumRapID) {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement pt = c.prepareStatement(GET_TOTAL_OF_RAP_BYIDCR);
            pt.setInt(1, cumRapID);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getTotalRap() {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement pt = c.prepareStatement(GET_TOTAL_OF_RAP);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getTotalLichChieu(int RapID) {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement pt = c.prepareStatement(GET_TOTAL_OF_LICHCHIEU_BYIDR);
            pt.setInt(1, RapID);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getTotalLichChieu() {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement pt = c.prepareStatement(GET_TOTAL_OF_LICHCHIEU);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public HeThongRap getHeThongRapById(int maHeThongRap) {
        String query = "SELECT * FROM HeThongRap WHERE ma_he_thong_rap = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, maHeThongRap);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new HeThongRap(
                        rs.getInt("ma_he_thong_rap"),
                        rs.getString("ten_he_thong_rap"),
                        rs.getString("logo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

// Method to get CumRap by ID
    @Override
    public CumRap getCumRapById(int maCumRap) {
        String query = "SELECT * FROM CumRap WHERE ma_cum_rap = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, maCumRap);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CumRap(
                        rs.getInt("ma_cum_rap"),
                        rs.getString("ten_cum_rap"),
                        rs.getString("dia_chi"),
                        rs.getInt("ma_he_thong_rap")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

// Method to get RapPhim by ID
    @Override
    public RapPhim getRapPhimById(int maRap) {
        String query = "SELECT * FROM RapPhim WHERE ma_rap = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, maRap);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new RapPhim(
                        rs.getInt("ma_rap"),
                        rs.getString("ten_rap"),
                        rs.getInt("ma_cum_rap")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
// Method to get LichChieu by ID

    @Override
    public LichChieu getLichChieuById(int maLichChieu) {
        String query = "SELECT * FROM LichChieu WHERE ma_lich_chieu = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, maLichChieu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new LichChieu(
                        rs.getInt("ma_lich_chieu"),
                        rs.getInt("ma_rap"),
                        rs.getInt("ma_phim"),
                        rs.getTimestamp("ngay_gio_chieu").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LichChieu> getLichChieuByRapPhimAndPhim(int maRap, int maPhim) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM LichChieu WHERE MaRap = ? AND MaPhim = ? AND NgayGioChieu > CURRENT_TIMESTAMP";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, maRap);
            pstmt.setInt(2, maPhim);

            ResultSet rs = pstmt.executeQuery();
            List<LichChieu> listLichChieu = new ArrayList<>();

            while (rs.next()) {
                LichChieu lichChieu = new LichChieu();
                lichChieu.setMaLichChieu(rs.getInt("MaLichChieu"));
                lichChieu.setNgayGioChieu(rs.getTimestamp("NgayGioChieu").toLocalDateTime());
                // Các trường khác nếu cần
                listLichChieu.add(lichChieu);
            }

            return listLichChieu;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) throws SQLException {
//     List<LichChieu> list =   new HeThongRapDAO().getLichChieusByMaRapForPage(1, 10, 1);
//          System.out.println("kich thuoc :"+list.size());
//          for (LichChieu heThongRap : list) {
//              System.out.println(heThongRap);
//             
//         }
        HeThongRapDAO heThongRapDAO = new HeThongRapDAO();
//        System.out.println("hethongrap : " + heThongRapDAO.getTotalHeThongRap());
//        System.out.println("cum rap : " + heThongRapDAO.getTotalCumRap(1));
//        System.out.println("rap : " + heThongRapDAO.getTotalRap(1));
//        System.out.println("Lich chieu : " + heThongRapDAO.getTotalLichChieu(1));
         Map<String, Long> revenueMap = heThongRapDAO.getMonthlyRevenue();   // LinkedHashMap<tenHeThongRap, doanhThu>

        // In tiêu đề bảng
        System.out.printf("%-25s | %15s%n", "Hệ thống rạp", "Doanh thu tháng");
        System.out.println("-----------------------------------------------");

        // Lặp & in từng hệ thống rạp
        revenueMap.forEach((cinemaName, total) ->
            System.out.printf("%-25s | %,15d%n", cinemaName, total)
        );
    }

    @Override
    public List<String> getAllTenHeThongRap() {
        List<String> result = new ArrayList<>();
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            ptm = con.prepareStatement(GET_ALL_TEN_HE_THONG_RAP);
            rs = ptm.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("ten_he_thong_rap"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Long> getMonthlyRevenue() {
        Map<String, Long> result = new LinkedHashMap<>();  
        PreparedStatement ptm = null;
        ResultSet rs = null;

        LocalDate firstDay = LocalDate.now().withDayOfMonth(1);
        LocalDate nextMonth = firstDay.plusMonths(1);

        try (Connection con = DBConnection.getConnection()) {
            ptm = con.prepareStatement(SQL_MONTH_REVENUE);   
            ptm.setDate(1, Date.valueOf(firstDay));
            ptm.setDate(2, Date.valueOf(nextMonth));

            rs = ptm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("ten_he_thong_rap");
                long revenue = rs.getLong("tong_doanh_thu_thang");
                result.put(name, revenue);          
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}


