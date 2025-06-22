<%@page import="service.MovieService"%>
<%@page import="service.IMovieService"%>
<%@page import="model.Movie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>ADMIN PAGE</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
        <link href="<%=request.getContextPath()%>/style/admin_style.css" rel="stylesheet">
    </head>
    <body>
        <!-- header -->
        <header class="shadow-sm">
            <div class="logo d-none d-md-block">
                <a href="paging" class="d-flex align-items-center">
                    <img src="asset/image/LOGO.png" width="40" height="40" alt="Logo">
                </a>
            </div>
            <div class="search-container">
                <input type="text" placeholder="Search..." class="search form-control">
                <span class="search-icon"><i class="bi bi-search"></i></span>
            </div>
            <div class="avatar d-flex align-items-center">
                <i class="bi bi-person-circle me-2"></i>
                Hello, ${sessionScope.alias}
            </div>
        </header>

        <!-- main -->
        <div class="main">
            <!--nav-bar nằm bên trái-->
            <nav class="nav-bar">
                <a href="paging?type=stats" class="nav-link ${requestScope.type == 'stats' ? 'active' : ''}">
                    <i class="bi bi-house-door fs-5"></i>
                </a>
                <a href="paging?type=users" class="nav-link ${requestScope.type == 'users' ? 'active' : ''}">
                    <i class="bi bi-people fs-5"></i>
                </a>
                <a href="paging?type=movies" class="nav-link ${requestScope.type == 'movies' ? 'active' : ''}">
                    <i class="bi bi-film fs-5"></i>
                </a>
            </nav>

            <!--dữ liệu bảng nằm bên phải nav-bar và chiếm khoảng 80% chiều rộng của trang-->
            <div class="data">
                <c:choose>
                    <c:when test="${requestScope.type == 'movies'}">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal_movie" data-type="${param.type}"> Add New</button>
                    </c:when>
                    <c:when test="${requestScope.type == 'users'}">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal_user" data-type="${param.type}">Add New</button>
                    </c:when>
                </c:choose>



                <!--TH hien thi thong tin bang-->
                <c:if test="${not empty list}">
                    <table>
                        <thead>
                            <tr>
                                <c:choose>
                                    <c:when test="${param.type == 'movies'}">
                                        <th>Movie Name</th>
                                        <th>Release Date</th>
                                        <th>Rate</th>
                                        <th>Hot</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                        </c:when>
                                        <c:when test="${param.type == 'users'}">
                                        <th>Username</th>
                                        <th>Full Name</th>
                                        <th>Email</th>
                                        <th>Phone Number</th>
                                        <th>Type</th>
                                        <th>Action</th>
                                        </c:when>
                                    </c:choose>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${requestScope.type == 'movies'}">
                                    <c:forEach var="movie" items="${requestScope.list}">
                                        <tr>
                                            <td>${movie.movieName}</td>
                                            <td>${movie.releaseDate}</td>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <i class="bi bi-star-fill text-warning me-1"></i>
                                                    ${movie.rate}
                                                </div>
                                            </td>
                                            <td>
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input" type="checkbox" ${movie.hot ? 'checked' : ''} disabled>
                                                </div>
                                            </td>
                                            <td>
                                                <span class="badge ${movie.status ? 'bg-success' : 'bg-info'}">
                                                    ${movie.status ? "Now showing" : "Coming soon"}
                                                </span>
                                            </td>
                                            <td class="action-field">
                                                <a href="movies?action=showDetail&id=${movie.id}" class="update-btn">
                                                    <i class="bi bi-pencil-square text-primary"></i>
                                                </a>
                                                <form action="movies" method="POST" class="delete-btn d-inline">
                                                    <input type="hidden" name="id" value="${movie.id}"/>
                                                    <button onclick="return confirm('Are your sure?')" type="submit" name="action" value="delete" class="btn btn-link p-0 ms-2">
                                                        <i class="bi bi-trash text-danger"></i>
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${requestScope.type == 'users'}">
                                    <c:forEach var="user" items="${requestScope.list}">
                                        <tr>
                                            <td>${user.username}</td>
                                            <td>${user.fullname}</td>
                                            <td>${user.email}</td>
                                            <td>${user.phoneNumber}</td>
                                            <td>
                                                <span class="badge ${user.typeOfUser == 'ADMIN' ? 'bg-danger' : 'bg-secondary'}">
                                                    ${user.typeOfUser}
                                                </span>
                                            </td>
                                            <td>
                                                <a href="users?action=showDetail&id=${user.id}">
                                                    <i class="bi bi-pencil-square text-primary"></i>
                                                </a>
                                                <form action="users" method="POST" class="delete-btn d-inline">
                                                    <input type="hidden" name="id" value="${user.id}"/>
                                                    <button type="submit" name="action" value="delete" class="btn btn-link p-0 ms-2">
                                                        <i class="bi bi-trash text-danger"></i>
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </tbody>
                    </table>
                </c:if>





                <!--Phan trang-->
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="paging?type=${type}&page=${currentPage - 1}">Previous</a>
                    </c:if>

                    <c:if test="${totalPages < 5}">
                        <c:forEach  var="i" begin="1" end="${totalPages}" >
                            <c:choose>
                                <c:when test="${i == currentPage}">
                                    <strong>${i}</strong>
                                </c:when>
                                <c:otherwise>
                                    <a href="paging?type=${type}&page=${i}">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:if>
                    <c:if test="${totalPages >= 5}">                            
                        <c:forEach var="i" begin="${currentPage - 2 > 0 ? currentPage - 2 : 1}" end="${totalPages > currentPage + 2 ? currentPage + 2 : totalPages}" >
                            <c:choose>
                                <c:when test="${i == currentPage}">
                                    <strong>${i}</strong>
                                </c:when>
                                <c:otherwise>
                                    <a href="paging?type=${type}&page=${i}">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:if>

                    <c:if test="${currentPage < totalPages}">
                        <a href="paging?type=${type}&page=${currentPage + 1}">Next</a>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- POPUP ADD NEW -->
        <!-- MOVIE -->
        <jsp:include page="modal_movie.jsp">
            <jsp:param name="title" value="ADD NEW MOVIE" />
            <jsp:param name="action" value="ADD" />
        </jsp:include>
        <!-- USER -->
        <jsp:include page="modal_user.jsp">
            <jsp:param name="title" value="ADD NEW USER" />
            <jsp:param name="action" value="ADD" />
        </jsp:include>

        <script>
            // Lấy ngày hôm nay theo định dạng yyyy-mm-dd
            const today = new Date().toISOString().split('T')[0];
            document.getElementById('releaseDate').setAttribute('min', today);
            document.getElementById('releaseDate').value = today;
        </script>
        <script src="script/chart.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>










