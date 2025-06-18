package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Booking;
import model.Showtime;
import service.BookingService;
import service.ShowtimeService;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    private ShowtimeService showtimeService = new ShowtimeService();
    private BookingService bookingService = new BookingService();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "confirm":
                fetchAddNewBooking(request, response);
                break;
            default:
                fetchInformationBooking(request, response);
        }

    }

    private void fetchInformationBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ma_lich_chieu = Integer.parseInt(request.getParameter("ma_lich_chieu"));
        String movieName = request.getParameter("movieName");

        Showtime showtime = showtimeService.getShowtimeInformationByID(ma_lich_chieu);
        String selectedSeats = request.getParameter("selectedSeats");
        long totalPrice = Long.parseLong(request.getParameter("totalPrice"));

        String tenRap = showtimeService.getCinemaNameByShowtimeID(ma_lich_chieu);
        String tenCumRap = showtimeService.getNameCinemaGroupByShowTimeID(ma_lich_chieu);

        request.setAttribute("movieName", movieName);
        request.setAttribute("tenRap", tenRap);
        request.setAttribute("tenCumRap", tenCumRap);
        request.setAttribute("ma_lich_chieu", ma_lich_chieu);
        request.setAttribute("showtime", showtime.getNgay_gio_chieu());
        request.setAttribute("selectedSeats", selectedSeats);
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("/page/checkout.jsp")
                .forward(request, response);
    }

    private void fetchAddNewBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ma_lich_chieu = Integer.parseInt(request.getParameter("ma_lich_chieu"));
        // viết logic lấy tai_khoan của user ở đây

        // giả lập dữ liệu
        int tai_khoan = 1;
        String selectedSeats = request.getParameter("selectedSeats");
        long totalPrice = Long.parseLong(request.getParameter("totalPrice"));

        Booking booking = new Booking(tai_khoan, ma_lich_chieu, selectedSeats, totalPrice);
        bookingService.addNewBooking(booking);

        request.setAttribute("totalPrice", totalPrice);
        request.getRequestDispatcher("/page/success.jsp")
                .forward(request, response);
    }

}
