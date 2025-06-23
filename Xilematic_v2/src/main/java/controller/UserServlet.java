package controller;

import constant.PageLink;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        response.sendRedirect("paging?type=users");
    }

    //process update function
    private void processUpdateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getAttribute("user");

        //validate
        userService.updateUser(user);
        response.sendRedirect("paging?type=users");
    }

    //process delete function
    private void processDeleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        userService.deleteUser(((User) request.getAttribute("user")).getId());
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
