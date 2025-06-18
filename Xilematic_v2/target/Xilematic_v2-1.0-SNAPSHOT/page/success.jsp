<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Xác nhận thanh toán thành công</title>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
        <link rel="stylesheet"
              href="<c:url value='/style/success_style.css' />" />
    </head>
    <body>
        <div class="confirmation-card">
            <div class="header">
                <div class="success-icon"><i class="fas fa-check"></i></div>
                <h1>Thanh toán thành công!</h1>
                <p class="subtitle">
                    Giao dịch của bạn đã được xử lý thành công.
                    Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.
                </p>
            </div>

            <div class="content">
                <div class="details">
                    <div class="detail-row">
                        <span class="detail-label">Mã giao dịch:</span>
                        <span class="detail-value" id="transaction-id"></span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Ngày thanh toán:</span>
                        <span class="detail-value" id="payment-date"></span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Số tiền:</span>
                        <span class="detail-value highlight">${totalPrice} ₫</span>
                    </div>
                </div>

                <button class="home-button" onclick="window.location.href = '<c:url value="/page/home.jsp" />'">
                    <i class="fas fa-home"></i> Về trang chủ
                </button>
            </div>

            <div class="footer">
                <p>
                    Cần hỗ trợ? Liên hệ chúng tôi qua
                    <a href="mailto:support@example.com">support@example.com</a>
                </p>
                <p>© 2025 Công ty Cổ phần ABC. Tất cả quyền được bảo lưu.</p>
            </div>
        </div>

        <script src="<c:url value='/script/success_js.js' />"></script>
    </body>
</html>
