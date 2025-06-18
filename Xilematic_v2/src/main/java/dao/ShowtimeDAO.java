package dao;

import context.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Showtime;
import utils.Helper;

public class ShowtimeDAO implements IShowtimeDAO {

    private Helper helper = new Helper();
    private static final String SELECT_SHOWTIME_BY_ID = "SELECT * FROM LichChieu l\n"
            + "JOIN RapPhim r ON r.ma_rap = l.ma_rap\n"
            + "JOIN Phim p ON p.ma_phim = l.ma_phim\n"
            + "WHERE ma_lich_chieu = ?";
    private static final String SELECT_CINEMAGROUP_NAME = "SELECT ten_cum_rap FROM CumRap c\n"
            + "JOIN RapPhim r ON r.ma_cum_rap = c.ma_cum_rap\n"
            + "JOIN LichChieu l ON l.ma_rap = r.ma_rap\n"
            + "WHERE ma_lich_chieu = ?";
    private static final String SELECT_CINEMA_NAME = "SELECT ten_rap FROM RapPhim r\n"
            + "JOIN LichChieu l ON l.ma_rap = r.ma_rap\n"
            + "WHERE ma_lich_chieu = ?";

    @Override
    public Showtime getShowtimeInformationByID(int ma_lich_chieu) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_SHOWTIME_BY_ID);
            ps.setInt(1, ma_lich_chieu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ma_phim = rs.getInt("ma_phim");
                int ma_rap = rs.getInt("ma_rap");
                String ngay_gio_chieu = helper.formatLocalDateTime(rs.getTimestamp("ngay_gio_chieu").toLocalDateTime());
                return new Showtime(ma_rap, ma_phim, ngay_gio_chieu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getNameCinemaGroupByShowTimeID(int ma_lich_chieu) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_CINEMAGROUP_NAME);
            ps.setInt(1, ma_lich_chieu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ten_cum_rap");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getCinemaNameByShowtimeID(int ma_lich_chieu) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_CINEMA_NAME);
            ps.setInt(1, ma_lich_chieu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ten_rap");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        ShowtimeDAO s = new ShowtimeDAO();
        System.out.println(s.getCinemaNameByShowtimeID(1));
    }
}
