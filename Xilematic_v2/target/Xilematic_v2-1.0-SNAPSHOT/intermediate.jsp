<%-- 
    Document   : intermediate
    Created on : Jun 21, 2025, 5:54:25 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            var act = request.getParameter("action");
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
                <jsp:forward page="authenticate"></jsp:forward>
            </c:when>
            <c:when test='<%=act.equals("add_movie") || act.equals("update_movie")%>'>
                <jsp:forward page="movies"></jsp:forward>
            </c:when>
            <c:when test='<%=act.equals("add_user") || act.equals("update_user")%>'>d
                <jsp:forward page="users"></jsp:forward>
            </c:when>
        </c:choose>


    </body>
</html>
