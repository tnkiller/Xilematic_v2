package controller;

import constant.PageLink;
import constant.SessionAttribute;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import service.UserService;

@WebServlet(name = "UserServlet", urlPatterns = {"/users"})
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        action = action == null ? "" : action;
        switch (action) {
            case "showDetail":
                processShowDetail(request, response);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        action = action == null ? "" : action;
        switch (action) {
            case "add_user":
                processAddUser(request, response);
                break;
            case "update_user":
                processUpdateUser(request, response);
                break;
            case "delete":
                processDeleteUser(request, response);
                break;
            default:
                break;
        }
    }

    //process add function
    private void processAddUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getAttribute("user");
        userService.insertUser(user);
        response.sendRedirect(PageLink.PAGING_SERVLET + "type=users");
    }

    //process update function
    private void processUpdateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute(SessionAttribute.USER_INFOR);
        User userUpdate = (User) request.getAttribute("user");
        userService.updateUser(userUpdate);
        //update session if id_update == id_session
        if (userSession.getId() == userUpdate.getId()) {
            session.setAttribute(SessionAttribute.USER_INFOR, userUpdate);
            response.sendRedirect(PageLink.PROFILE_PAGE);
        } else {
            response.sendRedirect(PageLink.PAGING_SERVLET + "type=users");
        }
    }

    //process delete function
    private void processDeleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(id);
        response.sendRedirect("paging?type=users");
    }

    //show detail
    private void processShowDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        User us = userService.getUser(Integer.parseInt(id));
        request.setAttribute("user", us);
        request.getRequestDispatcher(PageLink.EDIT_USER_PAGE).forward(request, response);
    }

}
