/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import constant.IConstant;
import constant.PageLink;
import entity.GoogleAccount;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import service.UserService;
import utils.Helper;
import utils.SessionUtil;

@WebServlet(name = "GoogleCallbackServlet", urlPatterns = {"/ggcallback"})
public class GoogleCallbackServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final String DEFAULT_ROLE = "user";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processLoginWithGG(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    //process login with gg
    private void processLoginWithGG(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String code = request.getParameter("code");
        String accessToken;
//        xử lí trường hợp người dùng cancel
        try {
            accessToken = getToken(code);
        } catch (IOException e) {
            response.sendRedirect(PageLink.LOGIN_PAGE);
            return;
        }
        GoogleAccount ggAcc = getUserInfo(accessToken);

        User user = userService.getUserByEmail(ggAcc.getEmail());

        //tai khoan moi
        if (user == null) {
            String password = Helper.generatePassword();
            String firstname = ggAcc.getFirst_name() != null ? ggAcc.getFirst_name() : "";
            String givenname = ggAcc.getGiven_name() != null ? ggAcc.getGiven_name() : "";
            String familyname = ggAcc.getFamily_name() != null ? ggAcc.getFamily_name() : "";
            String fullname = familyname + " " + givenname + " " + firstname;
            String email = ggAcc.getEmail();
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

    //get token from response
    private String getToken(String code) throws ClientProtocolException, IOException {

        String response = Request.Post(IConstant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", IConstant.GOOGLE_CLIENT_ID)
                                .add("client_secret", IConstant.GOOGLE_CLIENT_SECRET)
                                .add("redirect_uri", IConstant.GOOGLE_REDIRECT_URI)
                                .add("code", code)
                                .add("grant_type", IConstant.GOOGLE_GRANT_TYPE)
                                .build()
                )
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);

        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");

        return accessToken;

    }

    //get UserInfor when token is valid
    private GoogleAccount getUserInfo(final String accessToken) throws ClientProtocolException, IOException {

        String link = IConstant.GOOGLE_LINK_GET_USER_INFO + accessToken;

        String response = Request.Get(link).execute().returnContent().asString();

        System.out.println(response);

        GoogleAccount googlePojo = new Gson().fromJson(response, GoogleAccount.class);

        return googlePojo;

    }
}
