<%-- 
    Document   : table_movie
    Created on : Jun 22, 2025, 5:21:14 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="1">
            <thead>
                <tr>
                    <th>Movie Name</th>
                    <th>Release Date</th>
                    <th>Rate</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="movie" items="${requestScope.movies}">
                    <tr>
                        <td>${movie.movieName}</td>
                        <td>${movie.releaseDate}</td>
                        <td>${movie.rate}</td>
                        <td>${movie.status}</td>
                        <td>
                            <a href="edit?id=${movie.id}">Edit</a> | 
                            <a href="delete?id=${movie.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
