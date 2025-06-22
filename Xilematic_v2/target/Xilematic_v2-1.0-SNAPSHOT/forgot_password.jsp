<%-- Document : login Created on : May 18, 2025, 1:27:57 AM Author : ADMIN --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FORGOT PASSWORD</title>
        <!--link css-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/login_style.css">
        <!--logo WEB-->
        <link rel="icon" type="logo" href="asset/download.png" />
    </head>

    <body>
        <div class="login-box">
            <form action="authenticate" method="POST">
                <input type="hidden" name="sourcePage" value="forgotPassword"/>
                <h2>Forgot password</h2>
                <div class="input-box">
                    <span class="icon"><ion-icon name="refresh-circle"></ion-icon></span>
                    <input type="text" required name="input" value="${requestScope.username}">
                    <label>Username/Email</p></label>
                </div>


                <div style="color: greenyellow; margin-bottom: 20px">${requestScope.msg}</div>


                <c:choose>
                    <c:when test="${requestScope.isExist == 1}">
                        <div class="input-box">
                            <span class="icon" id="togglePassword" style="cursor: pointer;"><ion-icon
                                    name="lock-closed"></ion-icon></span>
                            <input type="password" required name="password" id="password">
                            <label>New password</label>
                        </div>
                        <input type="hidden" name="action" value="existed" />
                    </c:when>
                    <c:when test="${requestScope.isExist == -1}">
                        <div class="error-message">This user does NOT exist!</div>
                        <input type="hidden" name="action" value="none" />
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="action" value="none" />
                    </c:otherwise>
                </c:choose>



                <button type="submit">${requestScope.msg != null ? "Back to sign in" : "Reset"}</button>


            </form>
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
        </div>
    </body>

</html>