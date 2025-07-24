<%@page import="constant.PageLink"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lịch sử đặt vé - CinemaPlus</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/booking_history_style.css">
    </head>
    <body style="margin: 0;">
        <header>
            <!-- Header -->
            <jsp:include page="${request.getContextPath()}/components/header.jsp">
                <jsp:param name="page" value="booking_history"/>
            </jsp:include>
        </header>

        <div class="booking-history-container">
            <div class="booking-history-header">
                <h1>Booking history</h1>
                <p>Xem lại tất cả các vé bạn đã đặt tại Xilematic</p>
                <p style="font-style: italic; font-size: 14px;">Dữ liệu sẽ bị xóa sau 7 ngày</p>
            </div>

            <div class="booking-history-filter">
                <div class="filter-group">
                    <div class="filter-item">
                        <label for="time-filter">Time</label>
                        <select id="time-filter">
                            <option value="all">All</option>
                            <option value="month">30 days ago</option>
                            <option value="3months">3 months ago</option>
                            <option value="year">1 year ago</option>
                        </select>
                    </div>

                </div>

                <button class="filter-button">
                    <i class="fas fa-filter"></i> Filter
                </button>
            </div>

            <div class="booking-history-list">
                <c:choose>
                    <c:when test="${empty bookingList}">
                        <div class="no-bookings">
                            <i class="fas fa-ticket-alt"></i>
                            <h3>Chưa có đơn đặt vé nào</h3>
                            <p>Hãy khám phá các bộ phim mới nhất và đặt vé ngay hôm nay!</p>
                            <a href='<%=request.getContextPath() + "/" + PageLink.HOME_SERVLET%>'><button class="explore-button">Khám phá ngay</button></a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${bookingList}" var="booking">
                            <div class="booking-card">
                                <div class="booking-card-header">
                                    <div class="booking-id">#${booking.ma_dat_ve}</div>
                                </div>

                                <div class="booking-card-content">
                                    <div class="movie-info">
                                        <div class="movie-poster">
                                            <c:choose>
                                                <c:when test="${not empty booking.movie.image}">
                                                    <img src="${booking.movie.image}" alt="${booking.movie.movieName}" style="width:100%; height:100%; object-fit:cover;">
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fas fa-film"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="movie-details">
                                            <h3 class="movie-title">${booking.movie.movieName}</h3>
                                            <div class="movie-info-item">
                                                <span class="info-label">Rạp:</span>
                                                <span class="info-value">${booking.showtime.rapPhim.tenRap}</span>
                                            </div>
                                            <div class="movie-info-item">
                                                <span class="info-label">Suất:</span>
                                                <span class="info-value">
                                                    ${booking.showtime.ngay_gio_chieu}
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="booking-details">
                                        <div class="detail-row">
                                            <span class="detail-label">Ghế:</span>
                                            <span class="detail-value">${booking.ghe_da_dat}</span>
                                        </div>
                                        <div class="detail-row">
                                            <span class="detail-label">Tổng tiền:</span>
                                            <span class="detail-value">
                                                ${booking.gia_ve}
                                            </span>
                                        </div>
                                    </div>
                                </div>

                                <div class="booking-card-footer">
                                    <a href='<%=request.getContextPath() + "/" + PageLink.BOOKING_HISTORY_SERVLET + "action=viewDetail&ma_dat_ve="%>${booking.ma_dat_ve}'>
                                        <button class="action-btn">
                                            <i class="fa-solid fa-circle-info"></i> Chi tiết
                                        </button>
                                    </a>
                                    <button class="action-button" onclick="rebook('${booking.movie.id}', '${booking.movie.movieName}')">
                                        <i class="fas fa-redo-alt"></i> Đặt lại
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${not empty bookingList}">
                <div class="booking-history-pagination">
                    <c:if test="${currentPage > 1}">
                        <button class="pagination-button" onclick="changePage(${currentPage - 1})">
                            <i class="fas fa-chevron-left"></i>
                        </button>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="page">
                        <button class="pagination-button ${page == currentPage ? 'active' : ''}" 
                                onclick="changePage(${page})">
                            ${page}
                        </button>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <button class="pagination-button" onclick="changePage(${currentPage + 1})">
                            <i class="fas fa-chevron-right"></i>
                        </button>
                    </c:if>
                </div>
            </c:if>
        </div>


        <script>
            // Hiệu ứng hover cho các card
            document.querySelectorAll('.booking-card').forEach(card => {
                card.addEventListener('mouseenter', () => {
                    card.style.transform = 'translateY(-5px)';
                    card.style.boxShadow = '0 10px 25px rgba(229, 9, 20, 0.2)';
                });

                card.addEventListener('mouseleave', () => {
                    card.style.transform = 'translateY(0)';
                    card.style.boxShadow = '0 5px 15px rgba(0, 0, 0, 0.3)';
                });
            });

            // Hiệu ứng cho nút lọc
            const filterButton = document.querySelector('.filter-button');
            if (filterButton) {
                filterButton.addEventListener('mouseenter', () => {
                    filterButton.style.transform = 'translateY(-2px)';
                });

                filterButton.addEventListener('mouseleave', () => {
                    filterButton.style.transform = 'translateY(0)';
                });

                filterButton.addEventListener('click', applyFilters);
            }

            // Hiệu ứng cho nút khám phá
            const exploreButton = document.querySelector('.explore-button');
            if (exploreButton) {
                exploreButton.addEventListener('mouseenter', () => {
                    exploreButton.style.transform = 'translateY(-2px)';
                });

                exploreButton.addEventListener('mouseleave', () => {
                    exploreButton.style.transform = 'translateY(0)';
                });
            }

            // Hiệu ứng cho nút hành động
            document.querySelectorAll('.action-button').forEach(button => {
                button.addEventListener('mouseenter', () => {
                    button.style.backgroundColor = '#e50914';
                    button.style.color = 'white';
                });

                button.addEventListener('mouseleave', () => {
                    button.style.backgroundColor = 'transparent';
                    button.style.color = '#e50914';
                });
            });

            // Hàm xử lý phân trang
            function changePage(page) {
                const url = new URL(window.location.href);
                url.searchParams.set('page', page);
                window.location.href = url.toString();
            }

            // Hàm xử lý lọc
            function applyFilters() {
                const timeFilter = document.getElementById('time-filter').value;
                const statusFilter = document.getElementById('status-filter').value;

                // Gửi yêu cầu lọc đến server
                const form = document.createElement('form');
                form.method = 'GET';
                form.action = '${pageContext.request.contextPath}/booking-history';

                const timeInput = document.createElement('input');
                timeInput.type = 'hidden';
                timeInput.name = 'timeFilter';
                timeInput.value = timeFilter;
                form.appendChild(timeInput);

                const statusInput = document.createElement('input');
                statusInput.type = 'hidden';
                statusInput.name = 'statusFilter';
                statusInput.value = statusFilter;
                form.appendChild(statusInput);

                document.body.appendChild(form);
                form.submit();
            }

            // Các hàm xử lý hành động
            function viewTicket(bookingId) {
                alert('Xem vé: ' + bookingId);
                window.location.href = '${pageContext.request.contextPath}/ticket/' + bookingId;
            }

            function rebook(bookingId, movieName) {
                alert('Đặt lại vé: ' + bookingId);
                window.location.href = '${pageContext.request.contextPath}/SelectCalendar?id=' + bookingId + '&movieName=' + movieName;
            }

            function payNow(bookingId) {
                alert('Thanh toán: ' + bookingId);
                window.location.href = '${pageContext.request.contextPath}/payment/' + bookingId;
            }

            function cancelBooking(bookingId) {
                if (confirm('Bạn có chắc chắn muốn hủy đơn đặt vé này?')) {
                    alert('Hủy vé: ' + bookingId);
                    // Gửi yêu cầu hủy đến server
                }
            }
        </script>
    </body>
</html>