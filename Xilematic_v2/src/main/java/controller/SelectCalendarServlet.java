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
            
            StringBuilder htmlBuilder = new StringBuilder();

            if (listLichChieu != null && !listLichChieu.isEmpty()) {
                htmlBuilder.append("<h3>Lịch chiếu có sẵn:</h3>");
                for (LichChieu lc : listLichChieu) {
                    System.out.println("Kiểu dữ liệu: " + lc.getNgayGioChieu().getClass().getName());
                    String gioChieu = lc.getNgayGioChieu().format(DateTimeFormatter.ofPattern("HH:mm"));
                    htmlBuilder.append("<div class='suat-chieu-item'>");
 
                    htmlBuilder.append("<span>Suất: <strong class='gio-chieu'>").append(gioChieu).append("</strong></span>");
                    htmlBuilder.append("<a href='booking?lichChieuId=").append(lc.getMaLichChieu()).append("' class='btn-chon-ghe'>Chọn Ghế</a>");
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
        default:
            System.out.println("--- 3. Switch -> default case -> calling loadInitialPage() ---"); // Checkpoint 3
            loadInitialPage(request, response);
    }
}

private void loadInitialPage(HttpServletRequest request, HttpServletResponse response) {
    System.out.println("--- 4. INSIDE loadInitialPage() ---"); // Checkpoint 4
    try {
        List<HeThongRap> listHeThongRap = bookingService.getAllHeThongRap();
        List<Movie> listPhim = movieService.getAllMovies();

        // Checkpoint 5: Kiểm tra xem dữ liệu có bị null hoặc rỗng không
        System.out.println("--- 5. Data fetched: listHeThongRap size = " + (listHeThongRap != null ? listHeThongRap.size() : "null"));
        System.out.println("--- 5. Data fetched: listPhim size = " + (listPhim != null ? listPhim.size() : "null"));

        request.setAttribute("listHeThongRap", listHeThongRap);
        request.setAttribute("listPhim", listPhim);
        
        System.out.println("--- 6. Forwarding to booking.jsp... ---"); // Checkpoint 6
        request.getRequestDispatcher("home/booking.jsp").forward(request, response);
        
    } catch (Exception ex) {
        // Nếu có lỗi ở đây, nó sẽ được in ra
        LOGGER.log(Level.SEVERE, "--- ERROR in loadInitialPage ---", ex);
    }
}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

   
    
    
}