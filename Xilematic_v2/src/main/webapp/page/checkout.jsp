<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Xác nhận thanh toán</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
         <link rel="stylesheet"
              href="<c:url value='/style/checkout_style.css' />" />
    </head>
    <body>
        <div class="card">
            <div class="logo">
                <div class="logo-circle">
                    <svg viewBox="0 0 24 24">
                    <path d="M4,4v5h16V4H4z M4,10v10h16V10H4z"/>
                    </svg>
                </div>
            </div>

            <h2>XÁC NHẬN THÔNG TIN ĐẶT VÉ</h2>

            <div class="row">
                <span>Tên phim:</span>
                <span>${movieName}</span>
            </div>

            <div class="row">
                <span>Tên rạp:</span>
                <span>${tenCumRap}</span>
            </div>

            <div class="row">
                <span>Phòng chiếu:</span>
                <span>${tenRap}</span>
            </div>

            <div class="row">
                <span>Suất chiếu:</span>
                <span>${showtime}</span>
            </div>

            <div class="row">
                <span>Ghế đã chọn:</span>
                <span>${selectedSeats}</span>
            </div>

            <div class="row total-row">
                <span>Tổng tiền:</span>
                <span>${totalPrice} VND</span>
            </div>

            <form action="checkout?action=confirm" method="post">
                <input type="hidden" name="movieName" value="${movieName}">
                <input type="hidden" name="ma_lich_chieu" value="${ma_lich_chieu}">
                <input type="hidden" name="maRap" value="${maRap}">
                <input type="hidden" name="showtime" value="${showtime}">
                <input type="hidden" name="selectedSeats" value="${selectedSeats}">
                <input type="hidden" name="totalPrice" value="${totalPrice}">
                <button class="btn-confirm" type="submit">
                    Xác nhận thanh toán
                </button>
            </form>
        </div>
    </body>
</html>
