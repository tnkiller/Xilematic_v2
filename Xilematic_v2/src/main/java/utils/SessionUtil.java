/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import model.Movie;
import model.User;
import service.FavoriteServiceImpl;
import service.IFavoriteService;

/**
 *
 * @author ADMIN
 */
public class SessionUtil {

    public static final String USER_INFOR = "userInfor";
    public static final String COLOR_CODE = "colorCode";
    public static final String FAV_LIST = "favList";
    private static IFavoriteService favService = new FavoriteServiceImpl();

    public static void initializeSession(HttpSession session, User userInfor)
            throws ServletException, IOException {
        session.setAttribute(USER_INFOR, userInfor);
        session.setAttribute(COLOR_CODE, Helper.generateColorCode());
        var favList = getFavListFromUserId(userInfor.getId());
        session.setAttribute(FAV_LIST, favList);
    }

    private static List<?> getFavListFromUserId(int userId) {
        List<Movie> favList = favService.selectFavoriteByUserId(userId);
        return favList;
    }

}
