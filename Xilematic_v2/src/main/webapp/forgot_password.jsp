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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/forget_password.css">
    </head>

    <body>
        <div class="login-box">
            <a href="${pageContext.request.contextPath}/<%=PageLink.LOGIN_PAGE%>" class="home-button">
                <span>← Login</span>
            </a>
            <form action="<%=request.getContextPath()%>/<%=PageLink.AUTHENTICATE_SERVLET%>" method="POST">
                <h2>Reset password</h2>
                <div class="input-box">
                    <span class="icon"><ion-icon name="person"></ion-icon></ion-icon></span>
                    <input type="email" required name="recoverEmail">
                    <label>Email</p></label>
                </div>

                <c:if test="${not empty requestScope.succMsg}">
                    <div class="error-message" style="margin: 0 auto; color: greenyellow;">${requestScope.succMsg}</div>
                </c:if>
                <c:if test="${not empty requestScope.errMsg}">
                    <div class="error-message" style="margin: 0 auto; color: red;">${requestScope.errMsg}</div>
                </c:if>

                <button type="submit" name="action" value="forgotPassword">Send</button>

                <div class="register-link">
                    <p>Don't have an account? <a href="<%=PageLink.REGISTER_PAGE%>">Register</a></p>
                </div>
            </form>
        </div>
        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    </body>

</html>