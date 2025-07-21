/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;

import constant.PageLink;
import constant.SessionAttribute;
import jakarta.mail.Session;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Booking;
import model.User;
import service.BookingService;
import service.IBookingService;

@WebServlet(name = "BookingHistoryServlet", urlPatterns = { "/booking_history" })
public class BookingHistoryServlet extends HttpServlet {

    private static IBookingService bookingService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        bookingService = new BookingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "" : action;
        switch (action) {
            case "viewDetail":
                viewBookingDetail(request, response);
                break;
            default:
                viewBookingHistory(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // view list of bookings
    private void viewBookingHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        User currentUser = (User) request.getSession().getAttribute(SessionAttribute.USER_INFOR);
        int userId = currentUser.getId();
        var bookingList = bookingService.getBookingsByUserId(userId);
        out.println(bookingList);
        request.setAttribute("bookingList", bookingList);
        request.getRequestDispatcher(PageLink.BOOKING_HISTORY_PAGE).forward(request, response);
    }

    // view booking detail
    private void viewBookingDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String maDatVe = request.getParameter("ma_dat_ve");
        int id = Integer.parseInt(maDatVe);
        Booking booking = bookingService.getBookingById(id);
        request.setAttribute("booking", booking);
        request.getRequestDispatcher(PageLink.BOOKING_HISTORY_DETAIL_PAGE).forward(request, response);
    }

}
