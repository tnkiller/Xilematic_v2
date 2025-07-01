<%-- 
    Document   : header.jsp
    Created on : Jun 9, 2025, 9:42:55 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Header</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <!-- Font Awesome for search icon -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" href="<c:url value='/style/header_style.css'/>"/>

    </head>
    <body>
        <header class="bg-light py-3 shadow-sm">
            <div class="container d-flex justify-content-between align-items-center">
                <!-- Logo and Navigation -->
                <div class="header-logo">
                    <div class="logo">
                        <a href="#"><img src="<c:url value='/asset/image/LOGO.png'/>" alt="LOGO"></a>
                    </div>
                    <nav class="navbar">
                        <a href="homeservlet" class="text-dark">Home</a>
                        <a href="#" class="text-dark">News</a>
                        <a href="${pageContext.request.contextPath}/SelectCalendar" class="text-dark">Booking</a>
                    </nav>
                </div>

                <!-- Search Form -->
                <form action="homeservlet" method="GET" id="search-box" class="input-group">
                    <input type="text" id="search-text" name="search" class="form-control" placeholder="Search for movies...">
                    <button type="submit" id="search-btn" class="btn btn-danger">
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </button>
                </form>

                <!-- Authentication Section -->
                <div class="btn-authen">
                    <c:choose>
                        <c:when test="${empty sessionScope.userInfor}">
                            <a href="login.jsp" class="btn btn-outline-danger me-2">Login</a>
                            <a href="register.jsp" class="btn btn-danger">Register</a>
                        </c:when>
                        <c:otherwise>
                            <!-- Dropdown for logged-in user -->
                            <div class="dropdown">
                                <button class="btn btn-link p-0" type="button" id="avatarDropdown" data-bs-toggle="dropdown" aria-expanded="false">

                                    <div class="avatar" id="userAvatar">${sessionScope.userInfor.username.charAt(0)}</div>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="avatarDropdown">
                                    <li><a class="dropdown-item" href="profile.jsp">View Profile</a></li>
                                    <li><a class="dropdown-item" href="favorites?">My favourite</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item text-danger" href="authenticate?action=logout">Logout</a></li>
                                </ul>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </header>
        <script>
            // Generate random background color for avatar
            function getRandomColor() {
                const letters = '0123456789ABCDEF';
                let color = '#';
                for (let i = 0; i < 6; i++) {
                    color += letters[Math.floor(Math.random() * 16)];
                }
                return color;
            }

            // Apply random color to avatar
            document.addEventListener('DOMContentLoaded', function () {
                const avatar = document.getElementById('userAvatar');
                if (avatar) {
                    avatar.style.backgroundColor = getRandomColor();
                }
            });
        </script>
    </body>
</html>