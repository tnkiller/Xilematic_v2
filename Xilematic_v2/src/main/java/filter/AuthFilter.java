package filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

/**
 *
 * @author ASUS
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    // private static final boolean debug = true; // Biến này không cần thiết nếu chỉ dùng System.out

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[AuthFilter] Filter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        HttpSession session = httpRequest.getSession(false); // Không tạo session mới nếu chưa có
//
//        String path = httpRequest.getRequestURI();
//        String contextPath = httpRequest.getContextPath();
//
//        System.out.println("\n--- [AuthFilter] New Request ---");
//        System.out.println("[AuthFilter] Path requested: " + path);
//        System.out.println("[AuthFilter] Context Path: " + contextPath);
//
//        // Các trang công khai mà người dùng chưa đăng nhập có thể truy cập
//        // Sử dụng path.startsWith để xử lý các đường dẫn con nếu có (ví dụ: /homeservlet/abc)
//        boolean isHomePage = path.equals(contextPath + "/") || path.startsWith(contextPath + "/homeservlet");
//        boolean isDetailPage = path.startsWith(contextPath + "/DetailServlet");
//        boolean isintermediatePage = path.startsWith(contextPath + "/intermediate.jsp");
//        boolean isSelectCalendarPage = path.startsWith(contextPath + "/SelectCalendar");
//        boolean isLoginPage = path.startsWith(contextPath + "/login.jsp") ;
//        boolean isRegisterPage = path.startsWith(contextPath + "/register.jsp") ;
//        boolean isAuthenticateAction = path.startsWith(contextPath + "/authenticate"); // Servlet xử lý post login form
//
//        // Tài nguyên tĩnh (CSS, JS, Images, v.v.)
//        boolean isResource = path.contains(".css") || path.contains(".js") || path.contains("image");
//
//        System.out.println("[AuthFilter] Check flags:");
//        System.out.println("  - isHomePage: " + isHomePage);
//        System.out.println("  - isDetailPage: " + isDetailPage);
//        System.out.println("  - isSelectCalendarPage: " + isSelectCalendarPage);
//        System.out.println("  - isLoginPage: " + isLoginPage);
//        System.out.println("  - isRegisterPage: " + isRegisterPage);
//        System.out.println("  - isAuthenticateAction: " + isAuthenticateAction);
//        System.out.println("  - isResource: " + isResource);
//        System.out.println("  - isintermediatePage: " + isintermediatePage);
//
//        // Kiểm tra xem người dùng đã đăng nhập chưa
//        boolean loggedIn = (session != null && session.getAttribute("userInfor") != null);
//        System.out.println("[AuthFilter] User loggedIn (session check): " + loggedIn);
//        if (session != null) {
//            System.out.println("[AuthFilter] Session ID: " + session.getId());
//            System.out.println("[AuthFilter] userInfor attribute in session: " + session.getAttribute("userInfor"));
//        } else {
//            System.out.println("[AuthFilter] Session is null.");
//        }
//
//
//        if (loggedIn || isHomePage || isDetailPage || isSelectCalendarPage || isLoginPage || isRegisterPage || isAuthenticateAction || isResource||isintermediatePage) {
//            System.out.println("[AuthFilter] Access granted for path: " + path + ". Chaining filter.");
//            chain.doFilter(request, response); // Cho phép yêu cầu đi tiếp
//        } else {
//            System.out.println("[AuthFilter] Unauthorized access to path: " + path + ". Redirecting to login page.");
//            httpResponse.sendRedirect(contextPath + "/login.jsp"); // Chuyển hướng đến URL của servlet login
//            // Bạn có thể cân nhắc chuyển hướng đến login.jsp nếu bạn không có servlet /login
//            // httpResponse.sendRedirect(contextPath + "/login.jsp");
//        }
chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("[AuthFilter] Filter destroyed.");
    }
}