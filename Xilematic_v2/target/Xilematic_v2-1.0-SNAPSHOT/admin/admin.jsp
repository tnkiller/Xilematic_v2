<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Movie"%>
<%@page import="service.MovieService"%>
<%@page import="service.IMovieService"%>
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
    <!-- Thay thế dropdown bằng các icon riêng -->
    <a href="paging?type=heThongRap" class="nav-link ${requestScope.type == 'heThongRap' ? 'active' : ''}">
        <i class="bi bi-badge-4k" title="Hệ thống rạp"></i>
    </a>
    <a href="paging?type=cumRap" class="nav-link ${requestScope.type == 'cumRap' ? 'active' : ''}">
        <i class="bi bi-building-gear fs-5" title="Cụm rạp"></i>
    </a>
    <a href="paging?type=rap" class="nav-link ${requestScope.type == 'rap' ? 'active' : ''}">
        <i class="bi bi-easel fs-5" title="Rạp"></i>
    </a>
    <a href="paging?type=lichChieu" class="nav-link ${requestScope.type == 'lichChieu' ? 'active' : ''}">
        <i class="bi bi-calendar-event fs-5" title="Lịch chiếu"></i>
    </a>
</nav>

            <!--dữ liệu bảng nằm bên phải nav-bar và chiếm khoảng 80% chiều rộng của trang-->
            <div class="data">
                <!--nut add new cho moi bang-->
                <c:choose>
                    <c:when test="${requestScope.type == 'movies'}">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal_movie" data-type="${param.type}"> Add New</button>
                    </c:when>

                    <c:when test="${requestScope.type == 'users'}">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal_user" data-type="${param.type}">Add New</button>
                    </c:when>

                    <c:when test="${requestScope.type == 'heThongRap'}">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addHeThongRapPopup" data-type="${param.type}">Add New</button>
                    </c:when>
                    <c:when test="${requestScope.type == 'cumRap'}">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addCumRapPopup" data-type="${param.type}">Add New</button>
                    </c:when>
                    <c:when test="${requestScope.type == 'rap'}">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addRapPopup" data-type="${param.type}">Add New</button>
                    </c:when>
                    <c:when test="${requestScope.type == 'lichChieu'}">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addLichChieuPopup" data-type="${param.type}">Add New</button>
                    </c:when>
                </c:choose>
                <!--TH hien thi thong tin bang-->
                <!--NOTE: type, list được lấy từ paging servlet-->
                <!--bat dau table-->
                <table>
                    <!--bat dau table head-->
                    <!--tổng cộng có 6 cái đầu-->
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
                                    <c:when test="${param.type == 'heThongRap'}">
                                    <th>Mã hệ thống rạp</th>
                                    <th>Tên hệ thống rạp</th>
                                    <th>Logo</th>
                                    <th>Action</th>
                                    </c:when>



                                <c:when test="${param.type == 'cumRap'}">
                                    <th>Mã cụm rạp</th>
                                    <th>Tên cụm rạp</th>
                                    <th>Địa chỉ</th>
                                    <th>Mã hệ thống rạp phụ thuộc </th>
                                    <th>Action</th>
                                    </c:when>




                                <c:when test="${param.type == 'rap'}">
                                    <th>Mã rạp</th>
                                    <th>Tên rạp</th>
                                    <th>Mã cụm rạp phụ thuộc</th>
                                    <th>Action</th>
                                    </c:when>



                                <c:when test="${param.type == 'lichChieu'}">
                                    <th>Mã lịch chiếu </th>
                                    <th>Mã rạp</th>
                                    <th>Mã Phim</th>
                                    <th>Ngày giờ chiếu</th>
                                    <th>Action</th>
                                    </c:when>
                                </c:choose>
                        </tr>
                    </thead>

                    <!--bat dau table body-->
                    <!--tổng cộng có 6 cái thân-->
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
                            <c:when test="${requestScope.type == 'heThongRap'}">
                                <c:forEach var="heThongRap" items="${requestScope.list}">
                                    <tr>
                                        <td>${heThongRap.maHeThongRap}</td>
                                        <td>${heThongRap.tenHeThongRap}</td>
                                        <td>
                                            <img src="${heThongRap.logo}" alt="${heThongRap.tenHeThongRap} Logo" 
                                                 class="img-thumbnail" style="max-width: 50px; max-height: 50px;">
                                        </td>
                                        <td>
                                            <a href="TheaterSystemServlet?action=heThongRap&id=${heThongRap.maHeThongRap}" class="text-primary me-2">
                                                <i class="bi bi-pencil-square"></i>
                                            </a>
                                            <form action="TheaterSystemServlet" method="POST" class="delete-btn d-inline">
                                                <input type="hidden" name="id" value="${heThongRap.maHeThongRap}"/>
                                                <input type="hidden" name="typeEdit" value="heThongRap"/>
                                                <button type="submit" name="action" value="delete" class="btn btn-link p-0">
                                                    <i class="bi bi-trash text-danger"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>


                            <c:when test="${requestScope.type == 'cumRap'}">
                                <c:forEach var="cumRap" items="${requestScope.list}">
                                    <tr>
                                        <td>${cumRap.maCumRap}</td>
                                        <td>${cumRap.tenCumRap}</td>
                                        <td>${cumRap.diaChi}</td>
                                        <td>
                                            <span class="badge bg-secondary">${cumRap.maHeThongRap}</span>
                                        </td>
                                        <td>
                                            <a href="TheaterSystemServlet?action=cumRap&id=${cumRap.maCumRap}" class="text-primary me-2">
                                                <i class="bi bi-pencil-square"></i>
                                            </a>
                                            <form action="TheaterSystemServlet" method="POST" class="delete-btn d-inline">
                                                <input type="hidden" name="id" value="${cumRap.maCumRap}"/>
                                                <input type="hidden" name="typeEdit" value="cumRap"/>
                                                <button type="submit" name="action" value="delete" class="btn btn-link p-0">
                                                    <i class="bi bi-trash text-danger"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>


                            <c:when test="${requestScope.type == 'rap'}">
                                <c:forEach var="rap" items="${requestScope.list}">
                                    <tr>
                                        <td>${rap.maRap}</td>
                                        <td>${rap.tenRap}</td>
                                        <td>
                                            <span class="badge bg-secondary">${rap.maCumRap}</span>
                                        </td>
                                        <td>
                                            <a href="TheaterSystemServlet?action=rap&id=${rap.maRap}" class="text-primary me-2">
                                                <i class="bi bi-pencil-square"></i>
                                            </a>
                                            <form action="TheaterSystemServlet" method="POST" class="delete-btn d-inline">
                                                <input type="hidden" name="id" value="${rap.maRap}"/>
                                                <input type="hidden" name="typeEdit" value="rap"/>
                                                <button type="submit" name="action" value="delete" class="btn btn-link p-0">
                                                    <i class="bi bi-trash text-danger"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>


                            <c:when test="${requestScope.type == 'lichChieu'}">
                                <c:forEach var="lichChieu" items="${requestScope.list}">
                                    <tr>
                                        <td>
                                            <span class="badge bg-secondary">${lichChieu.maLichChieu}</span>
                                        </td>
                                        <td>
                                            <span class="badge bg-primary">${lichChieu.maRap}</span>
                                        </td>
                                        <td>
                                            <span class="badge bg-info">${lichChieu.maPhim}</span>
                                        </td>
                                        <td>${lichChieu.ngayGioChieu}</td>
                                        <td>
                                            <a href="TheaterSystemServlet?action=lichChieu&id=${lichChieu.maLichChieu}" class="text-primary me-2">
                                                <i class="bi bi-pencil-square"></i>
                                            </a>
                                            <form action="TheaterSystemServlet" method="POST" class="delete-btn d-inline">
                                                <input type="hidden" name="maLichChieu" value="${lichChieu.maLichChieu}"/>
                                                <input type="hidden" name="typeEdit" value="lichChieu"/>
                                                <button type="submit" name="action" value="delete" class="btn btn-link p-0">
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
        <!--Hệ thống rạp--> 
        <jsp:include page="modal_HeThongRap.jsp">
            <jsp:param name="title" value="Thêm Hệ Rạp" />
            <jsp:param name="action" value="ADD" />
        </jsp:include>

        <!--cụm rạp--> 

        <jsp:include page="modal_add_CumRap.jsp">
            <jsp:param name="title" value="Thêm Cụm Rạp" />
            <jsp:param name="action" value="ADD" />
        </jsp:include>
        <!--rạp-->

        <jsp:include page="modal_add_Rap.jsp">
            <jsp:param name="title" value="Thêm Rạp" />
            <jsp:param name="action" value="ADD" />
        </jsp:include>
        <!--lịch chiếu-->
        <jsp:include page="modal_add_LichChieu.jsp">
            <jsp:param name="title" value="Thêm Lịch Chiếu" />
            <jsp:param name="action" value="ADD" />
        </jsp:include>
        >>>>>>> origin/home_detail_booking/vinh

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










