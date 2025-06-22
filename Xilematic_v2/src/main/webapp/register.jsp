<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="constant.PageLink"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>REGISTER</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/register_style.css">
    </head>
    <body>
        <div class="login-box">
            <form action="<%=request.getContextPath()%>/intermediate.jsp" method="POST">
                <h2>Register</h2>
                <!-- Username Field -->
                <div class="input-box">
                    <span class="icon"><ion-icon name="person"></ion-icon></span>
                    <input type="text" required name="username" id="username" autofocus value="${username}">
                    <label>Username</label>
                    <span class="error-message" id="username-error">${errUsername}</span> <!-- Error message for Username -->
                </div>

                <!-- Fullname Field -->
                <div class="input-box">
                    <span class="icon"><ion-icon name="person-circle"></ion-icon></span>
                    <input type="text" required name="fullname" id="fullname" value="${fullname}">
                    <label>Fullname</label>
                    <span class="error-message" id="fullname-error">${errFullname}</span> <!-- Error message for Fullname -->
                </div>

                <!-- Email Field -->
                <div class="input-box">
                    <span class="icon"><ion-icon name="mail"></ion-icon></span>
                    <input type="email" required name="email" id="email" value="${email}">
                    <label>Email</label>
                    <span class="error-message" id="email-error">${errEmail}</span> <!-- Error message for Email -->
                </div>

                <!-- Phone Number Field -->
                <div class="input-box">
                    <span class="icon"><ion-icon name="call"></ion-icon></span>
                    <input type="tel" required name="phoneNumber" id="phoneNum" value="${phoneNum}">
                    <label>Phone Number</label>
                    <span class="error-message" id="phoneNum-error">${errPhoneNumber}</span> <!-- Error message for Phone Number -->
                </div>

                <!-- Password Field -->
                <div class="input-box">
                    <span class="icon" id="togglePassword" style="cursor: pointer;"><ion-icon name="lock-closed"></ion-icon></span>
                    <input type="password" required name="password" id="password">
                    <label>Password</label>
                    <span class="error-message" id="password-error">${errPassword}</span> <!-- Error message for Password -->
                </div>

                <!-- Confirm Password Field -->
                <div class="input-box">
                    <span class="icon" id="toggleConfirmPassword" style="cursor: pointer;"><ion-icon name="lock-closed"></ion-icon></span>
                    <input type="password" required name="confirmPassword" id="confirmPassword">
                    <label>Confirm password</label>
                    <span class="error-message" id="password-error">${errConfirmPassword}</span> <!-- Error message for Password -->
                </div>

                <input type="hidden" name="action" value="register"/>
                <c:choose>
                    <c:when test="${requestScope.msg == null}">
                        <div class="submit-btn"><button type="submit">Register</button></div>
                    </c:when>
                    <c:otherwise>
                        <div class="submit-btn"><a href="<%=PageLink.LOGIN_PAGE%>">${msg}</a></div>
                        </c:otherwise>
                    </c:choose>
            </form>
        </div>
    </body>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</html>

