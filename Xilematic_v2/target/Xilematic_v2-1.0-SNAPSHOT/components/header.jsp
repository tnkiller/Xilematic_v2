<%-- 
    Document   : header.jsp
    Created on : Jun 9, 2025, 9:42:55 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Header</title>
        <link rel="stylesheet"
              href="<c:url value='/style/header_style.css' />" />
    </head>
    <body>
        <header>
            <div class="header-logo">
                <div class="logo">
                    <a href="#"><img src="<c:url value='/asset/image/LOGO.png'/>" alt="Logo"/></a>
                </div>
                <div class="navbar">
                    <a href="#">Home</a>
                    <a href="#">News</a>
                    <a href="#">Booking</a>
                </div>
            </div>
            <div class="btn-authen">
                <button>Login</button>
                <button>Register</button>
            </div>
        </header>
    </body>
</html>
