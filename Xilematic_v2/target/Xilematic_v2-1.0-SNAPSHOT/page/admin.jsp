
<%@page import="service.MovieService"%>
<%@page import="service.IMovieService"%>
<%@page import="model.Movie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="style/admin_style.css">
        <link rel="stylesheet" href="style/chart_style.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <body>


        <!-- header -->
        <header>
            <div class="logo"><a href="paging"><img src="asset/image/LOGO.png" width="50px" height="300px" alt=""></a></div>
            <div class="search-container">
                <input type="text" placeholder="search" class="search" />
                <span class="search-icon"><ion-icon name="search"></ion-icon></span>
            </div>
            <div class="avatar">Hello, ${sessionScope.alias}</div>
        </header>


        <!-- main -->
        <div class="main">
            <nav class="nav-bar">
                <a href="paging?type=stats" class="nav-link"><span><ion-icon name="home"></ion-icon></span></a>
                <a href="paging?type=users" class="nav-link"><span><ion-icon name="person"></ion-icon></span></a>
                <a href="paging?type=movies" class="nav-link"><span><ion-icon name="film"></ion-icon></span></a>
            </nav>


            <div class="data">
                <c:if test="${requestScope.type != 'stats'}">
                    <button class="open-popup-btn" id="openPopupBtn" data-type="${param.type}">Add New</button>
                </c:if>

                <!--TH hien thi thong tin trong bang-->
                <c:choose>
                    <c:when test="${requestScope.type == 'stats'}">
                        <!--TH hien thi thong tin statistic-->
                        <div class="chart-container">
                            <div class="chart-title">Biểu đồ đường động với dữ liệu ngẫu nhiên</div>
                            <canvas id="myChart" style="height: 1000px;"></canvas>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <!--TH hien thi thong tin table user , movie-->
                        <table>
                            <c:set var="isMovie" value="${requestScope.type == 'movies'}"/>
                            <thead>
                                <tr>
                                    <th>${isMovie ? "Movie name" : "Username"}</th>
                                    <th>${isMovie ? "Release Date" : "Fullname"}</th>
                                    <th>${isMovie ? "Rate" : "Email"}</th>
                                    <th>${isMovie ? "Hot" : "Phone number"}</th>
                                    <th>${isMovie ? "Status" : "Type"}</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${requestScope.type == 'movies'}">
                                        <c:forEach var="movie" items="${requestScope.list}">
                                            <tr>
                                                <td>${movie.movieName}</td>
                                                <td>${movie.releaseDate}</td>
                                                <td>${movie.rate}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${movie.hot}">
                                                            <input type="checkbox" checked="" disabled=""/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="checkbox" disabled=""/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${movie.status ? "Now showing" : "Coming soon"}</td>
                                                <td><a href="movies?action=showDetail&id=${movie.id}" class="detail">Detail</a></td>

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
                                                <td>${user.typeOfUser}</td>
                                                <td><a href="movies?action=showDetail&id=${user.id}" id="userDetail" class="detail">Detail</a></td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>



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



        <!-- POPUP -->
        <!-- popup này sẽ xuất hiện khi ấn nút Add new -->
        <div id="addMoviePopup" class="popup-overlay" style="display:none;">
            <div class="popup-content">
                <span class="close-btn" onclick="document.getElementById('addMoviePopup').style.display = 'none'">&times;</span>
                <h2>Thêm Phim Mới</h2>
                <!--form này sẽ được gửi đến movie servlet (url = \movies)-->
                <form action="movies" method="POST">
                    <label>Tên phim:</label>
                    <input type="text" name="title" required>

                    <label>Trailer (URL):</label>
                    <input type="url" name="trailer" required>

                    <label>Ảnh đại diện (image):</label>
                    <input type="url" name="image" accept="image/*" required>

                    <label>Mô tả (description):</label>
                    <textarea name="description" required></textarea>

                    <label>Ngày phát hành (release date):</label>
                    <input type="date" name="releaseDate" id="releaseDate" required>

                    <label>Đánh giá (rate):</label>
                    <input type="number" name="rate" min="0" max="10" step="1" required placeholder="Nhập số từ 0 tới 10">


                    <label>Trạng thái (status):</label>
                    <select name="status" required>
                        <option value="true">Đang chiếu</option>
                        <option value="false">Sắp chiếu</option>
                    </select>

                    <label>
                        Hot (phim nổi bật)
                        <input type="checkbox" name="hot">
                    </label>

                    <label>Diễn viên (actor):</label>
                    <input type="text" name="actor" required>

                    <label>Đạo diễn (director):</label>
                    <input type="text" name="director" required>

                    <button type="submit" name="action" value="add">Thêm phim</button>
                </form>
            </div>
        </div>






        <!-- popup này sẽ xuất hiện khi ấn nút Detail -->                    
        <div id="detailPopup" class="popup-overlay" style="display:none;">
            <div class="popup-content">
                <span class="close-btn" onclick="document.getElementById('detailPopup').style.display = 'none'">&times;</span>
                <h2>Chi tiết</h2>
                <!--form này sẽ được gửi đến movie servlet (url = \movies)-->
                <form action="movies" method="POST" class="form-row-btn">
                    <label>Tên phim:</label>
                    <input type="text" name="title" value="" required="">

                    <label>Trailer (URL):</label>
                    <input type="url" name="trailer" value="" required>

                    <label>Ảnh đại diện (image):</label>
                    <input type="url" name="image" accept="image/*" value="" required>

                    <label>Mô tả (description):</label>
                    <textarea name="description"></textarea>

                    <label>Ngày phát hành (release date):</label>
                    <input type="date" name="releaseDate" id="releaseDate" value="" required>

                    <label>Đánh giá (rate):</label>
                    <input type="number" name="rate" min="0" max="10" step="1" value="" required placeholder="Nhập số từ 0 tới 10">

                    <label>Trạng thái (status):</label>
                    <select name="status" required>
                        <option value="true">Đang chiếu</option>
                        <option value="false">Sắp chiếu</option>
                    </select>

                    <label>
                        Hot (phim nổi bật)
                        <input type="checkbox" name="hot">
                    </label>

                    <label>Diễn viên (actor):</label>
                    <input type="text" name="actor" value="" required>

                    <label>Đạo diễn (director):</label>
                    <input type="text" name="director" value="" required>

                    <div class="func-btn">
                        <button type="submit" name="action" value="update" class="update-btn">Cập nhật</button>
                        <button type="submit" name="action" value="delete" class="delete-btn">Xóa</button>
                    </div>
                </form>
            </div>
        </div>


        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Đóng popup khi nhấn vào nút đóng
                document.getElementById('openPopupBtn').addEventListener('click', function () {
                    document.getElementById('addMoviePopup').style.display = 'flex';
                });
            });
        </script>
        <script>
            // Lấy ngày hôm nay theo định dạng yyyy-mm-dd
            const today = new Date().toISOString().split('T')[0];
            document.getElementById('releaseDate').setAttribute('min', today);
            document.getElementById('releaseDate').value = today;
        </script>
        <script src="script/chart.js"></script>
        <!-- script để tương tác với popup -->
        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    </body>
</html>