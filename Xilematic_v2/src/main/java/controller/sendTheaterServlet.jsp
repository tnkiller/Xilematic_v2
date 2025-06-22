<%-- 
    Document   : sendTheaterServlet
    Created on : Jun 21, 2025, 9:36:21 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
      
        <c:choose>
            <c:when test="${requestScope.typeEdit == 'heThongRap'}">
                <jsp:useBean id="heThongRap" class="model.HeThongRap" scope="session"/>
                <jsp:setProperty name="heThongRap" property="*"/>
            </c:when>
            <c:when test="${requestScope.typeEdit == 'cumRap'}">
                <jsp:useBean id="cumRap" class="model.CumRap" scope="session"/>
                <jsp:setProperty name="cumRap" property="*"/>
            </c:when>
            <c:when test="${requestScope.typeEdit == 'rap'}">
                <jsp:useBean id="rap" class="model.RapPhim" scope="session"/>
                <jsp:setProperty name="rap" property="*"/>
            </c:when>
            <c:when test="${requestScope.typeEdit == 'lichChieu'}">
                <jsp:useBean id="lichChieu" class="model.LichChieu" scope="session"/>
                <jsp:setProperty name="lichChieu" property="*"/>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>

       
        
        
        
        <jsp:forward page="/TheaterSystemServlet"/>
    
</html>
