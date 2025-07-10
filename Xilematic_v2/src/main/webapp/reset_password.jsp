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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/reset_password.css">
    </head>

    <body>
        <div class="login-box">
            <form action='<%=request.getContextPath() + "/" + PageLink.RESET_PASSWORD_SERVLET%>' method="POST">
                <h2>Reset password</h2>

                <div class="input-box">
                    <span class="icon"><ion-icon name="person"></ion-icon></ion-icon></span>
                    <input type="email" required name="email" value="${requestScope.email}" readonly="">
                </div>

                <div class="input-box">
                    <span class="icon" id="togglePassword" style="cursor: pointer;"><ion-icon
                            name="lock-closed"></ion-icon></span>
                    <input type="password" required minlength="6" name="password" id="password" >
                    <label>Password</label>
                </div>

                <div class="input-box">
                    <span class="icon" id="toggleConfirmPassword" style="cursor: pointer;"><ion-icon name="eye-off"></ion-icon></span>
                    <input type="password" required minlength="6" name="confirmPassword" id="confirmPassword">
                    <label>Confirm password</label>
                </div>

                <div class="error-message" style="margin: 0 auto; color: red;">${requestScope.msg}</div>


                <c:if test="${not empty requestScope.succMsg}">
                    <div class="error-message" style="margin: 0 auto; color: greenyellow;">${requestScope.succMsg}</div>
                </c:if>
                <c:if test="${not empty requestScope.errMsg}">
                    <div class="error-message" style="margin: 0 auto; color: red;">${requestScope.errMsg}</div>
                </c:if>

                <button type="submit" name="action" value="forgotPassword">Reset</button>

            </form>
        </div>
        <script>
            const passwordInput = document.getElementById('password');
            const togglePassword = document.getElementById('togglePassword');
            const confpasswordInput = document.getElementById('confirmPassword');
            const conftogglePassword = document.getElementById('toggleConfirmPassword');

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

            conftogglePassword.addEventListener('click', () => {
                const type = confirmPassword.getAttribute('type');
                if (type === 'password') {
                    confpasswordInput.setAttribute('type', 'text');
                    conftogglePassword.innerHTML = '<ion-icon name="eye"></ion-icon>';
                } else {
                    confpasswordInput.setAttribute('type', 'password');
                    conftogglePassword.innerHTML = '<ion-icon name="eye-off"></ion-icon>';
                }
            });
        </script>
        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    </body>

</html>