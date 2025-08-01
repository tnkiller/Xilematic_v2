
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Movie;
import model.Seat;
import model.Showtime;
import service.MovieService;
import service.SeatService;
import service.ShowtimeService;

@WebServlet(name = "BookingServlet", urlPatterns = { "/booking" })
public class BookingServlet extends HttpServlet {

    private SeatService seatService = new SeatService();
    private MovieService movieService = new MovieService();
    private ShowtimeService showtimeService = new ShowtimeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int ma_lich_chieu = Integer.parseInt(req.getParameter("ma_lich_chieu"));

        Showtime showtime = showtimeService.getShowtimeInformationByID(ma_lich_chieu);
        int ma_phim = showtime.getMa_phim();
        Movie movie = movieService.getMovie(ma_phim);
        List<Seat> seats = seatService.getSeatsByCinemaId(showtime.getMa_rap());

        req.setAttribute("ma_lich_chieu", ma_lich_chieu);
        req.setAttribute("seats", seats);
        req.setAttribute("movie", movie);
        req.setAttribute("showtime", showtime);
        req.getRequestDispatcher("user/booking.jsp").forward(req, res);
    }

}