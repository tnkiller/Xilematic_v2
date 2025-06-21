package com.vnpay.common;

import config.AppConfig;
import constant.SessionAttribute;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Booking;
import model.User;
import service.BookingService;
import service.ShowtimeService;

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
            if (signValue.equals(vnp_SecureHash)) {
                fetchAddNewBooking(request, response);
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
        
        User user = (User) request.getSession().getAttribute(SessionAttribute.USER_INFOR);
        int tai_khoan = user.getId();
        String emailClient = user.getEmail();
        long totalPrice = Long.parseLong(request.getParameter("vnp_Amount")) / 100;

        Booking booking = new Booking(tai_khoan, ma_lich_chieu, selectedSeats, totalPrice);
        bookingService.addNewBooking(booking);
        sendConfirmationEmail(emailClient, booking);

        request.setAttribute("totalPrice", totalPrice);
        request.getRequestDispatcher("/page/success.jsp")
                .forward(request, response);
    }

    private void sendConfirmationEmail(String toEmail, Booking b) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", AppConfig.SMTP_HOST);
        props.put("mail.smtp.port", AppConfig.SMTP_PORT);
        props.put("mail.debug", "true");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.connectiontimeout", "5000");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(AppConfig.EMAIL, AppConfig.EMAIL_PASSWORD);
            }
        });
        // lấy thông tin từ vé
        int ma_lich_chieu = b.getMa_lich_chieu();
        String ten_phim = showtimeService.getMoiveNameByShowtimeID(ma_lich_chieu);
        String tenRap = showtimeService.getCinemaNameByShowtimeID(ma_lich_chieu);
        String tenCumRap = showtimeService.getNameCinemaGroupByShowTimeID(ma_lich_chieu);
        String ngay_gio_chieu = showtimeService.getShowtimeInformationByID(ma_lich_chieu).getNgay_gio_chieu();
        String ghe_da_dat = b.getGhe_da_dat();
        long gia_ve = b.getGia_ve();
        try {
            System.out.println("Attempting to send email to: " + toEmail + " at " + new Date());
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(AppConfig.EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Xác nhận đặt vé từ hệ thống");

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

            emailBody.append("<tr><td colspan='2'><b>Giá vé:</b></td><td><b>")
                    .append(gia_ve).append(" VND</b></td></tr>");
            emailBody.append("</table>");
            emailBody.append("<p>Chúc bạn có trải nghiệm tuyệt vời! Nếu có thắc mắc, vui lòng liên hệ chúng tôi.</p>");

            message.setContent(emailBody.toString(), "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail + " at " + new Date());

        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
            throw e;
        }
    }
}
