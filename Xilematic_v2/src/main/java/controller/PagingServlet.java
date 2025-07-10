package controller;

import constant.PageLink;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CumRap;
import model.RapPhim;
import model.Seat;
import service.*;

@WebServlet(name = "PagingController", urlPatterns = {"/paging"})
public class PagingServlet extends HttpServlet {

    private final IUserService userService = new UserService();
    private final IMovieService movieService = new MovieService();
    private final IHeThongRapService heThongRap = new HeThongRapService();
    private final IRapPhimService rapPhimService = new RapPhimService();
    private final ICumRapService cumRapService = new CumRapService();
    private final ISeatService seatService = new SeatService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageSize = 6;

        String pageParam = request.getParameter("page");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;

        String type = request.getParameter("type");
        if (type == null) {
            type = "stats";
        }

        int maRap = 0;
        if ("ghe".equals(type)) {
            String maRapParam = request.getParameter("maRap");
            if (maRapParam != null && !maRapParam.isEmpty()) {
                try {
                    maRap = Integer.parseInt(maRapParam);
                    request.setAttribute("maRap", maRap);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            List<RapPhim> listRapPhim = rapPhimService.getAllRapPhim();
            request.setAttribute("listRapPhim", listRapPhim);
            List<Seat> listGhe = seatService.getSeatsByCinemaId(maRap);
            request.setAttribute("listGhe", listGhe);
        }

        List<?> data = null;
        try {
            data = getDataForPagination(type, page, pageSize, maRap);
        } catch (SQLException ex) {
            Logger.getLogger(PagingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        int totalItems = getTotalItemCount(type, maRap);
        int totalPages = (int) Math.ceil(totalItems / (double) pageSize);

        request.setAttribute("list", data);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("type", type);

        if ("rapPhim".equals(type)) {
            List<CumRap> listCumRap = cumRapService.getAllCumRap();
            request.setAttribute("listCumRap", listCumRap);
        }

        request.getRequestDispatcher(PageLink.ADMIN_PAGE).forward(request, response);
    }

    //lấy list tương ứng theo type
    private List<?> getDataForPagination(String type, int page, int pageSize, int maRap) throws SQLException {
        return switch (type) {
            case "users" ->
                userService.getUsersForPage(page, pageSize);
            case "movies" ->
                movieService.getMoviesForPage(page, pageSize);
            case "heThongRap" ->
                heThongRap.getHeThongRapsForPage(page, pageSize);
            case "cumRap" ->
                heThongRap.getCumRapForPage(page, pageSize);
            case "rap" ->
                heThongRap.getRapPhimsForPage(page, pageSize);
            case "lichChieu" ->
                heThongRap.getLichChieusForPage(page, pageSize);
            case "rapPhim" ->
                rapPhimService.getRapPhimForPage(page, pageSize);
            case "ghe" ->
                seatService.getSeatForPage(maRap, page, pageSize);
            default ->
                new ArrayList<>();
        };
    }

    //lấy tổng số lượng phần tử theo từng loại list
    private int getTotalItemCount(String type, int maRap) {
        return switch (type) {
            case "users" ->
                userService.getTotalUsersCount();
            case "movies" ->
                movieService.getTotalMoviesCount();
            case "heThongRap" ->
                heThongRap.getTotalHeThongRap();
            case "cumRap" ->
                heThongRap.getTotalCumRap();
            case "rapPhim" ->
                rapPhimService.getTotalRapPhim();
            case "ghe" ->
                seatService.getTotalSeatInCinema(maRap);
            case "lichChieu" ->
                heThongRap.getTotalLichChieu();
            default ->
                0;
        };
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
