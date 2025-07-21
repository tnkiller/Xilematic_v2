<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết vé - CinemaPlus</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/booking_history_detail.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/qrcode/1.4.4/qrcode.min.js" integrity="sha512-6x997BOv0e4O+G45Cwr4PZMYXP0ATChLcSob5IB6DPpXvDfRDMBFClXOg5ahmIVBoAf2tnsENpkHRJckceZiwQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    </head>
    <body style="margin: 0;">
        <!-- Header -->
        <jsp:include page="/components/header.jsp">
            <jsp:param name="page" value="ticket-detail"/>
        </jsp:include>

        <div class="ticket-card">
            <div class="ticket-info">
                <div class="ticket-left">
                    <div class="movie-info">
                        <div class="movie-poster">
                            <img src="${booking.movie.image}" alt="poster">
                        </div>
                        <div class="movie-details">
                            <h2 class="movie-title">${booking.movie.movieName}</h2>
                            <div class="movie-info-item">
                                <span class="info-label">Thể loại:</span>
                                <span class="info-value">${booking.movie.movieName}</span>
                            </div>
                            <div class="movie-info-item">
                                <span class="info-label">Đạo diễn:</span>
                                <span class="info-value">${booking.movie.director}</span>
                            </div>
                            <div class="movie-info-item">
                                <span class="info-label">Diễn viên:</span>
                                <span class="info-value">${booking.movie.actor}</span>
                            </div>
                            <div class="movie-info-item">
                                <span class="info-label">Ngôn ngữ:</span>
                                <span class="info-value">Tiếng Anh - Phụ đề Tiếng Việt</span>
                            </div>
                        </div>
                    </div>

                    <div class="ticket-details">
                        <div class="detail-grid">
                            <div class="detail-item">
                                <div class="detail-label">Rạp chiếu</div>
                                <div class="detail-value">${booking.showtime.rapPhim.tenRap}</div>
                            </div>
                            <div class="detail-item">
                                <div class="detail-label">Phòng chiếu</div>
                                <div class="detail-value">IMAX 3</div>
                            </div>
                            <div class="detail-item">
                                <div class="detail-label">Suất chiếu</div>
                                <div class="detail-value">${booking.showtime.ngay_gio_chieu}</div>
                            </div>
                            <div class="detail-item">
                                <div class="detail-label">Ghế ngồi</div>
                                <div class="detail-value">${booking.ghe_da_dat}</div>
                            </div>
                            <div class="detail-item">
                                <div class="detail-label">Số lượng vé</div>
                                <div class="detail-value">${booking.gia_ve / 45000}</div>
                            </div>
                            <div class="detail-item">
                                <div class="detail-label">Hình thức thanh toán</div>
                                <div class="detail-value">Ví điện tử VN Pay</div>
                            </div>
                            <div class="detail-item">
                                <div class="detail-label">Tổng thanh toán</div>
                                <div class="detail-value">${booking.gia_ve} ₫</div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="ticket-right">
                    <div class="ticket-id">#${booking.ma_dat_ve}</div>
                    <div class="ticket-status status-valid">VÉ HỢP LỆ</div>

                    <div class="qr-container">
                        <div id="qr-code" class="qr-code"><img alt="qrcode" id="qrCodeImage"></div>
                        <p class="qr-note">Vui lòng trình mã QR này tại quầy soát vé để được vào rạp</p>
                    </div>

                    <div class="ticket-actions">
                        <button class="action-button" onclick="saveTicket()">
                            <i class="fas fa-download"></i> Lưu vé
                        </button>
                        <button class="action-button" onclick="shareTicket()">
                            <i class="fas fa-share-alt"></i> Chia sẻ
                        </button>
                    </div>
                </div>
            </div>

            <div class="print-section">
                <button class="print-button" onclick="printTicket()">
                    <i class="fas fa-print"></i> In vé
                </button>
            </div>

            <div class="ticket-footer">
                <div class="footer-note">
                    <p><strong>Lưu ý:</strong> Vui lòng đến rạp trước ít nhất 15 phút để đổi vé. Mỗi mã QR chỉ có giá trị sử dụng một lần. Vé đã mua không thể hoàn trả hoặc đổi sang suất chiếu khác. Mang theo CMND/CCCD khi đổi vé nếu cần thiết.</p>
                </div>
            </div>
        </div>
    </div>

    <script>
                    // Tạo QR code
                    const bookingData = {
                        ticketId: "${booking.ma_dat_ve}",
                        movie: "${booking.movie.movieName}",
                        theater: "${booking.showtime.rapPhim.tenRap}",
                        room: "IMAX 3", // Thay thế bằng dữ liệu thực tế nếu có
                        showtime: "${booking.showtime.ngay_gio_chieu}",
                        seats: "${booking.ghe_da_dat}",
                        quantity: "${booking.gia_ve / 45000}",
                        total: "${booking.gia_ve}",
                        paymentMethod: "Ví điện tử VN Pay",
                        customer: "${user.name}", // Giả sử có thông tin user trong session
                        phone: "${user.phone}"    // Giả sử có thông tin user trong session
                    };
                    const ticketData = JSON.stringify(bookingData);
                    document.addEventListener("DOMContentLoaded", async () => {
                        const dataUrl = await toDataURL(ticketData);
                        const qrCodeImage = document.getElementById("qrCodeImage");
                        qrCodeImage.src = dataUrl;
                    });


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
    </script>
</body>
</html>