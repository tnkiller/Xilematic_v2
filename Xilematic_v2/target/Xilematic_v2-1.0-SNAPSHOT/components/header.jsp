<%@page import="constant.PageLink"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- CSS -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="<c:url value='/style/header_style.css'/>"/>
    </head>
    <body>
        <div class="site-header-wrapper">
            <header class="site-header">
                <!-- Logo -->
                <div class="site-header-logo">
                    <a href="<%=request.getContextPath()%>/homeservlet?">
                        <img src="<%=request.getContextPath()%>/asset/image/LOGO.png" alt="LOGO" />
                    </a>
                </div>

                <!-- Navigation links -->
                <nav class="site-header-nav">
                    <ul>
                        <li><a href="<%=request.getContextPath()%>/homeservlet?">Home</a></li>
                        <li><a href="#">News</a></li>
                        <li><a href="<%=request.getContextPath()%>/SelectCalendar?">Booking</a></li>
                    </ul>
                </nav>


                <!-- Search bar -->
                <div class="site-header-search" style='visibility: ${param.page eq "home" ? "visible" : "hidden"}'>
                    <form action="homeservlet" method="GET">
                        <input type="text" id="search-input" name="search" placeholder="Search for movies..." />
                        <button type="submit" class="search-button">
                            <i class="fas fa-search"></i>
                        </button>
                    </form>
                </div>



                <!-- User section -->
                <div class="site-header-user">
                    <c:choose>
                        <c:when test="${not empty sessionScope.userInfor}">
                            <div class="site-header-greeting">
                                <span>Hello, ${sessionScope.userInfor.username}</span>
                            </div>
                            <div class="site-header-avatar" id="user-avatar" style="background-color: ${sessionScope.colorCode};" onclick="toggleDropdown();">
                                <span id="avatar-letter"></span>
                            </div>
                            <div id="avatar-dropdown" class="site-header-dropdown">
                                <a href="<%=PageLink.PROFILE_PAGE%>">View Profile</a>
                                <a href="<%=request.getContextPath()%>/user/favorite.jsp">My favourite</a>
                                <a href="<%=request.getContextPath()%>/authenticate?action=logout">Logout</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="site-header-auth">
                                <a href="login.jsp">
                                    <button class="site-header-login">Login</button>
                                </a>
                                <a href="register.jsp">
                                    <button class="site-header-register">Register</button>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </header>
        </div>

        <script>
            $(document).ready(function () {
                var username = "${sessionScope.userInfor.username}";
                if (username) {
                    var firstLetter = username.charAt(0).toUpperCase();
                    $('#avatar-letter').text(firstLetter);
                }
            });


            function toggleDropdown() {
                document.getElementById("avatar-dropdown").classList.toggle("show");
            }

            window.onclick = function (event) {
                if (!event.target.matches('#user-avatar')) {
                    var dropdowns = document.getElementsByClassName("site-header-dropdown");
                    for (var i = 0; i < dropdowns.length; i++) {
                        var openDropdown = dropdowns[i];
                        if (openDropdown.classList.contains('show')) {
                            openDropdown.classList.remove('show');
                        }
                    }
                }
            }
        </script>
    </body>
</html>
