/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import constant.PageLink;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import model.CumRap;
import model.HeThongRap;
import model.LichChieu;
import model.RapPhim;
import service.HeThongRapService;

/**
 *
 * @author ASUS
 */
@WebServlet(name="TheaterSystemServlet", urlPatterns={"/TheaterSystemServlet"})
public class TheaterSystemServlet extends HttpServlet {
    private final HeThongRapService heThongRapService = new HeThongRapService();
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         String action = request.getParameter("action");
         if ( action == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters.");
        return;
    }
         switch (action) {
            case "heThongRap":
                showEditFormHeTHongRap(request, response);
                break;
            case "cumRap":
                showEditFormCumRap(request, response);
                break;
            case "rap":
                showEditFormRap(request, response);
                break;
            case "lichChieu":
                showEditFormLichChieu(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

     private void showEditFormHeTHongRap(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HeThongRap existing = heThongRapService.getHeThongRapById(id);
         System.out.println(existing);
        request.setAttribute("heThongRap", existing);
        request.getRequestDispatcher(PageLink.EDIT_HETHONGRAP_PAGE).forward(request, response);
    }
     private void showEditFormCumRap(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CumRap existing = heThongRapService.getCumRapById(id);
        request.setAttribute("cumRap", existing);
        request.getRequestDispatcher(PageLink.EDIT_CUMRAP_PAGE).forward(request, response);
    }
     private void showEditFormRap(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        RapPhim existing = heThongRapService.getRapPhimById(id);
        request.setAttribute("rapPhim", existing);
        request.getRequestDispatcher(PageLink.EDIT_RAP_PAGE).forward(request, response);
    }
     private void showEditFormLichChieu(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        LichChieu existing = heThongRapService.getLichChieuById(id);
        request.setAttribute("lichChieu", existing);
        request.getRequestDispatcher(PageLink.EDIT_LICHCHIEU_PAGE).forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String type = request.getParameter("typeEdit");
        String action = request.getParameter("action");
         if (type == null || action == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters.");
        return;
    }
        
        switch (type) {
            case "heThongRap":
                    HeThongRap heThongRap =(HeThongRap) request.getAttribute("heThongRap");
                    System.out.println(heThongRap);
                    switch (action) {
                    case "create":
                            heThongRapService.addHeThongRap(heThongRap);
                            response.sendRedirect("paging");
                        break;
                       
                    case "delete":
                        String maHeThongRapStr = request.getParameter("id");
                            if (maHeThongRapStr != null && !maHeThongRapStr.isEmpty()) {
                                try {
                                    int maHeThongRap = Integer.parseInt(maHeThongRapStr);
                                    heThongRapService.deleteHeThongRap(maHeThongRap);
                                    response.sendRedirect("paging");
                                } catch (NumberFormatException e) {
                                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid he thong Rap ID");
                                }
                            } else {
                                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "he thong rap  ID missing");
                            }
                        break;
                       
                    case "update":
                        
                        heThongRapService.updateHeThongRap(heThongRap);
                        response.sendRedirect("paging");
                        
                        break;
                       
                }
                break;
            case "cumRap":
                CumRap cumRap = (CumRap) request.getSession().getAttribute("cumRap");
                switch (action) {
                    case "create":
                            heThongRapService.addCumRap(cumRap);
                            response.sendRedirect("paging");
                        break;
                       
                    case "delete":
                        String maCumRapStr = request.getParameter("id");
                            if (maCumRapStr != null && !maCumRapStr.isEmpty()) {
                                try {
                                    int maCumRap = Integer.parseInt(maCumRapStr);
                                    heThongRapService.deleteCumRap(maCumRap);
                                    response.sendRedirect("paging");
                                } catch (NumberFormatException e) {
                                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid CumRap ID");
                                }
                            } else {
                                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "CumRap ID missing");
                            }
                        break;
                       
                    case "update":
                        heThongRapService.updateCumRap(cumRap);
                        response.sendRedirect("paging");
                        break;
                }
                break;
            case "rapPhim":
                RapPhim rap = (RapPhim) request.getSession().getAttribute("rap");
                switch (action) {
                    case "create":
                            heThongRapService.addRapPhim(rap);
                            response.sendRedirect("paging");
                        break;
                       
                    case "delete":
                        String maRapStr = request.getParameter("id");
                        if (maRapStr != null && !maRapStr.isEmpty()) {
                            try {
                                int maRap = Integer.parseInt(maRapStr);
                                heThongRapService.deleteRapPhim(maRap);
                                response.sendRedirect("paging");
                            } catch (NumberFormatException e) {
                                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Rap ID");
                            }
                        } else {
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Rap ID missing");
                        }
                        break;
                       
                    case "update":
                        heThongRapService.updateRapPhim(rap);
                        response.sendRedirect("paging");
                        break;
                }
                break;
            case "lichChieu":
                LichChieu lichChieu = (LichChieu) request.getSession().getAttribute("lichChieu");
                lichChieu.setNgayGioChieu(LocalDateTime.parse(request.getParameter("ngayGioChieu")));
                switch (action) {
                    case "create":
                            heThongRapService.addLichChieu(lichChieu);
                            response.sendRedirect("paging");
                        break;
                       
                    case "delete":
                        String lichChieuStr = request.getParameter("id");
                        if (lichChieuStr != null && !lichChieuStr.isEmpty()) {
                            try {
                                int maLichChieu = Integer.parseInt(lichChieuStr);
                                heThongRapService.deleteLichChieu(maLichChieu);
                                response.sendRedirect("paging");
                            } catch (NumberFormatException e) {
                                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Lich chieu ID");
                            }
                        } else {
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "lich chieu ID missing");
                        }
                        break;
                       
                    case "update":
                        heThongRapService.updateLichChieu(lichChieu);
                        response.sendRedirect("paging");
                        break;
                }

                break;
            
                default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown typeEdit parameter: " + type);

        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
