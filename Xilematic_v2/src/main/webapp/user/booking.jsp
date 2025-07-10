<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
        <!-- Font Awesome for search icon -->
        <link rel="stylesheet" href="https://ionic.io/ionicons">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" href="<c:url value='/style/booking_D_style.css' />" />

    </head>
    <body>
        <jsp:include page="${request.getContextPath()}/components/header.jsp"/>
        <div class="content">
            <div class="booking">
                <div class="screen">SCREEN</div>
                <div class="booking-seats">
                    <c:set var="rowSet" value="" />
                    <c:forEach var="seat" items="${seats}">
                        <c:set var="row" value="${fn:substring(seat.ten_ghe, 0, 1)}" />
                        <c:if test="${not fn:contains(rowSet, row)}">
                            <c:set var="rowSet" value="${rowSet}${row}," />
                        </c:if>
                    </c:forEach>
                    <c:set var="rowSet" value="${fn:substring(rowSet, 0, fn:length(rowSet)-1)}" />
                    <c:forEach var="row" items="${fn:split(rowSet, ',')}">
                        <div class="row">
                            <c:forEach var="seat" items="${seats}">
                                <c:if test="${fn:startsWith(seat.ten_ghe, row)}">
                                    <c:choose>
                                        <c:when test="${seat.trang_thai == 'n/a'}">
                                            <button class="seat notavailable-seat">${seat.ten_ghe}</button>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${seat.loai_ghe == 'VIP'}">
                                                    <button class="seat vip-seat ${seat.da_dat ? 'selected-seat' : ''}" 
                                                            ${seat.da_dat ? 'disabled' : ''} 
                                                            onclick="selectSeat('${seat.ten_ghe}', 70000)">${seat.ten_ghe}</button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="seat ${seat.da_dat ? 'selected-seat' : ''}" 
                                                            ${seat.da_dat ? 'disabled' : ''} 
                                                            onclick="selectSeat('${seat.ten_ghe}', 45000)">${seat.ten_ghe}</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </div>
                <div class="note">
                    <div class="note-item">
                        <div class="available"></div>
                        <p>: Ghế trống</p>
                    </div>
                    <div class="note-item">
                        <div class="available selected"></div>
                        <p>: Đã đặt</p>
                    </div>
                    <div class="note-item">
                        <div class="available selecting"></div>
                        <p>: Đang chọn</p>
                    </div>
                    <div class="note-item">
                        <div class="available vip"></div>
                        <p>: Ghế VIP</p>
                    </div>
                </div>
            </div>
            <div class="detail">
                <div class="detail-banner">
                    <img src="${movie.image}"
                         alt="Banner">
                </div>
                <h2>${movie.movieName}</h2>
                <p>Giờ chiếu <span>${showtime.ngay_gio_chieu}</span></p>
                <button onclick="openModal()">
                    <div class="btn-trailer">Trailer <span><ion-icon name="caret-forward-circle-outline"></ion-icon></span>
                    </div>
                </button>
            </div>
            <div class="ticket">
                <form id="bookingForm" action="<c:url value='/checkout'/>" method="post">
                    <input type="hidden" name="ma_lich_chieu" value="${ma_lich_chieu}">
                    <input type="hidden" name="showtime" value="${showtime}">
                    <input type="hidden" name="movieName" value="${movie.movieName}">
                    <input type="hidden" name="selectedSeats" id="f-seats">
                    <input type="hidden" name="totalPrice" id="f-totalPrice">
                    <p id="selected-seats">Ghế đã chọn: None</p>
                    <p id="total-price">Tổng:0 VND</p>
                    <p id="warning">Vui lòng chọn ít nhất 1 ghế!</p>
                    <button class="btn-book" type="submit" onclick="submitBooking()">Xác nhận</button>
                </form>

            </div>
        </div>
        <%@ include file="/components/footer.jsp" %>
        <div id="videoModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <iframe width="560" height="315" data-src="${movie.trailer}"
                        title="YouTube video player" frameborder="0"
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                        referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
            </div>
            <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
            <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

            <script src="<c:url value='/script/booking_js.js' />"></script>

    </body>
</html>