package demo;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import model.*;
import service.*;

public class FuntionToCall {
	private List<Movie> list;
        private MovieService movieService;
        private ShowtimeService showtime;
    public FuntionToCall(List<Movie> list) {
        this.list = list;
        movieService = new MovieService();
        showtime  = new ShowtimeService();
        
    }
    public List<Movie> findMoviesByGenre(String genre) throws SQLException {
    	List<Movie> movies = movieService.getMoviesByGenreName(genre);
    	return movies; 
		
	}

    public List<Movie> findMoviesByDirector(String directorName) {
        List<Movie> movies = list.stream()
            .filter(m -> m.getDirector() != null && m.getDirector().equalsIgnoreCase(directorName))
            .collect(Collectors.toList());
        return movies;
    }

    public String getBookingLink(String name) {
	    Movie movie = list.stream()
	            .filter(m -> m.getMovieName().equalsIgnoreCase(name))
	            .findFirst()
	            .orElse(null);

	    if (movie == null) {
	        return "Phim không tồn tại hoặc chưa được cập nhật.";
	    }

	    int movieId = movie.getId();
	    String bookingLink = "http://localhost:9999/xilematic/SelectCalendar?id=" + movieId;
	    return bookingLink;
	}


    public List<Movie> getDetailMovieByName(String filmName1) {
		List<Movie> movies = list.stream().filter(m->m.getMovieName().equalsIgnoreCase(filmName1)||m.getMovieName().contains(filmName1)).collect(Collectors.toList());
		return movies;
	}

    public List<Movie> showFilmHot() {
		List<Movie> hotMovies = list.stream()
		        .filter(Movie::isHot)
		        .collect(Collectors.toList());
		return  hotMovies;
	}
    public List<Movie> recommendMoviesByTime(LocalDateTime desiredTime) throws SQLException {
    List<Showtime> showtimes = showtime.getAllShowtimes();
    System.out.println("Total showtimes: " + showtimes.size());

    // Create multiple formatters to handle different date-time formats
    List<DateTimeFormatter> formatters = Arrays.asList(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
        DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("HH:mm - MM/dd/yyyy")
    );

    List<Movie> recommendedMovies = showtimes.stream()
        .filter(s -> {
            for (DateTimeFormatter formatter : formatters) {
                try {
                    LocalDateTime showtimeDateTime = LocalDateTime.parse(s.getNgay_gio_chieu(), formatter);
                    boolean isMatch = showtimeDateTime.toLocalDate().equals(desiredTime.toLocalDate()) &&
                                      showtimeDateTime.getHour() == desiredTime.getHour();
                    
                    if (isMatch) {
                        System.out.println("Matched Showtime: " + s.getNgay_gio_chieu());
                        return true;
                    }
                } catch (DateTimeParseException e) {
                    // Continue to next formatter if parsing fails
                    continue;
                }
            }
            return false;
        })
        .map(s -> {
            try {
                Movie movie = movieService.getMovie(s.getMa_phim());
                System.out.println("Movie found: " + (movie != null ? movie.getMovieName() : "NULL"));
                return movie;
            } catch (Exception e) {
                System.err.println("Error fetching movie for ID: " + s.getMa_phim());
                return null;
            }
        })
        .filter(Objects::nonNull)
        .distinct()
        .collect(Collectors.toList());

    System.out.println("Recommended Movies: " + recommendedMovies.size());
    return recommendedMovies;
}



	
}
