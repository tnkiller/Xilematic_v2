package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CumRap;
import model.HeThongRap;
import model.LichChieu;
import model.Movie;
import com.google.gson.Gson;
import java.time.format.DateTimeFormatter;
import model.RapPhim;
import service.BookingService;
import service.MovieService;

@WebServlet(name="SelectCalendarServlet", urlPatterns={"/SelectCalendar"})
public class SelectCalendarServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SelectCalendarServlet.class.getName());
    private BookingService bookingService;
    private MovieService movieService;

    @Override
    public void init() throws ServletException {
       bookingService = new BookingService();
       movieService = new MovieService();
    }
  

    private void getCumRap(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int heThongRapId = Integer.parseInt(request.getParameter("heThongRapId")) ;
       
        List<CumRap> listCumRap = bookingService.getCumRapByHeThongRapId(heThongRapId);
        System.out.println("list cum rap size :"+ listCumRap.size());
        for(CumRap cumRap : listCumRap){
            System.out.println(cumRap.toString());
        }
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
//        StringBuilder json = new StringBuilder("[");
//        for (int i = 0; i < listCumRap.size(); i++) {
//            CumRap cr = listCumRap.get(i);
//            json.append("{\"ma\":\"").append(cr.getMaCumRap()).append("\",\"ten\":\"").append(cr.getTenCumRap()).append("\"}");
//            if (i < listCumRap.size() - 1) {
//                json.append(",");
//            }
//        }
//        json.append("]");
//        out.print(json.toString());
//        out.flush();
    String json = new Gson().toJson(listCumRap);
        System.out.println(json);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(json);
    }
     private void getRapPhim(HttpServletRequest request, HttpServletResponse response) throws IOException {
          int cumRapID = Integer.parseInt(request.getParameter("cumRapId")) ;
          List<RapPhim> rapPhims = bookingService.getRapByCumRapId(cumRapID);
          System.out.println("List rap sizw : "+rapPhims.size());
          for(RapPhim rap : rapPhims){
              System.out.println(rap);
          }
          String json = new Gson().toJson(rapPhims);
        System.out.println(json);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(json);

    }
   

    private void getLichChieu(HttpServletRequest request, HttpServletResponse response)    
        throws ServletException, IOException {
   
        response.setContentType("text/html;charset=UTF-8");
   
        try (PrintWriter out = response.getWriter()) {
            int maPhim = Integer.parseInt(request.getParameter("maPhim")); // Đọc đúng tên tham số "maPhim"
            String ngayChieu = request.getParameter("ngayChieu");
            int rapPhimId = Integer.parseInt(request.getParameter("rapPhimId")); 

            List<LichChieu> listLichChieu = bookingService.getLichChieu( maPhim,rapPhimId, ngayChieu);
             for(LichChieu lichChieu : listLichChieu){
                 System.out.println(lichChieu);
             }
            StringBuilder htmlBuilder = new StringBuilder();

            if (listLichChieu != null && !listLichChieu.isEmpty()) {
                htmlBuilder.append("<h3>Lịch chiếu có sẵn:</h3>");
                for (LichChieu lc : listLichChieu) {
                    System.out.println("Kiểu dữ liệu: " + lc.getNgayGioChieu().getClass().getName());
                    String gioChieu = lc.getNgayGioChieu().format(DateTimeFormatter.ofPattern("HH:mm"));
                    htmlBuilder.append("<div class='suat-chieu-item'>");
 
                    htmlBuilder.append("<span>Suất: <strong class='gio-chieu'>").append(gioChieu).append("</strong></span>");
                    htmlBuilder.append("<a href='booking?ma_lich_chieu=").append(lc.getMaLichChieu()).append("' class='btn-chon-ghe'>Chọn Ghế</a>");
                    htmlBuilder.append("</div>");
                }
            } else {
                htmlBuilder.append("<p>Rất tiếc, không có lịch chiếu phù hợp với lựa chọn của bạn.</p>");
            }
            out.print(htmlBuilder.toString());
       
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getLichChieu", e);
            response.getWriter().println("<p>Đã có lỗi xảy ra khi tải lịch chiếu. Vui lòng thử lại.</p>");
        }
    }
     private void getLichChieuTheoCumRap(HttpServletRequest request, HttpServletResponse response)    
    throws ServletException, IOException {
    
    response.setContentType("text/html;charset=UTF-8");
    
    try (PrintWriter out = response.getWriter()) {
        int maPhim = Integer.parseInt(request.getParameter("maPhim"));
        int cumRapId = Integer.parseInt(request.getParameter("cumRapId")); 

        // Lấy danh sách rạp phim trong cụm rạp
        List<RapPhim> rapPhims = bookingService.getRapByCumRapId(cumRapId);
        
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div class='showtimes-container'>");
        
        if (rapPhims != null && !rapPhims.isEmpty()) {
            for (RapPhim rapPhim : rapPhims) {
                // Lấy lịch chiếu cho từng rạp phim
                List<LichChieu> listLichChieu = bookingService.getLichChieuByRapPhimAndPhim(rapPhim.getMaRap(), maPhim);
                
                if (listLichChieu != null && !listLichChieu.isEmpty()) {
                    // Thêm thông tin rạp
                    htmlBuilder.append("<div class='theater-info'>");
                    htmlBuilder.append("<i class='fas fa-map-marker-alt theater-icon'></i>");
                    htmlBuilder.append("<div>");
                    htmlBuilder.append("<div class='theater-name'>").append(rapPhim.getTenRap()).append("</div>");
                    htmlBuilder.append("</div>");
                    htmlBuilder.append("</div>");
                    
                    // Bắt đầu grid suất chiếu
                    htmlBuilder.append("<div class='showtime-grid'>");
                    
                    for (LichChieu lc : listLichChieu) {
                        String gioChieu = lc.getNgayGioChieu().format(DateTimeFormatter.ofPattern("HH:mm"));
                        
                        htmlBuilder.append("<div class='showtime-item'>");
                        htmlBuilder.append("<div class='showtime-time'>").append(gioChieu).append("</div>");
                        htmlBuilder.append("<div class='showtime-type'>2D Phụ đề</div>");
                        htmlBuilder.append("<div class='showtime-seats'>Còn 50 chỗ</div>");
                        htmlBuilder.append("<button class='btn-book' onclick=\"window.location.href='booking?ma_lich_chieu=")
                                .append(lc.getMaLichChieu()).append("'\">Đặt vé</button>");
                        htmlBuilder.append("</div>");
                    }
                    
                    htmlBuilder.append("</div>"); // Đóng showtime-grid
                }
            }
            
            // Nếu không có suất chiếu nào
            if (htmlBuilder.toString().equals("<div class='showtimes-container'>")) {
                htmlBuilder.append("<div class='empty-state'>");
                htmlBuilder.append("<i class='far fa-calendar-times'></i>");
                htmlBuilder.append("<h3>Không có suất chiếu</h3>");
                htmlBuilder.append("<p>Không tìm thấy suất chiếu nào cho phim này tại cụm rạp đã chọn</p>");
                htmlBuilder.append("</div>");
            }
        } else {
            htmlBuilder.append("<div class='empty-state'>");
            htmlBuilder.append("<i class='far fa-building'></i>");
            htmlBuilder.append("<h3>Không có rạp</h3>");
            htmlBuilder.append("<p>Không tìm thấy rạp phim trong cụm rạp này</p>");
            htmlBuilder.append("</div>");
        }
        
        htmlBuilder.append("</div>"); // Đóng showtimes-container
        
        out.print(htmlBuilder.toString());
    
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error in getLichChieuTheoCumRap", e);
        response.getWriter().println("<p>Đã có lỗi xảy ra khi tải lịch chiếu. Vui lòng thử lại.</p>");
    }
}


@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
    System.out.println("--- 1. doGet() CALLED ---"); // Checkpoint 1

    String action = request.getParameter("action");
    if(action == null){
      action = ""; 
    }
    
    System.out.println("--- 2. Action = '" + action + "' ---"); // Checkpoint 2
    
    switch (action) {
        case "getCumRap":
            getCumRap(request, response);
            break;
        case "getRapPhim":
            getRapPhim(request, response);
            break;
        case "getLichChieu":
            getLichChieu(request, response);
            break;  
            case "getLichChieuTheoCumRap":
        getLichChieuTheoCumRap(request, response);
        break;
    
        default:
            System.out.println("--- 3. Switch -> default case -> calling loadInitialPage() ---"); // Checkpoint 3
            loadInitialPage(request, response);
    }
}

private void loadInitialPage(HttpServletRequest request, HttpServletResponse response) {
    try {
        List<HeThongRap> listHeThongRap = bookingService.getAllHeThongRap();

        // Kiểm tra nếu có tham số id 
        String movieId = request.getParameter("id");
        if (movieId != null) {
            // Chuyển đổi movieId sang kiểu int nếu cần
            try {
                int selectedMovieId = Integer.parseInt(movieId);
                Movie movie= movieService.getMovie(selectedMovieId);
                System.out.println(movie);
                request.setAttribute("selectedMovie", movie);
            } catch (NumberFormatException e) {
                // Xử lý nếu movieId không hợp lệ
                System.out.println("Invalid movie ID: " + movieId);
            }
        }
        request.setAttribute("listHeThongRap", listHeThongRap);
        
        request.getRequestDispatcher("home/booking.jsp").forward(request, response);
        
    } catch (Exception ex) {
        LOGGER.log(Level.SEVERE, "--- ERROR in loadInitialPage ---", ex);
    }
}


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

   
    
    
}