package dao;

import context.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Booking;
import model.CumRap;
import model.HeThongRap;
import model.LichChieu;
import model.RapPhim;

public class BookingDAO implements IBookingDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private static final String INSERT_NEW_BOOKING = "INSERT INTO DatVe(tai_khoan, ma_lich_chieu, ghe_da_dat, gia_ve) VALUES (?, ?, ?, ?)";

    @Override
    public void addNewBooking(Booking b) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(INSERT_NEW_BOOKING);
            ps.setInt(1, b.getTai_khoan());
            ps.setInt(2, b.getMa_lich_chieu());
            ps.setString(3, b.getGhe_da_dat());
            ps.setLong(4, b.getGia_ve());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy tất cả hệ thống rạp (CGV, Lotte, ...)
    @Override
    public List<HeThongRap> getAllHeThongRap() {
        List<HeThongRap> list = new ArrayList<>();
        String query = "SELECT * FROM HeThongRap";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new HeThongRap(
                        rs.getInt("ma_he_thong_rap"),
                        rs.getString("ten_he_thong_rap"),
                        rs.getString("logo")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Đóng kết nối...
        return list;
    }

    // Lấy các cụm rạp theo mã hệ thống rạp
    @Override
    public List<CumRap> getCumRapByHeThongRapId(int heThongRapId) {
        List<CumRap> list = new ArrayList<>();
        String query = "SELECT * FROM CumRap WHERE ma_he_thong_rap = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, heThongRapId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ma_cum_rap = rs.getInt("ma_cum_rap");
                String ten_cum_rap = rs.getString("ten_cum_rap");
                String dia_chi = rs.getString("dia_chi");
                list.add(new CumRap(ma_cum_rap, ten_cum_rap, dia_chi, heThongRapId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<RapPhim> getRapByCumRapId(int cumRapId) {
        List<RapPhim> list = new ArrayList<>();
        String query = "SELECT * FROM RapPhim WHERE ma_cum_rap = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, cumRapId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ma_rap = rs.getInt("ma_rap");
                String ten_rap = rs.getString("ten_rap");
                int ma_cum_rap = rs.getInt("ma_cum_rap");
                list.add(new RapPhim(ma_rap, ten_rap, ma_cum_rap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy lịch chiếu theo Cụm Rạp, Phim và Ngày
    // Đây là phương thức phức tạp nhất
    @Override
    public List<LichChieu> getLichChieu(int maPhim, int maRap, String ngayChieu) {
        List<LichChieu> list = new ArrayList<>();
        // Câu query sẽ JOIN các bảng: LichChieu, RapPhim
        // và lọc theo ma_cum_rap, ma_phim, và ngày
        String query = "SELECT lc.*, r.ten_rap "
                + "FROM LichChieu lc "
                + "JOIN RapPhim r ON lc.ma_rap = r.ma_rap "
                + "WHERE  lc.ma_phim = ? AND CONVERT(date, lc.ngay_gio_chieu) = ? AND r.ma_rap=?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, maPhim);
            ps.setString(2, ngayChieu);// Định dạng 'YYYY-MM-DD'
            ps.setInt(3, maRap);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ma_lich_chieu = rs.getInt("ma_lich_chieu");
                int ma_rap = rs.getInt("ma_rap");
                int ma_phim = rs.getInt("ma_phim");
                LocalDateTime ngay_gio_chieu = rs.getTimestamp("ngay_gio_chieu").toLocalDateTime();
                list.add(new LichChieu(ma_lich_chieu, ma_rap, ma_phim, ngay_gio_chieu));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
        List<LichChieu> list = new ArrayList<>();
        new HeThongRapDAO().getLichChieuByRapPhimAndPhim(1, 1);
        System.out.println("kich thuoc :" + list.size());
        for (LichChieu heThongRap : list) {
            System.out.println(heThongRap);

        }
    }

}
