/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.FavoriteDAO;
import dao.IFavoriteDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Favorite;
import model.Movie;

/**
 *
 * @author ADMIN
 */
public class FavoriteServiceImpl implements IFavoriteService {

    private IFavoriteDAO favoriteDao = new FavoriteDAO();

    @Override
    public void insertFavorite(Favorite fav) {
        try {
            favoriteDao.insertFavorite(fav);
        } catch (SQLException ex) {
            Logger.getLogger(FavoriteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Favorite selectFavorite(int id) {
        try {
            favoriteDao.selectFavorite(id);
        } catch (SQLException ex) {
            Logger.getLogger(FavoriteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Favorite> selectAllFavourites() {
        try {
            return favoriteDao.selectAllFavourites();
        } catch (SQLException ex) {
            Logger.getLogger(FavoriteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean deleteFavorite(int id) {
        try {
            return favoriteDao.deleteFavorite(id);
        } catch (SQLException ex) {
            Logger.getLogger(FavoriteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean updateFavorite(Favorite fav) {
        try {
            return favoriteDao.updateFavorite(fav);
        } catch (SQLException ex) {
            Logger.getLogger(FavoriteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Favorite> getFavouritesForPage(int currentPage, int pageSize) {
        try {
            return favoriteDao.getFavouritesForPage(currentPage, pageSize);
        } catch (SQLException ex) {
            Logger.getLogger(FavoriteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getTotalFavouriteCount() {
        try {
            favoriteDao.getTotalFavouriteCount();
        } catch (SQLException ex) {
            Logger.getLogger(FavoriteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public List<Movie> selectFavoriteByUserId(int userId) {
        try {
            return favoriteDao.selectFavoriteByUserId(userId);
        } catch (SQLException ex) {
            Logger.getLogger(FavoriteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
