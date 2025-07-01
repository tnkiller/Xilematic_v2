package controller;

import constant.PageLink;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Seat;
import service.ISeatService;
import service.SeatService;

@WebServlet(name = "GheServlet", urlPatterns = {"/ghe"})
public class GheServlet extends HttpServlet {

    private ISeatService seatService = new SeatService();

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
            case "add_ghe":
                processAddGhe(request, response);
                break;
            case "update_ghe":
                processUpdateGhe(request, response);
                break;
            case "delete":
                processDeleteGhe(request, response);
                break;
            default:
                break;
        }
    }

    private void processShowDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int ma_ghe = Integer.parseInt(request.getParameter("ma_ghe"));
        Seat ghe = seatService.selectSeatByID(ma_ghe);
        request.setAttribute("ghe", ghe);
        request.getRequestDispatcher(PageLink.EDIT_GHE_PAGE).forward(request, response);
    }

    private void processDeleteGhe(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int maRap = Integer.parseInt(request.getParameter("maRap"));
        int ma_ghe = Integer.parseInt(request.getParameter("ma_ghe"));
        seatService.deleteSeat(ma_ghe);
        response.sendRedirect("paging?type=ghe&maRap=" + maRap);
    }

    private void processUpdateGhe(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Seat ghe = (Seat) request.getAttribute("ghe");
        int maRap = Integer.parseInt(request.getParameter("maRap"));
        seatService.updateSeat(ghe);
        response.sendRedirect("paging?type=ghe&maRap=" + maRap);
    }

    private void processAddGhe(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int maRap = Integer.parseInt(request.getParameter("maRap"));
        int soLuongGhe = Integer.parseInt(request.getParameter("soLuongGhe"));
        String gheVIP = request.getParameter("gheVIP");

        seatService.addSeatIntoCinema(maRap, soLuongGhe, gheVIP);
        response.sendRedirect("paging?type=ghe&maRap=" + maRap);
    }

}
