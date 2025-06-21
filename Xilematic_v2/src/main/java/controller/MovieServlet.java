package controller;

import constant.PageLink;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
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
        int id = Integer.parseInt(request.getParameter("id"));
        String movieName = request.getParameter("movieName");
        String trailer = request.getParameter("trailer");
        String image = request.getParameter("image");
        String description = request.getParameter("description");
        String releaseDate = request.getParameter("releaseDate");
        String rate = request.getParameter("rate");
        String hot = request.getParameter("hot");
        String status = request.getParameter("status");
        String actor = request.getParameter("actor");
        String director = request.getParameter("director");

        boolean isHot = hot != null;
        boolean isShowing = status.equals("1");
        movieService.updateMovie(new Movie(id, movieName, trailer, image, description, releaseDate, Integer.parseInt(rate), isHot, isShowing, actor, director));
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
        request.getRequestDispatcher(PageLink.DETAIL_PAGE).forward(request, response);
    }

}
