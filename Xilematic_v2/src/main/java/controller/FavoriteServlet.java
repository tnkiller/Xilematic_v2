/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import constant.PageLink;
import constant.SessionAttribute;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Movie;
import model.User;
import service.FavoriteServiceImpl;
import service.IFavoriteService;

@WebServlet(name = "FavoriteServlet", urlPatterns = {"/favorites"})
public class FavoriteServlet extends HttpServlet {

    private IFavoriteService favoriteService = new FavoriteServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sort = request.getParameter("sort");
        sort = sort != null ? sort : "";

        switch (sort) {
            case "title":
                sortByTitle(request, response);
                break;
            case "default":
                sortByDefault(request, response);
                break;
            default:
                loadFavMovies(request, response);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    //load favorite movie
    private void loadFavMovies(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Movie> favList;
        if (session.getAttribute("favoriteList") != null) {
            favList = (List<Movie>) session.getAttribute("favoriteList");
        } else {
            User user = (User) session.getAttribute(SessionAttribute.USER_INFOR);
            int userId = user.getId();
            favList = favoriteService.selectFavoriteByUserId(userId);
            session.setAttribute("favoriteList", favList);
        }
        request.setAttribute("movieCount", favList.size());
        request.getRequestDispatcher(PageLink.FAVORITE_PAGE).forward(request, response);
    }

    //sort by alphabet
    private void sortByTitle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Movie> favList = (List<Movie>) session.getAttribute("favoriteList");
        System.out.println(favList);
        favList.sort(((o1, o2) -> o1.getMovieName().compareTo(o2.getMovieName())));
        request.setAttribute("favoriteList", favList);
        favList = (List<Movie>) session.getAttribute("favoriteList");
        System.out.println(favList);
        request.setAttribute("movieCount", favList.size());
        request.getRequestDispatcher(PageLink.FAVORITE_PAGE).forward(request, response);
    }

    //sort by alphabet
    private void sortByDefault(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Movie> favList = (List<Movie>) session.getAttribute("favoriteList");
        request.setAttribute("movieCount", favList.size());
        System.out.println(favList);
        request.getRequestDispatcher(PageLink.FAVORITE_PAGE).forward(request, response);
    }

}
