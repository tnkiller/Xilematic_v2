package service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;
import dao.MovieDAO;
import utils.Helper;

public class MovieService implements IMovieService {

    private MovieDAO movieDao = new MovieDAO();
    private Helper helper = new Helper();

    @Override
    public void insertMovie(Movie movie) {
        try {
            movieDao.insertMovie(movie);
        } catch (SQLException ex) {
            Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Movie getMovie(int id) {
        try {
            return movieDao.selectMovie(id);
        } catch (SQLException ex) {
            Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Movie> getAllMovies() {
        try {
           return movieDao.selectAllMovies();
        } catch (SQLException ex) {
            Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean deleteMovie(int id) {
        try {
            return movieDao.deleteMovie(id);
        } catch (SQLException ex) {
            Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean updateMovie(Movie movie) {
        try {
            return movieDao.updateMovie(movie);
        } catch (SQLException ex) {
            Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Movie> getMoviesForPage(int currentPage, int pageSize) {
        try {
            return movieDao.getMovieForPage(currentPage, pageSize);
        } catch (SQLException ex) {
            Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getTotalMoviesCount() {
        try {
            return movieDao.getTotalMoviesCount();
        } catch (SQLException ex) {
            Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    @Override
    public String getMovieShowtimeByCinema(int ma_rap, int ma_phim) {
        try {
            return helper.formatLocalDateTime(movieDao.getMovieShowtimeByCinema(ma_rap, ma_phim));
        } catch (SQLException ex) {
            Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "N/A";
    }

    public static void main(String[] args) {
        MovieService m = new MovieService();
        List<Movie> l = m.getAllMovies();
        for (Movie movie : l) {
            System.out.println(movie.toString());
        }
    }

    @Override
    public List<Movie> getMoviesByGenreName(String genreName) throws SQLException {
       return movieDao.getMoviesByGenreName(genreName);
    }
}
