<%@page import="constant.PageLink"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Quản lý phim</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
        <style>
            body {
                background-color: #f8f9fa;
                padding-top: 20px;
            }

            .movie-form {
                background: white;
                border-radius: 10px;
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.1);
                padding: 30px;
                margin-bottom: 30px;
            }

            .form-label {
                font-weight: 500;
                color: #495057;
            }

            .form-control, .form-select {
                border-radius: 8px;
                padding: 12px 15px;
                border: 1px solid #ced4da;
                transition: all 0.3s;
            }

            .form-control:focus, .form-select:focus {
                border-color: #86b7fe;
                box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
            }

            textarea.form-control {
                min-height: 150px;
                resize: vertical;
            }

            .btn-update {
                background-color: #28a745;
                border: none;
                padding: 12px;
                font-size: 18px;
                font-weight: 500;
                letter-spacing: 0.5px;
                transition: all 0.3s;
            }

            .btn-update:hover {
                background-color: #218838;
                transform: translateY(-2px);
            }

            .btn-back {
                background-color: #6c757d;
                border: none;
                padding: 10px 20px;
                font-weight: 500;
                transition: all 0.3s;
            }

            .btn-back:hover {
                background-color: #5a6268;
                transform: translateY(-2px);
            }

            .form-check-input {
                width: 20px;
                height: 20px;
                margin-left: 10px;
            }

            .form-check-input:checked {
                background-color: #28a745;
                border-color: #28a745;
            }

            .header-section {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 25px;
            }

            .header-section h2 {
                color: #343a40;
                font-weight: 600;
                margin: 0;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header-section">
                <h2><i class="bi bi-film me-2"></i>Chi tiết phim</h2>
                <a href="javascript:history.back()" class="btn btn-back">
                    <i class="bi bi-arrow-left me-2"></i>Quay lại
                </a>
            </div>

            <!-- Form cho Update -->
            <form action="<%=request.getContextPath() + PageLink.INTERMEDIATE_PAGE%>" method="POST" class="movie-form">
                <input type="hidden" name="id" value="${movie.id}"/>
                <input type="hidden" name="action" value="update_movie"/>

                <div class="row g-3 mb-4">
                    <div class="col-md-6">
                        <label for="movieName" class="form-label">Tên phim:</label>
                        <input type="text" class="form-control" id="movieName" name="movieName" value="${movie.movieName}" required>
                    </div>

                    <div class="col-md-6">
                        <label for="trailer" class="form-label">Trailer (URL):</label>
                        <input type="url" class="form-control" id="trailer" name="trailer" value="${movie.trailer}" required>
                    </div>
                </div>

                <div class="row g-3 mb-4">
                    <div class="col-md-6">
                        <label for="image" class="form-label">Ảnh đại diện (URL):</label>
                        <input type="url" class="form-control" id="image" name="image" value="${movie.image}" required>
                    </div>

                    <div class="col-md-6">
                        <label for="releaseDate" class="form-label">Ngày phát hành:</label>
                        <input type="date" class="form-control" id="releaseDate" name="releaseDate" value="${movie.releaseDate}" required>
                    </div>
                </div>

                <div class="mb-4">
                    <label for="description" class="form-label">Mô tả:</label>
                    <textarea class="form-control" id="description" name="description" required>${movie.description}</textarea>
                </div>

                <div class="row g-3 mb-4">
                    <div class="col-md-3">
                        <label for="rate" class="form-label">Đánh giá (0-10):</label>
                        <input type="number" class="form-control" id="rate" name="rate" min="0" max="10" step="1" value="${movie.rate}" required>
                    </div>

                    <div class="col-md-3">
                        <label for="status" class="form-label">Trạng thái:</label>
                        <select class="form-select" id="status" name="status" required>
                            <c:if test="${movie.status == 'true'}">
                                <option value="true" selected>Đang chiếu</option>
                                <option value="false">Sắp chiếu</option>
                            </c:if> 
                            <c:if test="${movie.status == 'false'}">
                                <option value="true">Đang chiếu</option>
                                <option value="false" selected>Sắp chiếu</option>
                            </c:if> 
                        </select>
                    </div>

                    <div class="col-md-3">
                        <label class="form-label">Phim nổi bật:</label>
                        <div class="d-flex align-items-center" style="height: 100%;">
                            <div class="form-check form-switch">
                                <c:choose>
                                    <c:when test="${movie.hot == 'true'}">
                                        <input class="form-check-input" type="checkbox" role="switch" id="hot" name="hot" checked>
                                    </c:when>
                                    <c:otherwise>
                                        <input class="form-check-input" type="checkbox" role="switch" id="hot" name="hot">
                                    </c:otherwise>
                                </c:choose>
                                <label class="form-check-label ms-2" for="hot">Hot</label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row g-3 mb-4">
                    <div class="col-md-6">
                        <label for="actor" class="form-label">Diễn viên:</label>
                        <input type="text" class="form-control" id="actor" name="actor" value="${movie.actor}" required>
                    </div>

                    <div class="col-md-6">
                        <label for="director" class="form-label">Đạo diễn:</label>
                        <input type="text" class="form-control" id="director" name="director" value="${movie.director}" required>
                    </div>
                </div>

                <button type="submit" class="btn btn-update btn-lg w-100 mt-3">
                    <i class="bi bi-save2 me-2"></i>Cập nhật phim
                </button>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Lấy ngày hôm nay theo định dạng yyyy-mm-dd
            const today = new Date().toISOString().split('T')[0];
            document.getElementById('releaseDate').setAttribute('min', today);
            // Chỉ đặt giá trị mặc định nếu chưa có giá trị
            if (!document.getElementById('releaseDate').value) {
                document.getElementById('releaseDate').value = today;
            }
        </script>
    </body>
</html>