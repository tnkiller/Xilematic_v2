package service;

import java.util.List;
import model.Movie;

public interface IMovieService {

    public void insertMovie(Movie movie);

    public Movie getMovie(int id);

    public List<Movie> getAllMovies();

    public boolean deleteMovie(int id);

    public boolean updateMovie(Movie Movie);

    public List<Movie> getMoviesForPage(int currentPage, int pageSize);

    public int getTotalMoviesCount();

    public String getMovieShowtimeByCinema(int ma_rap, int ma_phim);
}
