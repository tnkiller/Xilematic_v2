/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import constant.PageLink;
import constant.SessionAttribute;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;
import model.User;
import service.UserService;

@WebServlet(name = "AuthenticateServlet", urlPatterns = {"/authenticate"})
public class AuthenticationServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action != null) ? action : "";
        switch (action) {
            case "login":
                processLogin(request, response);
                break;
            case "register":
                processRegister(request, response);
                break;
            case "forgotPassword":
                break;
            default:
        }
    }

    //process login
    private void processLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        var user = userService.login(username, password);

        if (user == null) {
            request.setAttribute("errorMsg", "Wrong username or password!");
            request.getRequestDispatcher(PageLink.LOGIN_PAGE).forward(request, response);
        } else {
            if (rememberMe != null) {
                processRememberMe(true, username, password, response);
            } else {
                processRememberMe(false, username, password, response);
            }
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.USER_INFOR, user);
            String[] namePart = user.getFullname().split(" ");
            String name = namePart[namePart.length - 1];
            session.setAttribute("alias", name);
            if (user.getTypeOfUser().equals("admin") || user.getTypeOfUser().equals("dev")) {
                request.setAttribute("type", "stats");
                request.setAttribute("status", true);
                request.getRequestDispatcher(PageLink.ADMIN_PAGE).forward(request, response);
            } else {
                request.getRequestDispatcher(PageLink.HOME_PAGE).forward(request, response);
            }
        }
    }

    //process register
    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] requestAttributeErr = {"errUsername", "errFullname", "errEmail", "errPhoneNumber", "errPassword", "errConfirmPassword"};
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phoneNum = request.getParameter("phoneNum");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String msg = "";
        String role = "user"; //default
        boolean flag = true;

//special code for 'admin' role
        if (username.endsWith("..@admin")) {
            role = "admin";
            username = username.substring(0, username.indexOf("."));
        }

        request.setAttribute("username", username);
        request.setAttribute("fullname", fullname);
        request.setAttribute("email", email);
        request.setAttribute("phoneNum", phoneNum);
        request.setAttribute("password", password);

        // khong cho khoang cach
        if (username.isBlank()
                || password.isBlank()
                || fullname.isBlank()
                || email.isBlank()
                || phoneNum.isBlank()
                || confirmPassword.isBlank()) {
            for (String i : requestAttributeErr) {
                request.setAttribute(i, "PLease fill form correctly!");
                request.getRequestDispatcher(PageLink.REGISTER_PAGE).forward(request, response);
            }
            return;
        }

//         validate không cho trùng trong database
        if (userService.isUsernameExist(username)) {
            request.setAttribute(requestAttributeErr[0], "This ID existed database!");
            request.setAttribute("username", null);
            flag = false;
        }

        if (!fullname.matches("^[A-Za-z]+(?: [A-Za-z]+)+$")) {
            request.setAttribute(requestAttributeErr[1], "Invalid fullname!");
            request.setAttribute("fullname", null);
            flag = false;
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            request.setAttribute(requestAttributeErr[2], "Invalid email!");
            request.setAttribute("email", null);
            flag = false;

        }

        if (!phoneNum.matches("^0\\d{9}$")) {
            request.setAttribute(requestAttributeErr[3], "Invalid phone number!");
            request.setAttribute("phoneNum", null);
            flag = false;
        }

        if (!password.matches("^.{3,}$")) {
            request.setAttribute("password", null);
            request.setAttribute(requestAttributeErr[4], "Invalid phone number!");
            flag = false;
        }

        if (!confirmPassword.equals(password)) {
            request.setAttribute(requestAttributeErr[5], "Do not match password!");
            flag = false;
        }

        if (!flag) {
            request.getRequestDispatcher(PageLink.REGISTER_PAGE).forward(request, response);
            return;
        }

        boolean status = userService.register(new User(username, fullname, email, phoneNum, password, role));
        msg = status ? "Back to login" : "Error happened!";
        request.setAttribute("msg", msg);
        request.getRequestDispatcher(PageLink.REGISTER_PAGE).forward(request, response);
    }

    //process remember me
    private void processRememberMe(boolean isChecked, String username, String password, HttpServletResponse response) {
        Cookie cookieUsername;
        Cookie cookiePassword;
        if (isChecked) {
            String encodedUsernameValue = Base64.getEncoder().encodeToString(username.getBytes());
            String encodedPasswordValue = Base64.getEncoder().encodeToString(password.getBytes());
            cookieUsername = new Cookie("USERNAME", encodedUsernameValue);
            cookiePassword = new Cookie("PASSWORD", encodedPasswordValue);
            cookieUsername.setHttpOnly(true);
            cookiePassword.setHttpOnly(true);
            cookieUsername.setSecure(true);
            cookiePassword.setSecure(true);
            cookieUsername.setMaxAge(24 * 60 * 60);
            cookiePassword.setMaxAge(24 * 60 * 60);
        } else {
            cookieUsername = new Cookie("USERNAME", "");
            cookiePassword = new Cookie("PASSWORD", "");
            cookieUsername.setMaxAge(0);
            cookiePassword.setMaxAge(0);
            cookieUsername.setPath("/");
            cookiePassword.setPath("/");
        }
        response.addCookie(cookieUsername);
        response.addCookie(cookiePassword);
    }

    //process log out
    private void processLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.invalidate();

        response.sendRedirect("login.jsp");
    }

    //process forgot password
//    private void processForgotPassword(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String flag = request.getParameter("action");
//        switch (flag) {
//            case "none" ->
//                getNewPassword(request, response);
//            case "existed" -> {
//                updateNewPassword(request, response);
//            }
//        }
//    }
    //get new password from user
//    private void getNewPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String username = request.getParameter("input");
//        int isExist = 0;
//
//        User o = new UserDAO().getUserByUsername(username);
//
//        if (o != null) {
//            isExist = 1;
//        } else {
//            isExist = -1;
//        }
//        request.setAttribute("username", username);
//        request.setAttribute("isExist", isExist);
//        request.getRequestDispatcher(PageLink.FORGOT_PASSWORD_PAGE).forward(request, response);
//    }
    //update new password into DB
//    private void updateNewPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String username = request.getParameter("input");
//        String newPassword = request.getParameter("password");
//
//        User newU = new User();
//        newU.setPassword(newPassword);
//        newU.setUsername(username);
//
//        if (new UserDAO().updateUser(newU) != -1) {
//            request.setAttribute("msg", "Changed new password successfully!");
//            request.getRequestDispatcher(PageLink.FORGOT_PASSWORD_PAGE).forward(request, response);
//        }
//
//    }
}
