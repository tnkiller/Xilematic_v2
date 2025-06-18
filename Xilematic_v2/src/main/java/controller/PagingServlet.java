
package controller;

import constant.PageLink;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import service.IMovieService;
import service.MovieService;
import service.IUserService;
import service.UserService;


@WebServlet(name = "PagingController", urlPatterns = {"/paging"})
public class PagingServlet extends HttpServlet {

    private final IUserService userService = new UserService();
    private final IMovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageSize = 6;
        String pageParam = request.getParameter("page");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;

        String type = request.getParameter("type");
        if (type == null) {
            type = "stats";  // Đặt loại dữ liệu mặc định
        }

        List<?> data = getDataForPagination(type, page, pageSize);

        // Tính toán số trang
        int totalItems = getTotalItemCount(type);
        int totalPages = (int) Math.ceil(totalItems / (double) pageSize);

        // Đưa thông tin vào request
        request.setAttribute("list", data);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("type", type);

        // Chuyển đến JSP tương ứng
        request.getRequestDispatcher(PageLink.ADMIN_PAGE).forward(request, response);
    }

    //lấy list tương ứng theo type
    private List<?> getDataForPagination(String type, int page, int pageSize) {
        return switch (type) {
            case "users" ->
                userService.getUsersForPage(page, pageSize);
            case "movies" ->
                movieService.getMoviesForPage(page, pageSize);
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
            default ->
                0;
        };
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
