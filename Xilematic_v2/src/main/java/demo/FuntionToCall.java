package demo;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
		List<Movie> movies = list.stream().filter(m->m.getMovieName().equalsIgnoreCase(filmName1)).collect(Collectors.toList());
		return movies;
	}

    public List<Movie> showFilmHot() {
		List<Movie> hotMovies = list.stream()
		        .filter(Movie::isHot)
		        .collect(Collectors.toList());
		return  hotMovies;
	}
    public List<Movie> recommendMoviesByTime(LocalDateTime desiredTime) throws SQLException {
        List<Showtime> showtimes = showtime.getAllShowtimes(); // cần service này
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return showtimes.stream()
            .filter(s -> {
                try {
                    LocalDateTime showtimeDateTime = LocalDateTime.parse(s.getNgay_gio_chieu(), formatter);
                    return showtimeDateTime.toLocalDate().equals(desiredTime.toLocalDate()) &&
                           showtimeDateTime.getHour() == desiredTime.getHour();
                } catch (Exception e) {
                    return false; // bỏ qua nếu lỗi định dạng
                }
            })
            .map(s -> movieService.getMovie(s.getMa_phim())) // cần viết thêm hàm này nếu chưa có
            .distinct()
            .collect(Collectors.toList());
    }

	
}
