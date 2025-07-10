/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import constant.PageLink;
import entity.TokenForgetPassword;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.TokenForgetPasswordService;
import service.UserService;

@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/reset_password"})
public class ResetPasswordServlet extends HttpServlet {

    private static final TokenForgetPasswordService tokenService = new TokenForgetPasswordService();
    private static final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
//token phai ton tai
        if (isValidToken(request, response, token)) {
            User user = userService.getUser(tokenService.getTokenForgetPassword(token).getUserId());
            HttpSession session = request.getSession();
            session.setAttribute("tokenSession", token);
            request.setAttribute("email", user.getEmail());
            request.getRequestDispatcher(PageLink.RESET_PASSWORD_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("tokenSession");

        if (!isValidToken(request, response, token)) {
            return;
        }

        String email = request.getParameter("email");
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("msg", "Confirm password does not match!");
            request.setAttribute("email", email);
            request.getRequestDispatcher(PageLink.RESET_PASSWORD_PAGE).forward(request, response);
            return;
        }

        User user = userService.getUserByEmail(email);
        TokenForgetPassword tokenSession = new TokenForgetPassword();
        tokenSession.setToken(token);
        tokenSession.setUsed(true);
        user.setPassword(newPassword);

        tokenService.updateStatus(tokenSession);
        userService.updateUser(user);

        response.sendRedirect(PageLink.LOGIN_PAGE);

    }

    //validate token 
    private boolean isValidToken(HttpServletRequest request, HttpServletResponse response, String token)
            throws ServletException, IOException {
        if (token != null) {
            TokenForgetPassword tokenForgetPassword = tokenService.getTokenForgetPassword(token);
            if (tokenForgetPassword == null) { //token khong ton tai trong db
                request.setAttribute("errMsg", "Token invalid!");
                request.getRequestDispatcher(PageLink.FORGOT_PASSWORD_PAGE).forward(request, response);
                return false;
            }

            if (tokenForgetPassword.isUsed()) { //token da duoc su dung
                request.setAttribute("errMsg", "This token is used!");
                request.getRequestDispatcher(PageLink.FORGOT_PASSWORD_PAGE).forward(request, response);
                return false;
            }

            if (tokenService.isExpired(tokenForgetPassword.getExpiryTime())) { //token het han
                request.setAttribute("errMsg", "Token is expired!");
                request.getRequestDispatcher(PageLink.FORGOT_PASSWORD_PAGE).forward(request, response);
                return false;
            }
            return true;
        }
        return false;
    }

}
