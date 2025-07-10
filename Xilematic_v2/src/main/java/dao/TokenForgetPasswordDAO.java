/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBConnection;
import entity.TokenForgetPassword;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class TokenForgetPasswordDAO {

    private final static String INSERT_TOKEN = "INSERT INTO [dbo].[TokenQuenMatKhau]\n"
            + "           ([token]\n"
            + "           ,thoi_gian_song\n"
            + "           ,[duoc_su_dung]\n"
            + "           ,[ma_nguoi_dung])\n"
            + "     VALUES\n"
            + "           (?,?,?,?)";

    private final static String GET_TOKEN = "SELECT * FROM TokenQuenMatKhau WHERE token = ?";

    private final static String UPDATE_STATUS = "UPDATE TokenQuenMatKhau SET duoc_su_dung = ? WHERE token = ?";

    public boolean insertTokenForget(TokenForgetPassword token) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(INSERT_TOKEN);
            ps.setString(1, token.getToken());
            ps.setTimestamp(2, Timestamp.valueOf(token.getExpiryTime()));
            ps.setBoolean(3, token.isUsed());
            ps.setInt(4, token.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public TokenForgetPassword getTokenForgetPassword(String token) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(GET_TOKEN);
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new TokenForgetPassword(rs.getInt("id"), rs.getString("token"),
                        rs.getTimestamp("thoi_gian_song").toLocalDateTime(), rs.getBoolean("duoc_su_dung"), rs.getInt("ma_nguoi_dung"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void updateStatus(TokenForgetPassword token) {
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement pt = conn.prepareStatement(UPDATE_STATUS);
            pt.setBoolean(1, token.isUsed());
            pt.setString(2, token.getToken());
            pt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TokenForgetPasswordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
