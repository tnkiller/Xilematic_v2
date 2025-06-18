
package controller;

import service.CommentService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BinhLuan;
import model.Movie;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import service.MovieService;
import service.UserService;


@WebServlet(name="DetailServlet", urlPatterns={"/DetailServlet"})
public class DetailServlet extends HttpServlet {
        private static final Logger LOGGER = Logger.getLogger(HomeServlet.class.getName());
    
    private MovieService movieService;
    private CommentService commentService;
    private UserService userServiceImpl;

    @Override
    public void init() throws ServletException {
        try {
            movieService = new MovieService();
            commentService = new CommentService();
            userServiceImpl = new UserService();
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing MovieService", e);
            throw new ServletException("Could not initialize MovieService", e);
        }
    }
    
   

    
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
   try {
        // Log chi tiết thông tin đầu vào
       
        String movieIdParam = request.getParameter("id");
        LOGGER.info("Received movie ID: " + movieIdParam);
        if (movieIdParam == null || movieIdParam.isEmpty()) {
            LOGGER.warning("Missing movie ID");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID phim");
            return;
        }

        int movieId;
        try {
            movieId = Integer.parseInt(movieIdParam);
        } catch (NumberFormatException e) {
            LOGGER.severe("Invalid movie ID format: " + movieIdParam);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID phim không hợp lệ");
            return;
        }

        Movie movie = movieService.getMovie(movieId);
        
        if (movie == null) {
            LOGGER.warning("Movie not found for ID: " + movieId);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy phim");
            return;
        }

        request.setAttribute("movie", movie);
        
        List<BinhLuan> danhSachBinhLuan;
        try {
            danhSachBinhLuan = commentService.layBinhLuanTheoPhim(movieId);
            for (BinhLuan bl : danhSachBinhLuan) {
                User user = userServiceImpl.getUser(bl.getMaNguoiDung());
                bl.setTenNguoiDung(user != null ? user.getFullname() : "Người dùng ẩn");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching comments", e);
            danhSachBinhLuan = Collections.emptyList(); // Tránh null
        }
        
        request.setAttribute("comments", danhSachBinhLuan);

        request.getRequestDispatcher("home/detail.jsp").forward(request, response);
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Unexpected error in DetailServlet", e);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                           "Đã xảy ra lỗi: " + e.getMessage());
    }
}


    
    
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("userInfor") == null) {
        sendErrorResponse(response, "Vui lòng đăng nhập");
        return;
    }

    User nguoiDung = (User) session.getAttribute("userInfor");

    try {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        JSONObject jsonObject = new JSONObject(jsonBuilder.toString());
        int maPhim = jsonObject.getInt("movieId");
        String noiDung = jsonObject.getString("content");

        // Thêm validation
        if (noiDung == null || noiDung.trim().isEmpty()) {
            sendErrorResponse(response, "Nội dung bình luận không được để trống");
            return;
        }

        BinhLuan binhLuan = new BinhLuan();
        binhLuan.setMaNguoiDung(nguoiDung.getId());
        binhLuan.setMaPhim(maPhim);
        binhLuan.setNoiDung(noiDung.trim()); // Trim để loại bỏ khoảng trắng thừa
        binhLuan.setNgayTao(LocalDate.now());
        binhLuan.setTrangThai(true);

        boolean ketQua = commentService.themBinhLuan(binhLuan);

        PrintWriter out = response.getWriter();
        JSONObject responseJson = new JSONObject();
        responseJson.put("success", ketQua);
        
        if (ketQua) {
            responseJson.put("message", "Bình luận đã được thêm thành công");
            responseJson.put("commentId", binhLuan.getMaBinhLuan());
            // Thêm thông tin người dùng để client có thể hiển thị
            responseJson.put("username", nguoiDung.getFullname());
            responseJson.put("commentDate", LocalDate.now().toString());
        } else {
            responseJson.put("message", "Thêm bình luận : "+binhLuan.toString()+ " thất bại");
        }
        out.print(responseJson.toString());
        out.flush();

    } catch (JSONException e) {
        sendErrorResponse(response, "Dữ liệu JSON không hợp lệ");
    } catch (Exception e) {
        sendErrorResponse(response, "Đã xảy ra lỗi: " + e.getMessage());
    }
}


    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        JSONObject errorJson = new JSONObject();
        errorJson.put("success", false);
        errorJson.put("message", message);
        
        PrintWriter out = response.getWriter();
        out.print(errorJson.toString());
        out.flush();
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
