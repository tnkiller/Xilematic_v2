<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Thêm Cụm Rạp</title>
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
    <!-- Add Cụm Rạp Modal -->
    <div class="modal fade" id="addCumRapPopup" tabindex="-1" aria-labelledby="addCumRapLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content popup-content">
                <div class="modal-header">
                    <h2 class="modal-title" id="addCumRapLabel">Thêm Cụm Rạp Mới</h2>
                    <button type="button" class="btn-close close-btn" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="cumRapForm" action="sendTheaterServlet.jsp" method="POST" novalidate>
                        <div class="mb-3">
                            <label class="form-label">Mã Cụm Rạp:</label>
                            <input type="number" name="maCumRap" class="form-control" required>
                            <div class="error-message" id="maCumRapError">Vui lòng nhập Mã Cụm Rạp</div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Tên Cụm Rạp:</label>
                            <input type="text" name="tenCumRap" class="form-control" required>
                            <div class="error-message" id="tenCumRapError">Vui lòng nhập Tên Cụm Rạp</div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mã Hệ Thống Rạp:</label>
                            <input type="number" name="maHeThongRap" class="form-control" required>
                            <div class="error-message" id="maHeThongRapError">Vui lòng nhập Mã Hệ Thống Rạp</div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Địa Chỉ:</label>
                            <input type="text" name="diaChi" class="form-control" required>
                            <div class="error-message" id="diaChiError">Vui lòng nhập Địa Chỉ</div>
                        </div>
                        <input type="hidden" name="typeEdit" value="cumRap">
                        <input type="hidden" name="action" value="create">
                        
                        <button type="submit" class="btn btn-primary">Thêm Cụm Rạp</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('cumRapForm');
            const inputs = form.querySelectorAll('input[required]');
            
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