
package controller;

import constant.PageLink;
import dao.IHeThongRapDAO;
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
import service.HeThongRapService;
import service.IHeThongRapService;
import service.IMovieService;
import service.MovieService;
import service.IUserService;
import service.UserService;


@WebServlet(name = "PagingController", urlPatterns = {"/paging"})
public class PagingServlet extends HttpServlet {

    private final IUserService userService = new UserService();
    private final IMovieService movieService = new MovieService();
    private final IHeThongRapService heThongRap = new HeThongRapService();

    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int pageSize = 6;
//        List<?> data=null;
//        int totalItems=0;
//        int totalPages=0;
//        String pageParam = request.getParameter("page");
//        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
//
//        String type = request.getParameter("type");
//        if (type == null) {
//            type = "stats";  // Đặt loại dữ liệu mặc định
//        }
//        if(type.equalsIgnoreCase("cumRap")||type.equalsIgnoreCase("Rap")||type.equalsIgnoreCase("lichChieu")){
//            // id đại diện cho rapId, cumRapId , heThỏngapId tùy theo loại type truyền vào 
//        int id = Integer.parseInt(request.getParameter("id")); 
//            try {
//                data = getDataForPagination(type, page, pageSize,id);
//            } catch (SQLException ex) {
//                Logger.getLogger(PagingServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        // Tính toán số trang
//        totalItems = getTotalItemCount(type,id);
//        totalPages = (int) Math.ceil(totalItems / (double) pageSize);
//        }else{
//         data = getDataForPagination(type, page, pageSize);
//
//        // Tính toán số trang
//         totalItems = getTotalItemCount(type);
//        totalPages = (int) Math.ceil(totalItems / (double) pageSize);
//        }
//        // Đưa thông tin vào request
//        request.setAttribute("list", data);
//        request.setAttribute("totalPages", totalPages);
//        request.setAttribute("currentPage", page);
//        request.setAttribute("type", type);
//
//        // Chuyển đến JSP tương ứng
//        request.getRequestDispatcher(PageLink.ADMIN_PAGE).forward(request, response);
//    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageSize = 6;
        List<?> data=null;
        int totalItems=0;
        int totalPages=0;
        String pageParam = request.getParameter("page");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;

        String type = request.getParameter("type");
        if (type == null) {
            type = "stats";  // Đặt loại dữ liệu mặc định
        }
        try {
            data = getDataForPagination(type, page, pageSize);
        } catch (SQLException ex) {
            Logger.getLogger(PagingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Tính toán số trang
         totalItems = getTotalItemCount(type);
        totalPages = (int) Math.ceil(totalItems / (double) pageSize);
        
        // Đưa thông tin vào request
        request.setAttribute("list", data);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("type", type);

        // Chuyển đến JSP tương ứng
        request.getRequestDispatcher(PageLink.ADMIN_PAGE).forward(request, response);
    }

    //lấy list tương ứng theo type
    private List<?> getDataForPagination(String type, int page, int pageSize) throws SQLException {
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
            default ->
                new ArrayList<>();
        };
    }
    private List<?> getDataForPagination(String type, int page, int pageSize,int id) throws SQLException {
        return switch (type) {
            case "cumRap" ->
                heThongRap.getCumRapByIDForPage(page, pageSize, id);
            
            case "rap" ->
                heThongRap.getRapPhimsByCumRapIDForPage(page, pageSize, id);
            
            case "lichChieu" ->
                heThongRap.getLichChieusByMaRapForPage(page, pageSize, id);
            
            default ->
                new ArrayList<>();
        };
    }
    

    //lấy tổng số lượng phần tử theo từng loại list
    private int getTotalItemCount(String type) {
        return switch (type) {
            case "users" ->
                userService.getTotalUsersCount();
            case "movies" ->
                movieService.getTotalMoviesCount();
            case "heThongRap"->
                heThongRap.getTotalHeThongRap();
            case "cumRap"->
                heThongRap.getTotalCumRap();
            case "rap"->
                heThongRap.getTotalRap();
            case "lichChieu"->
                heThongRap.getTotalLichChieu();    
            default ->
                0;
        };
    }
    private int getTotalItemCount(String type,int id ) {
        return switch (type) {
            case "cumRap"->
                heThongRap.getTotalCumRap(id);
            case "rap"->
                heThongRap.getTotalRap(id);
            case "lichChieu"->
                heThongRap.getTotalLichChieu(id);
            default ->
                0;
        };
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
