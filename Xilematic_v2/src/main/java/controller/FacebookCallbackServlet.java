/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import constant.IConstant;
import constant.PageLink;
import entity.FacebookAccount;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import model.User;
import service.UserService;
import utils.Helper;
import utils.SessionUtil;

@WebServlet(name = "FacebookCallbackServlet", urlPatterns = {"/fbcallback"})
public class FacebookCallbackServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final String DEFAULT_ROLE = "user";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processLoginWithFB(request, response);
    }

    //process login with facebook
    private void processLoginWithFB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null) {
            request.setAttribute("errorMsg", "Authorization_failed");
            request.getRequestDispatcher(PageLink.LOGIN_PAGE).forward(request, response);
            return;
        }

        // Bước 1: Lấy access token từ code
        String tokenUrl = "https://graph.facebook.com/v20.0/oauth/access_token?"
                + "client_id=" + IConstant.FACEBOOK_CLIENT_ID
                + "&redirect_uri=" + URLEncoder.encode(IConstant.FACEBOOK_REDIRECT_URI, StandardCharsets.UTF_8)
                + "&client_secret=" + IConstant.FACEBOOK_CLIENT_SECRET
                + "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8);

        String accessToken = getAccessToken(tokenUrl);
        if (accessToken == null) {
            request.setAttribute("errorMsg", "Token_failed");
            request.getRequestDispatcher(PageLink.LOGIN_PAGE).forward(request, response);
            return;
        }

        // Bước 2: Lấy thông tin người dùng
        FacebookAccount fbAcc = getUserInfo(accessToken);

        User user = userService.getUserByEmail(fbAcc.getEmail());

        //tai khoan moi
        if (user == null) {
            String password = Helper.generatePassword();
            String fullname = fbAcc.getName();
            String email = fbAcc.getEmail();
            user = new User("", fullname, email, null, password, DEFAULT_ROLE);
            int lastId = userService.createUser(user);
            user = userService.getUser(lastId);
            //generate username automatically
            user.setUsername("User" + String.valueOf(lastId));
            userService.updateUser(user);
        }
        //initialize new session
        SessionUtil.initializeSession(request.getSession(), user);
        if (!DEFAULT_ROLE.equals(user.getTypeOfUser())) {
            response.sendRedirect(PageLink.PAGING_SERVLET);
        } else {
            response.sendRedirect(PageLink.HOME_SERVLET);
        }
    }

    //method to get access token
    private String getAccessToken(String tokenUrl) throws IOException {
        URL url = new URL(tokenUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name())) {
            String jsonResponse = scanner.useDelimiter("\\A").next();
            // Sử dụng Gson để phân tích JSON
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            return jsonObject.has("access_token") ? jsonObject.get("access_token").getAsString() : null;
        }
    }

    private FacebookAccount getUserInfo(String accessToken) throws IOException {
        URL url = new URL(IConstant.FACEBOOK_LINK_GET_USER_INFO + accessToken);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name())) {
            String jsonResponse = scanner.useDelimiter("\\A").next();
            // Sử dụng Gson để phân tích JSON
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, FacebookAccount.class);
        }
    }

}
