/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import constant.PageLink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.Favorite;
import model.Movie;
import model.User;
import service.FavoriteServiceImpl;
import service.IFavoriteService;
import utils.SessionUtil;

@WebServlet(name = "FavoriteServlet", urlPatterns = {"/favorites"})
public class FavoriteServlet extends HttpServlet {

    private IFavoriteService favoriteService = new FavoriteServiceImpl();

    // DO GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        action = action != null ? action : "";

        switch (action) {
            case "delete":
                processRemove(request, response);
                break;
            case "load":
                loadFavMovies(request, response);
                break;
            case "sort":
                processSort(request, response);
                break;
            default:
                PrintWriter out = response.getWriter();
                out.println("cha co con me gi o day ca");
                break;
        }
    }

    // DO POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        StringBuilder jsonString = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString.toString(), JsonObject.class);
        String action = jsonObject.get("action").getAsString();
        int movieId = Integer.parseInt(jsonObject.get("movieId").getAsString());
        int userId = Integer.parseInt(jsonObject.get("userId").getAsString());
        switch (action) {
            case "add":
                processAddMovieIntoFavList(request, response, movieId, userId);
                break;
            case "remove":
                processDelete(request, response, movieId, userId);
                break;
            default:
                PrintWriter out = response.getWriter();
                out.println("cha co con me gi o day ca");
                break;
        }
        JsonObject responseJson = new JsonObject();
        System.out.println("Received data: " + jsonString.toString());
        responseJson.addProperty("message", "Đã nhận dữ liệu: " + jsonString.toString());
    }

    // load favorite movie
    private void loadFavMovies(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionUtil.USER_INFOR);
        int userId = user.getId();
        List<Movie> favList = favoriteService.selectFavoriteByUserId(userId);
        request.setAttribute("favoriteList", favList);
        request.setAttribute("movieCount", favList.size());
        request.getRequestDispatcher(PageLink.FAVORITE_PAGE).forward(request, response);
    }

    // process delete function
    private void processDelete(HttpServletRequest request, HttpServletResponse respons, int movieId, int userId)
            throws ServletException, IOException {
        favoriteService.deleteFavoriteByCondition(userId, movieId);
        updateSession(request, respons, movieId, userId);
    }

    // process remove function
    private void processRemove(HttpServletRequest request, HttpServletResponse respons)
            throws ServletException, IOException {
        int movieId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionUtil.USER_INFOR);
        int userId = user.getId();
        favoriteService.deleteFavoriteByCondition(userId, movieId);
        updateSession(request, respons, movieId, userId);
        respons.sendRedirect(PageLink.FAVORITE_SERVLET + "action=load");
    }

    // process sort function
    private void processSort(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sort = request.getParameter("sort");
        sort = sort != null ? sort : "";

        switch (sort) {
            case "title":
                sortByTitle(request, response);
                break;
            default:
                loadFavMovies(request, response);
                break;
        }
    }

    // sort by alphabet
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

    // process adding a movie to favorites
    private void processAddMovieIntoFavList(HttpServletRequest request, HttpServletResponse response, int movieId,
            int userId)
            throws IOException {
        favoriteService.insertFavorite(new Favorite(userId, movieId));
        updateSession(request, response, movieId, userId);
    }

    // update session
    private void updateSession(HttpServletRequest request, HttpServletResponse response, int movieId,
            int userId) {
        HttpSession session = request.getSession();
        List<Movie> favList = (List<Movie>) session.getAttribute(SessionUtil.FAV_LIST);
        favList = favoriteService.selectFavoriteByUserId(userId);
        session.setAttribute(SessionUtil.FAV_LIST, favList);
    }

}
