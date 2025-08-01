package com.vnpay.common;

import constant.PageLink;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import model.Booking;
import model.Mail;
import model.User;
import service.BookingService;
import service.ShowtimeService;
import utils.SendEmail;
import utils.SessionUtil;

public class VnpayReturn extends HttpServlet {

    private ShowtimeService showtimeService = new ShowtimeService();
    private BookingService bookingService = new BookingService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MessagingException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);
            String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
            if (signValue.equals(vnp_SecureHash)) {
                if ("00".equals(vnp_TransactionStatus)) {
                    // Giao dịch thành công
                    fetchAddNewBooking(request, response);
                } else {
                    // Giao dịch thất bại hoặc bị hủy
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.removeAttribute("ma_lich_chieu");
                        session.removeAttribute("selectedSeats");
                    }
                    request.setAttribute("errorMessage", "Giao dịch không thành công hoặc đã bị hủy.");
                    request.getRequestDispatcher(PageLink.ERROR_PAGE).forward(request, response);
                }
            } else {
                // Chữ ký không hợp lệ
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.removeAttribute("ma_lich_chieu");
                    session.removeAttribute("selectedSeats");
                }
                request.setAttribute("errorMessage", "Chữ ký không hợp lệ.");
                request.getRequestDispatcher(PageLink.ERROR_PAGE).forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(VnpayReturn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(VnpayReturn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fetchAddNewBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MessagingException {
        HttpSession session = request.getSession(false);
        int ma_lich_chieu = (int) session.getAttribute("ma_lich_chieu");
        String selectedSeats = (String) session.getAttribute("selectedSeats");
        session.removeAttribute("ma_lich_chieu");
        session.removeAttribute("selectedSeats");

        User user = (User) request.getSession().getAttribute(SessionUtil.USER_INFOR);
        int tai_khoan = user.getId();
        String emailClient = user.getEmail();
        long totalPrice = Long.parseLong(request.getParameter("vnp_Amount")) / 100;

        Booking booking = new Booking(tai_khoan, ma_lich_chieu, selectedSeats, totalPrice);
        bookingService.addNewBooking(booking);
        Mail mail = new Mail();
        mail.setTo(emailClient);
        sendConfirmationEmail(mail, booking);

        request.setAttribute("totalPrice", totalPrice);
        request.getRequestDispatcher(PageLink.SUCCESS_PAGE).forward(request, response);
    }

    private void sendConfirmationEmail(Mail mail, Booking b) throws MessagingException {
        int ma_lich_chieu = b.getMa_lich_chieu();
        String ten_phim = showtimeService.getMoiveNameByShowtimeID(ma_lich_chieu);
        String tenRap = showtimeService.getCinemaNameByShowtimeID(ma_lich_chieu);
        String tenCumRap = showtimeService.getNameCinemaGroupByShowTimeID(ma_lich_chieu);
        String ngay_gio_chieu = showtimeService.getShowtimeInformationByID(ma_lich_chieu).getNgay_gio_chieu();
        String ghe_da_dat = b.getGhe_da_dat();
        long gia_ve = b.getGia_ve();

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<h2>Cảm ơn bạn đã đặt vé tại hệ thống của chúng tôi!</h2>");
        emailBody.append("<p>Chi tiết vé của bạn:</p>");
        emailBody.append("<table border='1' cellpadding='5' cellspacing='0'>");
        emailBody.append("<tr><th>Tên phim</th><th>Tên rạp</th><th>Phòng chiếu</th><th>Suất chiếu</th><th>Ghế đã chọn</th></tr>");

        emailBody.append("<tr>");
        emailBody.append("<td>").append(ten_phim).append("</td>");
        emailBody.append("<td>").append(tenCumRap).append("</td>");
        emailBody.append("<td>").append(tenRap).append("</td>");
        emailBody.append("<td>").append(ngay_gio_chieu).append("</td>");
        emailBody.append("<td>").append(ghe_da_dat).append("</td>");
        emailBody.append("</tr>");

        emailBody.append("<tr><td colspan='2'><b>Giá vé:</b></td><td><b>").append(gia_ve).append(" VND</b></td></tr>");
        emailBody.append("</table>");
        emailBody.append("<p>Chúc bạn có trải nghiệm tuyệt vời! Nếu có thắc mắc, vui lòng liên hệ chúng tôi.</p>");

        //initilize Mail object to send mail
        mail.setSubject("Xác nhận đặt vé từ hệ thống");
        mail.setBody(emailBody.toString());
        SendEmail.sendMail(mail);
    }

}
