<%-- Document : login Created on : May 18, 2025, 1:27:57 AM Author : ADMIN --%>

<%@page import="java.util.Base64"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="constant.PageLink"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="icon" type="image/gif" href="asset/image/AnimatedLogo.gif" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/login_style.css">
    </head>

    <body>

        <!--giai ma cookie-->
        <%
            Cookie[] cookies = request.getCookies();
            String username = null;
            String password = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("USERNAME".equals(cookie.getName())) {
                        byte[] decodedBytes = Base64.getDecoder().decode(cookie.getValue());
                        username = new String(decodedBytes);
                    } else if ("PASSWORD".equals(cookie.getName())) {
                        byte[] decodedBytes = Base64.getDecoder().decode(cookie.getValue());
                        password = new String(decodedBytes);
                    }
                }
                request.setAttribute("username", username);
                request.setAttribute("password", password);
            }
        %>




        <div class="login-box">
            <a href="${pageContext.request.contextPath}/" class="home-button">
                <span>← Home</span>
            </a>
            <form action='<%=request.getContextPath() + "/" + PageLink.INTERMEDIATE_PAGE%>' method="POST">
                <h2>Login</h2>
                <div class="input-box">
                    <span class="icon"><ion-icon name="person"></ion-icon></ion-icon></span>
                    <input type="text" required name="username" value="${empty username ? '': username}">
                    <label>Username/Email</p></label>
                </div>

                <div class="input-box">
                    <span class="icon" id="togglePassword" style="cursor: pointer;"><ion-icon
                            name="lock-closed"></ion-icon></span>
                    <input type="password" required name="password" id="password" value="${empty password ? '' : password}">
                    <label>Password</label>
                </div>

                <div class="error-message">${requestScope.errorMsg}</div>

                <div class="remember-forgot">
                    <label>
                        <c:choose>
                            <c:when test="${username != null}">
                                <input type="checkbox" name="rememberMe" checked="">
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" name="rememberMe">
                            </c:otherwise>
                        </c:choose>
                        Remember me
                    </label>
                    <a href="${pageContext.request.contextPath}/<%=PageLink.FORGOT_PASSWORD_PAGE%>">Forgot Password?</a>
                </div>

                <button type="submit" name="action" value="login">Login</button>

                <div class="login-with">
                    <a href="https://www.facebook.com/v20.0/dialog/oauth?client_id=714288088041771&redirect_uri=http://localhost:9999/xilematic/fbcallback&scope=public_profile,email"><img
                            src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Facebook_Logo_%282019%29.png/1024px-Facebook_Logo_%282019%29.png"
                            alt="facebook"></a>
                    <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:9999/xilematic/ggcallback&response_type=code&client_id=747901007172-0j3e46shld22dvqq0q6cg0scptbv64ch.apps.googleusercontent.com&approval_prompt=force"><img
                            src="https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Google_Favicon_2025.svg/250px-Google_Favicon_2025.svg.png"
                            alt="google"></a>
                </div>

                <div class="register-link">
                    <p>Don't have an account? <a href="<%=PageLink.REGISTER_PAGE%>">Register</a></p>
                </div>
            </form>
        </div>
        <script>
            const passwordInput = document.getElementById('password');
            const togglePassword = document.getElementById('togglePassword');

            togglePassword.addEventListener('click', () => {
                const type = passwordInput.getAttribute('type');
                if (type === 'password') {
                    passwordInput.setAttribute('type', 'text');
                    togglePassword.innerHTML = '<ion-icon name="lock-open"></ion-icon>';
                } else {
                    passwordInput.setAttribute('type', 'password');
                    togglePassword.innerHTML = '<ion-icon name="lock-closed"></ion-icon>';
                }
            });
        </script>
        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    </body>

</html>