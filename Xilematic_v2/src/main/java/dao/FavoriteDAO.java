/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Favorite;
import model.Movie;

/**
 *
 * @author ADMIN
 */
public class FavoriteDAO implements IFavoriteDAO {

    private static final String INSERT_FAV = "INSERT INTO YeuThich(ma_nguoi_dung,ma_phim) VALUES (?,?)";
    private static final String SELECT_FAV_BY_ID = "SELECT * FROM YeuThich WHERE ma_yeu_thich = ?";
    private static final String SELECT_FAV_BY_USERID = "SELECT * FROM YeuThich WHERE ma_nguoi_dung = ?";
    private static final String SELECT_ALL_FAV = "SELECT * FROM YeuThich ";
    private static final String UPDATE_FAV = "UPDATE YeuThich SET ma_nguoi_dung = ?, ma_phim = ?  WHERE ma_yeu_thich = ?";
    private static final String DELETE_FAV = "DELETE YeuThich WHERE ma_yeu_thich = ?";

    @Override
    public void insertFavorite(Favorite fav) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(INSERT_FAV);
            ps.setInt(1, fav.getUser().getId());
            ps.setInt(2, fav.getMovie().getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Favorite selectFavorite(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_FAV_BY_ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Favorite(
                        rs.getInt("ma_yeu_thich"),
                        new UserDAO().selectUser(rs.getInt("ma_nguoi_dung")),
                        new MovieDAO().selectMovie(rs.getInt("ma_phim")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Favorite> selectAllFavourites() throws SQLException {
        List<Favorite> result = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_ALL_FAV);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Favorite(
                        rs.getInt("ma_yeu_thich"),
                        new UserDAO().selectUser(rs.getInt("ma_nguoi_dung")),
                        new MovieDAO().selectMovie(rs.getInt("ma_phim")))
                );
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteFavorite(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(DELETE_FAV);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateFavorite(Favorite fav) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(UPDATE_FAV);
            ps.setInt(1, fav.getId());
            ps.setInt(2, fav.getUser().getId());
            ps.setInt(3, fav.getMovie().getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Favorite> getFavouritesForPage(int currentPage, int pageSize) throws SQLException {
        return null;
    }

    @Override
    public int getTotalFavouriteCount() throws SQLException {
        return 0;
    }

    @Override
    public List<Movie> selectFavoriteByUserId(int userId) throws SQLException {
        List<Movie> result = new ArrayList<>();
        MovieDAO movieDao = null;
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(SELECT_FAV_BY_USERID);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                movieDao = new MovieDAO();
                result.add(movieDao.selectMovie(rs.getInt("ma_phim")));
            }
            while (rs.next()) {
                result.add(movieDao.selectMovie(rs.getInt("ma_phim")));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //MAIN TEST
    public static void main(String[] args) throws SQLException {
        new FavoriteDAO().selectFavoriteByUserId(16).forEach(System.out::println);
        System.out.println(new FavoriteDAO().selectFavoriteByUserId(16).size());
    }

}
