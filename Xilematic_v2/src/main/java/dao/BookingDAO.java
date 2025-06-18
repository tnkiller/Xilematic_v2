
package dao;

import context.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Booking;


public class BookingDAO implements IBookingDAO{
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
    public static void main(String[] args) {
        Booking b = new Booking(1, 1, "A2,A3", 100000);
        BookingDAO da = new BookingDAO();
        da.addNewBooking(b);
    }
}
