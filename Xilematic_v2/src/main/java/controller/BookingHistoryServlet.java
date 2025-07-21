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
            case "cancel":
                // Logic to cancel a booking
                request.getRequestDispatcher("/cancelBooking.jsp").forward(request, response);
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

}
