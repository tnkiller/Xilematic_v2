<%@page import="model.Movie"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>My Favorite Movies</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/favourite.css">
    </head>
    <body>
        <div class="header">
            HEADER
        </div>

        <div class="container">
            <div class="row">
                <div class="col-12">
                    <h1 class="main-title">
                        MY FAVOURITE
                        <span class="movie-count">You have ${movieCount} films</span>
                    </h1>

                    <div class="sort-by">
                        <form action="${pageContext.request.contextPath}/favorites" method="GET">
                            <select class="form-select w-auto" name="sort" onchange="this.form.submit()">
                                <option value="default" ${param.sort == 'default' ? 'selected' : ''}>Sort by:</option>
                                <option value="popular" ${param.sort == 'popular' ? 'selected' : ''}>Most Popular</option>
                                <option value="newest" ${param.sort == 'newest' ? 'selected' : ''}>Newest</option>
                                <option value="title" ${param.sort == 'title' ? 'selected' : ''}>Alphabetical</option>
                            </select>
                        </form>
                    </div>

                    <div class="row">
                        <%
                            // Kiểm tra các scope bằng Scriptlet
                            List<Movie> favoriteList = null;

                            if (request.getAttribute("favoriteList") != null) {
                                favoriteList = (List<Movie>) request.getAttribute("favoriteList");
                            } else if (session.getAttribute("favoriteList") != null) {
                                favoriteList = (List<Movie>) session.getAttribute("favoriteList");
                            }
                            pageContext.setAttribute("favoriteList", favoriteList);
                        %>
                        <c:forEach var="movie" items="${favoriteList}">
                            <div class="col-md-4">
                                <div class="movie-card">
                                    <div class="movie-poster" style="background-image: url('${movie.image}');"></div>
                                    <div class="movie-info">
                                        <div class="movie-title">${movie.movieName}</div>
                                        <div class="movie-director">Director: ${movie.director}</div>
                                        <div class="movie-meta">
                                            <span class="movie-duration">144m</span>
                                            <span class="movie-views">144m views</span>
                                        </div>
                                        <div class="movie-actions">
                                            <a href="${pageContext.request.contextPath}/book?movieId=${movie.id}" class="btn btn-book">DAT VE</a>
                                            <a href="${pageContext.request.contextPath}/favorites?movieId=${movie.id}" class="btn btn-delete">XOA</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <div class="footer">
            FOOTER
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>