<%@page import="constant.PageLink"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Quản lý người dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    </head>
    <body>
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                            <h4 class="card-title mb-0">Cập nhật thông tin Ghế</h4>
                        </div>
                        <div class="card-body">
                            <form action="<%=request.getContextPath()%>/intermediate.jsp" method="POST">
                                <input type="hidden" name="action" value="update_ghe"/>
                                <input type="hidden" name="maGhe" value="${ghe.ma_ghe}"/>
                                <input type="hidden" name="maRap" value="${maRap}"/>
                                <input type="hidden" name="page" value="${currentPage}"/>
                                <div class="mb-3">
                                    <label for="maGhe" class="form-label">Mã ghế</label>
                                    <input type="number" class="form-control" value="${ghe.ma_ghe}" id="maGhe" disabled>
                                </div>
                                <div class="mb-3">
                                    <label for="tenGhe" class="form-label">Tên ghế</label>
                                    <input type="text" class="form-control" value="${ghe.ten_ghe}" id="tenGhe" name="tenGhe" required>
                                </div>
                                <div class="mb-3">
                                    <label for="loaiGhe" class="form-label">Loại ghế</label>
                                    <select class="form-select" id="loaiGhe" name="loaiGhe" required>
                                        <option value="" disabled>Chọn loại ghế</option>
                                        <option value="VIP" ${ghe.loai_ghe == 'VIP' ? 'selected' : ''}>VIP</option>
                                        <option value="Thường" ${ghe.loai_ghe == 'Thường' ? 'selected' : ''}>Thường</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="trangThai" class="form-label">Trạng thái</label>
                                    <select class="form-select" id="trangThai" name="trangThai" required>
                                        <option value="" disabled>Chọn trạng thái</option>
                                        <option value="active" ${ghe.trang_thai == 'active' ? 'selected' : ''}>active</option>
                                        <option value="n/a" ${ghe.trang_thai == 'n/a' ? 'selected' : ''}>n/a</option>
                                    </select>
                                </div>
                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <a href="javascript:history.back()" class="btn btn-secondary me-md-2">
                                        <i class="bi bi-arrow-left"></i> Back
                                    </a>
                                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>