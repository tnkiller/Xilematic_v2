
package dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import model.Movie;


public interface IMovieDAO {

    public void insertMovie(Movie movie) throws SQLException;

    public Movie selectMovie(int id) throws SQLException;

    public List<Movie> selectAllMovies() throws SQLException;

    public boolean deleteMovie(int id) throws SQLException;

    public boolean updateMovie(Movie movie) throws SQLException;

    public List<Movie> getMovieForPage(int currentPage, int pageSize) throws SQLException;

    public int getTotalMoviesCount() throws SQLException;


    public LocalDateTime getMovieShowtimeByCinema(int ma_rap, int ma_phim) throws SQLException;

}
