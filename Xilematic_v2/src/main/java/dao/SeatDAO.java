package dao;

import context.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Seat;

public class SeatDAO implements ISeatDAO {

    private final static String SELECT_SEATS_BY_CINEMA = "SELECT * FROM Ghe WHERE ma_rap = ?";
    private final static String DELETE_SEAT = "DELETE Ghe WHERE ma_ghe = ?";
    private final static String UPDATE_SEAT = "UPDATE Ghe\n"
            + "SET ten_ghe = ?, loai_ghe = ?, da_dat = ?, trang_thai = ?\n"
            + "WHERE ma_ghe = ?";
    private final static String INSERT_SEAT = "INSERT INTO Ghe (ten_ghe, loai_ghe, ma_rap, da_dat, trang_thai) VALUES (?, ?, ?, ?, ?)";
    private final static String SELECT_SEAT_NAME = "SELECT ten_ghe FROM Ghe WHERE ma_rap = ?";
    private final static String SELECT_TOTAL_SEAT_IN_CINEMA = "SELECT COUNT(*) FROM Ghe Where ma_rap = ?";
    private static final String PAGING_GHE = "SELECT * FROM Ghe WHERE ma_rap = ? ORDER BY ma_rap OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SELECT_GHE_BY_ID = "SELECT * FROM Ghe WHERE ma_ghe = ?";

    @Override
    public List<Seat> selectSeatsByCinemaId(int ma_rap) {
        List<Seat> result = new ArrayList<>();
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            ptm = con.prepareStatement(SELECT_SEATS_BY_CINEMA);
            ptm.setInt(1, ma_rap);
            rs = ptm.executeQuery();
            while (rs.next()) {
                int ma_ghe = rs.getInt("ma_ghe");
                String ten_ghe = rs.getString("ten_ghe");
                String loai_ghe = rs.getString("loai_ghe");
                boolean da_dat = rs.getBoolean("da_dat");
                String trang_thai = rs.getString("trang_thai");
                result.add(new Seat(ma_ghe, ten_ghe, loai_ghe, da_dat, trang_thai));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> selectSeatnameByCinemaId(int ma_rap) {
        List<String> result = new ArrayList<>();
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            ptm = con.prepareStatement(SELECT_SEAT_NAME);
            ptm.setInt(1, ma_rap);
            rs = ptm.executeQuery();
            while (rs.next()) {
                String ten_ghe = rs.getString("ten_ghe");
                result.add(ten_ghe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void addSeatIntoCinema(int ma_rap, int so_luong_ghe, String ten_ghe_vip) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(INSERT_SEAT);
            char row = 'A';
            int col = 1;
            int seatCount = 0;
            String lastSeat = "";
            if (!selectSeatnameByCinemaId(ma_rap).isEmpty()) {
                lastSeat = selectSeatnameByCinemaId(ma_rap).get(selectSeatnameByCinemaId(ma_rap).size() - 1);
                row = lastSeat.charAt(0);
                col = Integer.parseInt(lastSeat.substring(1));
                if (col == 10) {
                    row++;
                    col = 1;
                } else {
                    col++;
                }
                if (row > 'Z') {
                    return;
                }
            }

            Set<String> vipSeats = new HashSet<>();
            if (!ten_ghe_vip.equals("")) {
                vipSeats = new HashSet<>(Arrays.asList(ten_ghe_vip.split(",")));
            }

            while (seatCount < so_luong_ghe) {
                if (col > 10) {
                    row++;
                    col = 1;
                    continue;
                }
                String tenGhe = row + String.format("%d", col);
                boolean isVip = vipSeats.contains(tenGhe);
                ps.setString(1, tenGhe);
                ps.setString(2, isVip ? "VIP" : "Thường");
                ps.setInt(3, ma_rap);
                ps.setBoolean(4, false);
                ps.setString(5, "active");
                ps.addBatch();

                col++;

                seatCount++;
            }
            // Thực thi tất cả các câu insert
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteSeat(int ma_ghe) {
        boolean rowDeleted = false;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DELETE_SEAT);
            ps.setInt(1, ma_ghe);
            rowDeleted = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    @Override
    public boolean updateSeat(Seat ghe) {
        boolean rowUpdated = false;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(UPDATE_SEAT);
            ps.setString(1, ghe.getTen_ghe());
            ps.setString(2, ghe.getLoai_ghe());
            ps.setBoolean(3, ghe.isDa_dat());
            ps.setString(4, ghe.getTrang_thai());
            ps.setInt(5, ghe.getMa_ghe());
            rowUpdated = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public int getTotalSeatInCinema(int ma_rap) {
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            ptm = con.prepareStatement(SELECT_TOTAL_SEAT_IN_CINEMA);
            ptm.setInt(1, ma_rap);
            rs = ptm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        SeatDAO s = new SeatDAO();
        s.addSeatIntoCinema(34, 40, "");
    }

    @Override
    public List<Seat> getSeatForPage(int ma_rap, int currentPage, int pageSize) {
        List<Seat> result = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(PAGING_GHE);
            ps.setInt(1, ma_rap);
            ps.setInt(2, (currentPage - 1) * pageSize);
            ps.setInt(3, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ma_ghe = rs.getInt("ma_ghe");
                String ten_ghe = rs.getString("ten_ghe");
                String loai_ghe = rs.getString("loai_ghe");
                boolean da_dat = rs.getBoolean("da_dat");
                String trang_thai = rs.getString("trang_thai");
                result.add(new Seat(ma_ghe, ten_ghe, loai_ghe, da_dat, trang_thai));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Seat selectSeatByID(int ma_ghe) {
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            ptm = con.prepareStatement(SELECT_GHE_BY_ID);
            ptm.setInt(1, ma_ghe);
            rs = ptm.executeQuery();
            if (rs.next()) {
                String ten_ghe = rs.getString("ten_ghe");
                int ma_rap = rs.getInt("ma_rap");
                boolean da_dat = rs.getBoolean("da_dat");
                String loai_ghe = rs.getString("loai_ghe");
                String trang_thai = rs.getString("trang_thai");
                return new Seat(ma_ghe, ten_ghe, loai_ghe, da_dat, trang_thai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}
