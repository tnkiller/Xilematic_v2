/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import context.DBConnection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ADMIN
 */
public class ExportData {

    private static final String sql = "SELECT p.*, t.ten_the_loai\n"
            + "FROM Phim p inner join Phim_TheLoai tl ON p.ma_phim = tl.ma_phim\n"
            + "	inner join TheLoai t ON tl.ma_the_loai = t.ma_the_loai\n"
            + "WHERE p.is_active IS NULL";
    private static final String path = "C:\\Users\\ADMIN\\Downloads\\data.txt";

    public static void main(String[] args) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
                while (rs.next()) {
                    String sentence = rs.getString(2) + "|" + rs.getString(14);
                    bw.write(sentence);
                    bw.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
