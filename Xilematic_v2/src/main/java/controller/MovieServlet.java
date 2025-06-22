package controller;

import constant.PageLink;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;
import service.MovieService;

@WebServlet(name = "MovieServlet", urlPatterns = {"/movies"})
public class MovieServlet extends HttpServlet {

    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        action = action == null ? "" : action;
        switch (action) {
            case "showDetail":
                processShowDetail(request, response);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        action = action == null ? "" : action;
        switch (action) {
            case "add_movie":
                processAddMovie(request, response);
                break;
            case "update_movie":
                processUpdateMovie(request, response);
                break;
            case "delete":
                processDeleteMovie(request, response);
                break;
            default:
                break;
        }
    }

    //process add function
    private void processAddMovie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Movie movie = (Movie) request.getAttribute("movie");
        movie.setReleaseDate(request.getParameter("releaseDate"));
        movieService.insertMovie(movie);
        response.sendRedirect("paging?type=movies");
    }

    //process update function
    private void processUpdateMovie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Movie movie = (Movie) request.getAttribute("movie");
        movie.setHot("on".equals(request.getParameter("hot")));
        movie.setReleaseDate(request.getParameter("releaseDate"));
        movieService.updateMovie(movie);
        response.sendRedirect("paging?type=movies");
    }

    //process delete function
    private void processDeleteMovie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        movieService.deleteMovie(id);
        response.sendRedirect("paging?type=movies");
    }

    //show detail
    private void processShowDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Movie mv = movieService.getMovie(Integer.parseInt(id));
        request.setAttribute("movie", mv);
        request.getRequestDispatcher(PageLink.EDIT_MOVIE_PAGE).forward(request, response);
    }

}
