<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đặt Vé Xem Phim - CinemaVision</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="<c:url value='/style/booking_V_style.css' />" />
    </head>
    <body>
        <jsp:include page="${request.getContextPath()}/components/header.jsp">
            <jsp:param name="page" value="booking" />
        </jsp:include>
        <div class="container">
            <div class="headerForWeb">
                <div class="logo">
                    <i class="fas fa-film"></i>
                    <span>${selectedMovie.movieName}</span>
                </div>
                <div class="date-display">
                    <i class="far fa-calendar-alt"></i>
                    <%= new java.text.SimpleDateFormat("EEEE, dd/MM/yyyy").format(new java.util.Date())%>
                </div>
            </div>

            <main>
                <div class="booking-section">
                    <!-- Movie poster on the left -->
                    <div class="movie-poster-container hover-grow">
                        <img src="${selectedMovie.image}" alt="${selectedMovie.movieName}">
                        <div class="movie-poster-overlay">
                            <h3 class="movie-poster-title">${selectedMovie.movieName}</h3>
                            <div class="movie-poster-meta">
                                <span><i class="fas fa-star"></i> 9.2/10</span>
                                <span><i class="fas fa-clock"></i> 120 phút</span>
                            </div>
                        </div>
                    </div>

                    <!-- Booking form on the right -->
                    <div class="booking-card neumorphic">
                        <h2 class="booking-title">
                            <i class="fas fa-rocket"></i>
                            <span class="gradient-text">Đặt vé siêu tốc</span>
                        </h2>

                        <div class="form-grid">
                            <div class="select-wrapper">
                                <i class="fas fa-city"></i>
                                <select id="heThongRapSelect">
                                    <option value="">Hệ thống rạp</option>
                                    <c:forEach items="${listHeThongRap}" var="htr">
                                        <option value="${htr.maHeThongRap}">${htr.tenHeThongRap}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="select-wrapper">
                                <i class="fas fa-map-marker-alt"></i>
                                <select id="cumRapSelect" disabled>
                                    <option value="">Cụm rạp</option>
                                </select>
                            </div>

                            <div class="select-wrapper">
                                <i class="fas fa-theater-masks"></i>
                                <select id="rapPhimSelect" disabled>
                                    <option value="">Rạp phim</option>
                                </select>
                            </div>

                            <div class="select-wrapper">
                                <i class="far fa-calendar"></i>
                                <input type="date" id="ngayChieuInput" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>">
                            </div>

                            <button id="btnXemLichChieu" disabled>
                                <i class="fas fa-bolt"></i> Tìm lịch chiếu nhanh
                            </button>
                        </div>
                    </div>
                </div>

                <div class="showtimes-container" id="lichChieuResult">
                    <div class="empty-state">
                        <i class="far fa-clock"></i>
                        <h3>Chưa có lịch chiếu</h3>
                        <p>Vui lòng chọn rạp và ngày chiếu để xem lịch chiếu</p>
                    </div>
                </div>
            </main>
        </div>

        <div class="footer-namespace">
            <%@ include file="/components/footer.jsp" %>
        </div>

        <script>
            $(document).ready(function () {
                // Reset các trạng thái ban đầu
                function resetSelects() {
                    $('#cumRapSelect').prop('disabled', true)
                            .html('<option value="">Cụm rạp</option>');
                    $('#rapPhimSelect').prop('disabled', true)
                            .html('<option value="">Rạp phim</option>');
                    $('#btnXemLichChieu').prop('disabled', true);
                    $('#lichChieuResult').html(`
                    <div class="empty-state">
                        <i class="far fa-clock"></i>
                        <h3>Chưa có lịch chiếu</h3>
                        <p>Vui lòng chọn rạp và ngày chiếu để xem lịch chiếu</p>
                    </div>
                `);
                }

                // Khi chọn Hệ Thống Rạp
                $('#heThongRapSelect').change(function () {
                    resetSelects();

                    var heThongRapId = $(this).val();
                    if (heThongRapId) {
                        $.ajax({
                            url: 'SelectCalendar',
                            type: 'GET',
                            data: {
                                action: 'getCumRap',
                                heThongRapId: heThongRapId
                            },
                            beforeSend: function () {
                                $('#cumRapSelect').html('<option value="">Đang tải...</option>');
                            },
                            success: function (data) {
                                $('#cumRapSelect').empty().append('<option value="">Cụm rạp</option>');
                                $.each(data, function (index, cumRap) {
                                    $('#cumRapSelect').append(
                                            '<option value="' + cumRap.maCumRap + '">' + cumRap.tenCumRap + '</option>'
                                            );
                                });
                                $('#cumRapSelect').prop('disabled', false);
                            },
                            error: function () {
                                $('#cumRapSelect').html('<option value="">Lỗi khi tải cụm rạp</option>');
                            }
                        });
                    }
                });

                // Khi chọn Cụm Rạp
                $('#cumRapSelect').change(function () {
                    // Reset các select phía dưới
                    $('#rapPhimSelect').prop('disabled', true)
                            .html('<option value="">Rạp phim</option>');
                    $('#btnXemLichChieu').prop('disabled', true);
                    $('#lichChieuResult').html(`
                    <div class="empty-state">
                        <i class="far fa-clock"></i>
                        <h3>Chưa có lịch chiếu</h3>
                        <p>Vui lòng chọn rạp và ngày chiếu để xem lịch chiếu</p>
                    </div>
                `);

                    var cumRapId = $(this).val();
                    if (cumRapId) {
                        $.ajax({
                            url: 'SelectCalendar',
                            type: 'GET',
                            data: {
                                action: 'getRapPhim',
                                cumRapId: cumRapId
                            },
                            beforeSend: function () {
                                $('#rapPhimSelect').html('<option value="">Đang tải...</option>');
                            },
                            success: function (data) {
                                $('#rapPhimSelect').empty().append('<option value="">Rạp phim</option>');
                                $.each(data, function (index, rapPhim) {
                                    $('#rapPhimSelect').append(
                                            '<option value="' + rapPhim.maRap + '">' + rapPhim.tenRap + '</option>'
                                            );
                                });
                                $('#rapPhimSelect').prop('disabled', false);
                            },
                            error: function () {
                                $('#rapPhimSelect').html('<option value="">Lỗi khi tải rạp phim</option>');
                            }
                        });
                    }
                });

                // Kiểm tra điều kiện enable nút "Tìm lịch chiếu"
                $('#rapPhimSelect, #ngayChieuInput').change(function () {
                    var rapPhimId = $('#rapPhimSelect').val();
                    var ngayChieu = $('#ngayChieuInput').val();

                    if (rapPhimId && ngayChieu) {
                        $('#btnXemLichChieu').prop('disabled', false);
                    } else {
                        $('#btnXemLichChieu').prop('disabled', true);
                    }
                });

                // Khi click nút "Tìm lịch chiếu"
                $('#btnXemLichChieu').click(function () {
                    var rapPhimId = $('#rapPhimSelect').val();
                    var ngayChieu = $('#ngayChieuInput').val();

                    if (!rapPhimId || !ngayChieu) {
                        alert("Vui lòng chọn đầy đủ thông tin!");
                        return;
                    }

                    // Hiển thị loading
                    $('#lichChieuResult').html(`
                    <div class="loading">
                        <div class="spinner"></div>
                    </div>
                `);

                    $.ajax({
                        url: 'SelectCalendar',
                        type: 'GET',
                        data: {
                            action: 'getLichChieu',
                            rapPhimId: rapPhimId,
                            maPhim: ${selectedMovie.id},
                            ngayChieu: ngayChieu
                        },
                        success: function (response) {
                            // Giả sử response là HTML được trả về từ server
                            if (response.trim() === '') {
                                $('#lichChieuResult').html(`
                                <div class="empty-state">
                                    <i class="far fa-calendar-times"></i>
                                    <h3>Không có suất chiếu</h3>
                                    <p>Không tìm thấy suất chiếu nào cho phim này vào ngày đã chọn</p>
                                </div>
                            `);
                            } else {
                                $('#lichChieuResult').html(response);
                            }
                        },
                        error: function () {
                            $('#lichChieuResult').html(`
                            <div class="empty-state">
                                <i class="fas fa-exclamation-triangle"></i>
                                <h3>Lỗi khi tải lịch chiếu</h3>
                                <p>Đã xảy ra lỗi khi tải lịch chiếu. Vui lòng thử lại sau.</p>
                            </div>
                        `);
                        }
                    });
                });
            });
        </script>
    </body>
</html>