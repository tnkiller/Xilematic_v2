package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.*;

@WebFilter(urlPatterns = {"/*"}) // Áp dụng cho tất cả servlet
public class AuthFilter implements Filter {

    // MAP quyền truy cập
    private static final Map<String, Set<String>> roleAccessMap = new HashMap<>();
    // Các tài nguyên công khai
    private static final List<String> publicUrls = Arrays.asList(
            "/login.jsp", "/register.jsp", "/intermediate.jsp", "/access_denied.jsp", "/favorites",
            "/style/", "/script/", "/asset/", "/favicon.ico", "/authenticate", "/components/", "/home/", "/"
    );

    @Override
    public void init(FilterConfig filterConfig) {
//        // Khởi tạo các quyền truy cập cho từng role
//
//        //Các trang admin có thể vào
//        Set<String> adminPages = new HashSet<>(Arrays.asList(
//                "/admin/", "/home/", "/user/", "/paging", "/users", "movies"
//        ));
//
//        //Các trang user có thể vào
//        Set<String> userPages = new HashSet<>(Arrays.asList(
//                "/user/", "/home/"
//        ));
//        roleAccessMap.put("admin", adminPages);
//        roleAccessMap.put("user", userPages);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        chain.doFilter(request, response);

//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        String contextPath = req.getContextPath();         // ex: /myapp
//        String uri = req.getRequestURI();                  // ex: /myapp/secure/admin
//        String path = uri.substring(contextPath.length()); // ex: /secure/admin
//
    

////        path = /style/home_style.css
//        if (publicUrls.stream().anyMatch(path::startsWith)) {
//            chain.doFilter(request, response); // Cho phép truy cập URL công khai
//            return;
//        }
//
//        // Kiểm tra phiên đăng nhập
//        HttpSession session = req.getSession(false);
//
//        if (session != null && session.getAttribute(SessionAttribute.USER_INFOR) != null) {
//            User u = (User) session.getAttribute(SessionAttribute.USER_INFOR);
//            Set<String> allowedPrefixes = roleAccessMap.get(u.getTypeOfUser());
//
//            System.out.println(path);
//            System.out.println(u.getTypeOfUser());
//
//            boolean allowed = false;
//
//            if (allowedPrefixes != null) {
//                for (String prefix : allowedPrefixes) {
//                    if (path.startsWith(prefix)) {
//                        allowed = true;
//                        break;
//                    }
//                }
//            }
//            if (allowed) {
//                chain.doFilter(request, response);
//            } else {
//                res.sendRedirect(contextPath + "/access_denied.jsp");
//            }
//
//        } else {
//            res.sendRedirect(contextPath + "/" + PageLink.LOGIN_PAGE);
//        }
    }

}
