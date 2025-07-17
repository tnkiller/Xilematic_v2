package controller;

import constant.PageLink;
import constant.SessionAttribute;
import entity.GoogleAccount;
import entity.RequestAttribute;
import entity.TokenForgetPassword;
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
import service.TokenForgetPasswordService;
import service.UserService;
import utils.Helper;
import utils.Validator;

@WebServlet(name = "AuthenticateServlet", urlPatterns = {"/authenticate"})
public class AuthenticationServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final String DEFAULT_ROLE = "user";
    private final String ACTION_PARAM = "action";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter(ACTION_PARAM);
        action = action != null ? action : "";
        switch (action) {
            case "logout" ->
                processLogout(request, response);
            default ->
                processLoginWithGG(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter(ACTION_PARAM);
        action = (action != null) ? action : "";
        switch (action) {
            case "login" ->
                processLogin(request, response);
            case "register" ->
                processRegister(request, response);
            case "forgotPassword" ->
                processForgotPassword(request, response);
            default -> {
            }
        }
    }

    //process login
    private void processLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getAttribute("user");
        String username = user.getUsername();
        String password = user.getPassword();
        String rememberMe = request.getParameter("rememberMe");
        User u = userService.login(username, password);

        if (u == null) {
            request.setAttribute("errorMsg", "Wrong username or password!");
            request.getRequestDispatcher(PageLink.LOGIN_PAGE).forward(request, response);
        } else {
            if (rememberMe != null) {
                processRememberMe(true, username, password, response);
            } else {
                processRememberMe(false, username, password, response);
            }
            u.setPassword(password);
            //initialize new session
            initializeSession(request, u);
            if (!DEFAULT_ROLE.equals(u.getTypeOfUser())) {
                request.getRequestDispatcher(PageLink.ADMIN_PAGE).forward(request, response);
            } else {
                request.getRequestDispatcher(PageLink.HOME_SERVLET).forward(request, response);
            }
        }
    }

    //process login with gg
    private void processLoginWithGG(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String code = request.getParameter("code");
        String accessToken;
//        xử lí trường hợp người dùng cancel
        try {
            accessToken = GoogleLogin.getToken(code);
        } catch (IOException e) {
            response.sendRedirect(PageLink.LOGIN_PAGE);
            return;
        }
        GoogleAccount ggAcc = GoogleLogin.getUserInfo(accessToken);

        User user = userService.getUserByEmail(ggAcc.getEmail());

        //tai khoan moi
        if (user == null) {
            String password = Helper.generatePassword();
            String firstname = ggAcc.getFirst_name() != null ? ggAcc.getFirst_name() : "";
            String givenname = ggAcc.getGiven_name() != null ? ggAcc.getGiven_name() : "";
            String familyname = ggAcc.getFamily_name() != null ? ggAcc.getFamily_name() : "";
            String fullname = familyname + " " + givenname + " " + firstname;
            String email = ggAcc.getEmail();
            user = new User("", fullname, email, "", password, DEFAULT_ROLE);
            int lastId = userService.createUser(user);
            user = userService.getUser(lastId);
            //generate username automatically
            user.setUsername("User" + String.valueOf(lastId));
            userService.updateUser(user);
        }
        //initialize new session
        initializeSession(request, user);
        if (!DEFAULT_ROLE.equals(user.getTypeOfUser())) {
            response.sendRedirect(PageLink.PAGING_SERVLET);
        } else {
            response.sendRedirect(PageLink.HOME_SERVLET);
        }
    }

    //process register
    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getAttribute("user");
        user.setTypeOfUser(DEFAULT_ROLE);//default
        String confirmPassword = request.getParameter("confirmPassword");

        var res = Validator.validateUserInformation(user, confirmPassword);
        //res != null -> co du lieu khong hop le
        if (res != null) {
            for (RequestAttribute i : res) {
                if (i.getContent() != null) {
                } else {

                }
                request.setAttribute(i.getName(), i.getContent());
            }
            request.getRequestDispatcher(PageLink.REGISTER_PAGE).forward(request, response);
            return;
        }

        userService.register(user);
        response.sendRedirect(PageLink.LOGIN_PAGE);
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
        }
        response.addCookie(cookieUsername);
        response.addCookie(cookiePassword);
    }

    //process log out
    private void processLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(PageLink.LOGIN_PAGE);
    }

    //initialize session 
    private void initializeSession(HttpServletRequest request, User userInfor)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute(SessionAttribute.USER_INFOR, userInfor);
        session.setAttribute(SessionAttribute.COLOR_CODE, Helper.generateColorCode());
    }

    //process forgot password
    private void processForgotPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String recoverEmail = request.getParameter("recoverEmail");

        //Step 1: check xem tai khoan voi email nay co ton tai trong db khong
        User user = userService.getUserByEmail(recoverEmail);

        //KHONG CO -> gui error msg
        if (user == null) {
            request.setAttribute("errMsg", "This email does not exist!");
            request.getRequestDispatcher(PageLink.FORGOT_PASSWORD_PAGE).forward(request, response);
            return;
        }
        //CO -> gui link reset password
        TokenForgetPasswordService service = new TokenForgetPasswordService();
        String strToken = service.generateToken();
        String linkReset = "http://localhost:9999/xilematic/" + PageLink.RESET_PASSWORD_SERVLET + "token=" + strToken;
        TokenForgetPassword token = new TokenForgetPassword(user.getId(), strToken, false, service.generateExpiryTime());

        if (service.insertTokenForget(token)) {
            if (service.sendResetPasswordMail(recoverEmail, linkReset, user.getFullname())) {
                request.setAttribute("succMsg", "Please check your email to get link for reset password!");
            } else {
                request.setAttribute("errMsg", "Wrong something! Please try again...");
            }
        } else {
            request.setAttribute("errMsg", "Can't generate new token!");
        }
        request.getRequestDispatcher(PageLink.FORGOT_PASSWORD_PAGE).forward(request, response);
    }

}
