<%-- 
    Document   : intermediate
    Created on : Jun 21, 2025, 5:54:25 PM
    Author     : ADMIN
--%>

<%@page import="constant.PageLink"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        
        
        <%
            var act = request.getParameter("action");
            // Kiểm tra nếu act là null HOẶC session không tồn tại
            if (act == null && session.getAttribute("userInfor") == null) {
                // Chuyển hướng đến trang login.jsp
                response.sendRedirect("login.jsp");
                return; // Dừng việc thực thi tiếp theo của trang
            }
        %>


        <!--movie bean-->
        <jsp:useBean id="movie" class="model.Movie" scope="request"/>
        <jsp:setProperty name="movie" property="*"/>

        <!--user bean-->
        <jsp:useBean id="user" class="model.User" scope="request"/>
        <jsp:setProperty name="user" property="*"/>

        <!--navigate-->
        <c:choose>
            <c:when test='<%=act.equals("login") || act.equals("register")%>'>
                <jsp:forward page="authenticate"/>
            </c:when>
            <c:when test='<%=act.equals("add_movie") || act.equals("update_movie")%>'>
                <jsp:forward page="movies"/>
            </c:when>
            <c:when test='<%=act.equals("add_user") || act.equals("update_user")%>'>
                <jsp:forward page="users"/>
            </c:when>
            <c:when test='<%=act.equals("update_rapPhim") || act.equals("add_rapPhim")%>'>
                <jsp:forward page="rapPhim"/>
            </c:when>
            <c:when test='<%=act.equals("update_ghe") || act.equals("add_ghe")%>'>
                <jsp:forward page="ghe">
                    <jsp:param value="${param.maRap}" name="maRap"></jsp:param>
                </jsp:forward>
            </c:when>
            <c:otherwise>
                <jsp:forward page='<%=request.getContextPath() + "/" + PageLink.ERROR_PAGE%>'></jsp:forward>
            </c:otherwise>
        </c:choose>

    </body>
</html>
