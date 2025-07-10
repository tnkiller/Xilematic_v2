package dao;

import context.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Seat;

public class SeatDAO implements ISeatDAO {

    private final static String SELECT_SEATS_BY_CINEMA = "SELECT * FROM Ghe WHERE ma_rap = ?";

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
                String ten_ghe = rs.getString("ten_ghe");
                String loai_ghe = rs.getString("loai_ghe");
                boolean da_dat = rs.getBoolean("da_dat");
                result.add(new Seat(ten_ghe, loai_ghe, da_dat));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        SeatDAO a = new SeatDAO();
        List<Seat> l = a.selectSeatsByCinemaId(1);
        for (Seat seat : l) {
            System.out.println(seat.toString());
        }
    }
}
