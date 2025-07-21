
package controller;

import constant.PageLink;
import constant.SessionAttribute;
import entity.GoogleAccount;
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
import utils.GenerateInfor;
import utils.Validator;

@WebServlet(name = "AuthenticateServlet", urlPatterns = {"/authenticate"})
public class AuthenticationServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n--- [AuthenticationServlet] doGet called ---");
        processLoginWithGG(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n--- [AuthenticationServlet] doPost called ---");
        String action = request.getParameter("action");
        action = (action != null) ? action : "";
        System.out.println("[AuthenticationServlet] Action parameter: " + action);

        switch (action) {
            case "login":
                processLogin(request, response);
                break;
            case "register":
                processRegister(request, response);
                break;
            case "forgotPassword":
                System.out.println("[AuthenticationServlet] Forgot password action (not implemented).");
                break;
            case "logout": // Thêm trường hợp logout vào switch
                processLogout(request, response);
                break;
            default:
                System.out.println("[AuthenticationServlet] Default action (no action specified or unrecognized).");
        }
    }

    //process login
    private void processLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[AuthenticationServlet] Entering processLogin method.");
        User u = (User) request.getAttribute("user");
        if (u != null) {
            System.out.println("[AuthenticationServlet] User object from request attribute: " + u.getUsername());
        } else {
            System.out.println("[AuthenticationServlet] User object from request attribute is NULL. This might be an issue if login data is expected here.");
            // Consider handling this case, perhaps redirect to login with an error
            request.setAttribute("errorMsg", "Login data missing!");
            request.getRequestDispatcher(PageLink.LOGIN_PAGE).forward(request, response);
            return; // Exit to prevent NullPointerException
        }

        String username = u.getUsername();
        String password = u.getPassword();
        String rememberMe = request.getParameter("rememberMe");

        System.out.println("[AuthenticationServlet] Login attempt for Username: " + username);
        System.out.println("[AuthenticationServlet] Password (hashed/raw depends on Validator): " + password); // Cẩn thận với password thật
        System.out.println("[AuthenticationServlet] Remember Me: " + (rememberMe != null ? "true" : "false"));

        var user = userService.login(username, password);

        if (user == null) {
            System.out.println("[AuthenticationServlet] Login failed: Wrong username or password!");
            request.setAttribute("errorMsg", "Wrong username or password!");
            request.getRequestDispatcher(PageLink.LOGIN_PAGE).forward(request, response);
        } else {
            System.out.println("[AuthenticationServlet] Login successful for user: " + user.getUsername());
            if (rememberMe != null) {
                processRememberMe(true, username, password, response);
                System.out.println("[AuthenticationServlet] Remember Me processed (set cookies).");
            } else {
                processRememberMe(false, username, password, response);
                System.out.println("[AuthenticationServlet] Remember Me processed (remove cookies).");
            }
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.USER_INFOR, user);
            System.out.println("[AuthenticationServlet] Session attribute 'userInfor' set for user: " + user.getUsername());

            String[] namePart = user.getFullname().split(" ");
            String name = namePart[namePart.length - 1];
            session.setAttribute("alias", name);
            System.out.println("[AuthenticationServlet] Session attribute 'alias' set to: " + name);

            if (user.getTypeOfUser().equals("admin")) {
                request.setAttribute("type", "stats");
                request.setAttribute("status", true);
                System.out.println("[AuthenticationServlet] User is Admin. Forwarding to ADMIN_PAGE.");
                request.getRequestDispatcher(PageLink.ADMIN_PAGE).forward(request, response);
            } else {
                System.out.println("[AuthenticationServlet] User is normal user. Forwarding to homeservlet.");
                request.getRequestDispatcher("homeservlet").forward(request, response);
            }
        }
    }

    //process login with gg
    private void processLoginWithGG(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[AuthenticationServlet] Entering processLoginWithGG method.");
        String code = request.getParameter("code");
        System.out.println("[AuthenticationServlet] Google OAuth code: " + code);

        String accessToken = null;
        GoogleAccount ggAcc = null;
        try {
            // Đảm bảo GoogleLogin.getToken và GoogleLogin.getUserInfo không ném ra exception không mong muốn
            accessToken = GoogleLogin.getToken(code);
            System.out.println("[AuthenticationServlet] Google Access Token received.");
            ggAcc = GoogleLogin.getUserInfo(accessToken);
            System.out.println("[AuthenticationServlet] Google Account Info fetched for email: " + ggAcc.getEmail());
        } catch (Exception e) {
            System.err.println("[AuthenticationServlet] Error during Google login: " + e.getMessage());
            response.sendRedirect(PageLink.LOGIN_PAGE + "?error=googleLoginFailed"); // Chuyển hướng về trang login với lỗi
            return;
        }


        var user = userService.getUserByEmail(ggAcc.getEmail());
        System.out.println("[AuthenticationServlet] User found by email in DB: " + (user != null ? user.getUsername() : "NULL (new user)"));

        if (user == null) {
            System.out.println("[AuthenticationServlet] User not found in DB. Registering new user from Google Account.");
            String username = GenerateInfor.generateUsername();
            String password = GenerateInfor.generatePassword();
            String firstname = ggAcc.getFirst_name() != null ? ggAcc.getFirst_name() : "";
            String givenname = ggAcc.getGiven_name() != null ? ggAcc.getGiven_name() : "";
            String familyname = ggAcc.getFamily_name() != null ? ggAcc.getFamily_name() : "";
            String fullname = familyname + " " + givenname + " " + firstname;
            String email = ggAcc.getEmail();

            System.out.println("[AuthenticationServlet] Generated username: " + username);
            System.out.println("[AuthenticationServlet] Generated password: " + password);
            System.out.println("[AuthenticationServlet] Fullname from GG: " + fullname);
            System.out.println("[AuthenticationServlet] Email from GG: " + email);

            while (userService.isUsernameExist(username)) {
                username = GenerateInfor.generateUsername();
                System.out.println("[AuthenticationServlet] Username already exists, regenerating: " + username);
            }
            user = new User(username, fullname, email, "", password, "user");
            boolean registered = userService.register(user);
            System.out.println("[AuthenticationServlet] New Google user registered status: " + registered);
            if (!registered) {
                System.err.println("[AuthenticationServlet] Failed to register new Google user!");
                response.sendRedirect(PageLink.LOGIN_PAGE + "?error=registrationFailed");
                return;
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionAttribute.USER_INFOR, user);
        System.out.println("[AuthenticationServlet] Session attribute 'userInfor' set for Google user: " + user.getUsername());

        if ("admin".equals(user.getTypeOfUser())) {
            System.out.println("[AuthenticationServlet] Google user is Admin. Redirecting to ADMIN_PAGE.");
            response.sendRedirect(PageLink.ADMIN_PAGE);
        } else {
            System.out.println("[AuthenticationServlet] Google user is normal user. Redirecting to HOME_PAGE.");
            response.sendRedirect(PageLink.HOME_PAGE);
        }
    }

    //process register
    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("[AuthenticationServlet] Entering processRegister method.");
        String[] requestAttributeErr = {"errUsername", "errFullname", "errEmail", "errPhoneNumber", "errPassword", "errConfirmPassword"};
        User user = (User) request.getAttribute("user");
        if (user != null) {
            System.out.println("[AuthenticationServlet] User object from request attribute (for register): " + user.getUsername());
        } else {
            System.err.println("[AuthenticationServlet] User object from request attribute is NULL for registration. Check filter/interceptor.");
            request.setAttribute("errorMsg", "Registration data missing!");
            request.getRequestDispatcher(PageLink.REGISTER_PAGE).forward(request, response);
            return;
        }

        user.setTypeOfUser("user");//default
        String username = user.getUsername();
        String fullname = user.getFullname();
        String email = user.getEmail();
        String phoneNum = user.getPhoneNumber();
        String password = user.getPassword();
        String confirmPassword = request.getParameter("confirmPassword");

        System.out.println("[AuthenticationServlet] Registering user details:");
        System.out.println("  - Username: " + username);
        System.out.println("  - Fullname: " + fullname);
        System.out.println("  - Email: " + email);
        System.out.println("  - Phone: " + phoneNum);
        System.out.println("  - Password: " + password);
        System.out.println("  - Confirm Password: " + confirmPassword);

        boolean flag = true;

        if (Validator.isEmpty(username) != null
                || Validator.isEmpty(fullname) != null
                || Validator.isEmpty(email) != null
                || Validator.isEmpty(phoneNum) != null
                || Validator.isEmpty(password) != null) {
            System.out.println("[AuthenticationServlet] Required fields are empty. Forwarding to REGISTER_PAGE with error.");
            for (String i : requestAttributeErr) {
                request.setAttribute(i, "PLease fill form correctly!");
            }
            request.getRequestDispatcher(PageLink.REGISTER_PAGE).forward(request, response);
            return;
        }

        // Validate individual fields and set error messages
        String validationError;

        validationError = Validator.isValidUsername(username);
        if (validationError != null) {
            request.setAttribute(requestAttributeErr[0], validationError);
            flag = false;
            System.out.println("[AuthenticationServlet] Username validation error: " + validationError);
        } else {
            request.setAttribute("username", username);
        }

        validationError = Validator.isValidFullname(fullname);
        if (validationError != null) {
            request.setAttribute(requestAttributeErr[1], validationError);
            flag = false;
            System.out.println("[AuthenticationServlet] Fullname validation error: " + validationError);
        } else {
            request.setAttribute("fullname", fullname);
        }

        validationError = Validator.isValidEmail(email);
        if (validationError != null) {
            request.setAttribute(requestAttributeErr[2], validationError);
            flag = false;
            System.out.println("[AuthenticationServlet] Email validation error: " + validationError);
        } else {
            request.setAttribute("email", email);
        }

        validationError = Validator.isValidPhoneNumber(phoneNum);
        if (validationError != null) {
            request.setAttribute(requestAttributeErr[3], validationError);
            flag = false;
            System.out.println("[AuthenticationServlet] Phone number validation error: " + validationError);
        } else {
            request.setAttribute("phoneNum", phoneNum);
        }

        validationError = Validator.isValidPassword(password);
        if (validationError != null) {
            request.setAttribute(requestAttributeErr[4], validationError);
            flag = false;
            System.out.println("[AuthenticationServlet] Password validation error: " + validationError);
        } else {
            request.setAttribute("password", password);
        }

        validationError = Validator.isValidConfirmPassword(password, confirmPassword);
        if (validationError != null) {
            request.setAttribute(requestAttributeErr[5], validationError);
            flag = false;
            System.out.println("[AuthenticationServlet] Confirm password validation error: " + validationError);
        }

        System.out.println("[AuthenticationServlet] Validation flag status before final check: " + flag);

        if (!flag) {
            System.out.println("[AuthenticationServlet] Validation failed. Forwarding to REGISTER_PAGE.");
            request.getRequestDispatcher(PageLink.REGISTER_PAGE).forward(request, response);
            return;
        }

        System.out.println("[AuthenticationServlet] All validations passed. Attempting to register user: " + username);
        boolean status = userService.register(user);
        System.out.println("[AuthenticationServlet] User registration status: " + status);

        if (status) {
             System.out.println("[AuthenticationServlet] User registered successfully. Redirecting to LOGIN_PAGE.");
             response.sendRedirect(PageLink.LOGIN_PAGE);
        } else {
             System.err.println("[AuthenticationServlet] Failed to register user (userService.register returned false).");
             // Có thể thêm thông báo lỗi cụ thể hơn cho người dùng
             request.setAttribute("errorMsg", "Registration failed. Please try again.");
             request.getRequestDispatcher(PageLink.REGISTER_PAGE).forward(request, response);
        }
    }

    //process remember me
    private void processRememberMe(boolean isChecked, String username, String password, HttpServletResponse response) {
        System.out.println("[AuthenticationServlet] Entering processRememberMe method. isChecked: " + isChecked);
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
            cookiePassword.setSecure(true); // Chỉ nên dùng true trên HTTPS
            cookieUsername.setMaxAge(24 * 60 * 60);
            cookiePassword.setMaxAge(24 * 60 * 60);
            System.out.println("[AuthenticationServlet] Setting 'Remember Me' cookies (USERNAME, PASSWORD) with MaxAge: " + (24 * 60 * 60));
        } else {
            cookieUsername = new Cookie("USERNAME", "");
            cookiePassword = new Cookie("PASSWORD", "");
            cookieUsername.setMaxAge(0);
            cookiePassword.setMaxAge(0);
            cookieUsername.setPath("/");
            cookiePassword.setPath("/");
            System.out.println("[AuthenticationServlet] Removing 'Remember Me' cookies (USERNAME, PASSWORD) by setting MaxAge to 0.");
        }
        response.addCookie(cookieUsername);
        response.addCookie(cookiePassword);
    }

    //process log out
    private void processLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[AuthenticationServlet] Entering processLogout method.");
        HttpSession session = request.getSession(false); // Get existing session, don't create new
        if (session != null) {
            session.invalidate();
            System.out.println("[AuthenticationServlet] Session invalidated.");
        } else {
            System.out.println("[AuthenticationServlet] No active session to invalidate.");
        }
        System.out.println("[AuthenticationServlet] Redirecting to login.jsp after logout.");
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
