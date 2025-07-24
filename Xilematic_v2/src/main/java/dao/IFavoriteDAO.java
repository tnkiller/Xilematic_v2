/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.Favorite;
import model.Movie;

/**
 *
 * @author ADMIN
 */
public interface IFavoriteDAO {

    public void insertFavorite(Favorite fav) throws SQLException;

    public Favorite selectFavorite(int id) throws SQLException;

    public List<Movie> selectFavoriteByUserId(int userId) throws SQLException;

    public List<Favorite> selectAllFavourites() throws SQLException;

    public boolean deleteFavorite(int id) throws SQLException;

    public boolean deleteFavoriteByCondition(int userId, int movieId) throws SQLException;

    public boolean updateFavorite(Favorite fav) throws SQLException;

    public List<Favorite> getFavouritesForPage(int currentPage, int pageSize) throws SQLException;

    public int getTotalFavouriteCount() throws SQLException;

}
