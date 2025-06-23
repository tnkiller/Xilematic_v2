/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.Favorite;
import model.Movie;

/**
 *
 * @author ADMIN
 */
public interface IFavoriteService {

    public void insertFavorite(Favorite fav);

    public Favorite selectFavorite(int id);

    public List<Movie> selectFavoriteByUserId(int userId);

    public List<Favorite> selectAllFavourites();

    public boolean deleteFavorite(int id);

    public boolean updateFavorite(Favorite fav);

    public List<Favorite> getFavouritesForPage(int currentPage, int pageSize);

    public int getTotalFavouriteCount();

}
