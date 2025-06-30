package controller;

import constant.PageLink;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.CumRap;
import model.RapPhim;
import service.CumRapService;
import service.ICumRapService;
import service.IRapPhimService;
import service.RapPhimService;

@WebServlet(name = "RapPhimServlet", urlPatterns = {"/rapPhim"})
public class RapPhimServlet extends HttpServlet {

    private IRapPhimService rapPhimService = new RapPhimService();
    private ICumRapService CumRapService = new CumRapService();

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
            case "add_rapPhim":
                processAddRapPhim(request, response);
                break;
            case "update_rapPhim":
                processUpdateRapPhim(request, response);
                break;
            case "delete":
                processDeleteRapPhim(request, response);
                break;
            default:
                break;
        }
    }

    private void processShowDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        RapPhim rap = rapPhimService.selectRapPhimByID(id);
        List<CumRap> cumRaps = CumRapService.getAllCumRap();
        request.setAttribute("rap", rap);
        request.setAttribute("cumRaps", cumRaps);
        request.getRequestDispatcher(PageLink.EDIT_RAP_PHIM_PAGE).forward(request, response);
    }

    private void processDeleteRapPhim(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int maRap = Integer.parseInt(request.getParameter("maRap"));
        rapPhimService.deleteRapPhim(maRap);
        response.sendRedirect("paging?type=rapPhim");
    }

    private void processUpdateRapPhim(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RapPhim rapPhim = (RapPhim) request.getAttribute("rapPhim");
        rapPhimService.updateRapPhim(rapPhim);
        response.sendRedirect("paging?type=rapPhim");
    }

    private void processAddRapPhim(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RapPhim rapPhim = (RapPhim) request.getAttribute("rapPhim");
        rapPhimService.addNewRapPhim(rapPhim);
        response.sendRedirect("paging?type=rapPhim");
    }
}
