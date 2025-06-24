<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Thêm Lịch Chiếu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error-message {
            color: red;
            font-size: 0.875rem;
            margin-top: 0.25rem;
            display: none;
        }
    </style>
</head>
<body>
    <!-- Add Lịch Chiếu Modal -->
    <div class="modal fade popup-overlay" id="addLichChieuPopup" tabindex="-1" aria-labelledby="addLichChieuLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content popup-content">
                <div class="modal-header">
                    <h2 class="modal-title" id="addLichChieuLabel">Thêm Lịch Chiếu Mới</h2>
                    <button type="button" class="btn-close close-btn" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="lichChieuForm" action="sendTheaterServlet.jsp" method="POST" novalidate>
                        <div class="mb-3">
                            <label class="form-label">Mã Rạp:</label>
                            <input type="number" name="maRap" class="form-control" required>
                            <div class="error-message" id="maRapError">Vui lòng nhập Mã Rạp</div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mã Phim:</label>
                            <input type="number" name="maPhim" class="form-control" required>
                            <div class="error-message" id="maPhimError">Vui lòng nhập Mã Phim</div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Ngày Giờ Chiếu:</label>
                            <input type="datetime-local" name="ngayGioChieu" class="form-control" required>
                            <div class="error-message" id="ngayGioChieuError">Vui lòng chọn Ngày Giờ Chiếu</div>
                        </div>
                        <input type="hidden" name="typeEdit" value="lichChieu">
                        <input type="hidden" name="action" value="create">
                        
                        <button type="submit" class="btn btn-primary">Thêm Lịch Chiếu</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('lichChieuForm');
            const inputs = form.querySelectorAll('input[required]');
            
            // Set minimum date to current date
            const today = new Date().toISOString().slice(0, 16);
            document.querySelector('input[name="ngayGioChieu"]').min = today;

            // Function to show error message
            function showError(input) {
                const errorElement = document.getElementById(`${input.name}Error`);
                errorElement.style.display = 'block';
                input.classList.add('is-invalid');
            }

            // Function to hide error message
            function hideError(input) {
                const errorElement = document.getElementById(`${input.name}Error`);
                errorElement.style.display = 'none';
                input.classList.remove('is-invalid');
            }

            // Real-time validation for each input
            inputs.forEach(input => {
                input.addEventListener('input', function() {
                    if (this.value.trim() === '') {
                        showError(this);
                    } else {
                        hideError(this);
                    }
                });
            });

            // Form submission validation
            form.addEventListener('submit', function(e) {
                let isValid = true;

                inputs.forEach(input => {
                    if (input.value.trim() === '') {
                        showError(input);
                        isValid = false;
                    } else {
                        hideError(input);
                    }
                });

                if (!isValid) {
                    e.preventDefault();
                }
            });
        });
    </script>
</body>
</html>
