<%@page import="constant.PageLink"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý Cụm Rạp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
</head>
<body>
    <div class="container">
        <div class="header-section">
            <h2><i class="bi bi-building-fill me-2"></i>Quản Lý Cụm Rạp</h2>
            <a href="javascript:history.back()" class="btn btn-back">
                <i class="bi bi-arrow-left me-2"></i>Quay lại
            </a>
        </div>

        <!-- Form cho Quản Lý Cụm Rạp -->
        <form action="<%=request.getContextPath()%>/sendTheaterServlet.jsp" method="POST" class="theater-form">
            <input type="hidden" name="typeEdit" value="cumRap"/>
            <input type="hidden" name="id" value="${cumRap.maCumRap}"/>

            <div class="row g-3 mb-4">
                <div class="col-md-6">
                    <label for="maCumRap" class="form-label">Mã Cụm Rạp:</label>
                    <input type="number" class="form-control" id="maCumRap" name="maCumRap" 
                           value="${cumRap.maCumRap}" required>
                </div>

                <div class="col-md-6">
                    <label for="tenCumRap" class="form-label">Tên Cụm Rạp:</label>
                    <input type="text" class="form-control" id="tenCumRap" name="tenCumRap" 
                           value="${cumRap.tenCumRap}" required>
                </div>
            </div>

            <div class="row g-3 mb-4">
                <div class="col-md-6">
                    <label for="diaChi" class="form-label">Địa Chỉ:</label>
                    <input type="text" class="form-control" id="diaChi" name="diaChi" 
                           value="${cumRap.diaChi}" required>
                </div>

                <div class="col-md-6">
                    <label for="maHeThongRap" class="form-label">Mã Hệ Thống Rạp:</label>
                    <input type="number" class="form-control" id="maHeThongRap" name="maHeThongRap" 
                           value="${cumRap.maHeThongRap}" required>
                </div>
            </div>

            <div class="row g-3">
                <div class="col-md-6">
                    <button type="submit" name="action" value="update" class="btn btn-update btn-lg w-100">
                        <i class="bi bi-save2 me-2"></i>Cập Nhật
                    </button>
                </div>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form');
            form.addEventListener('submit', function(e) {
                // Form validation
                const maCumRap = document.getElementById('maCumRap');
                const tenCumRap = document.getElementById('tenCumRap');
                const diaChi = document.getElementById('diaChi');
                const maHeThongRap = document.getElementById('maHeThongRap');

                if (!maCumRap.value || !tenCumRap.value || !diaChi.value || !maHeThongRap.value) {
                    e.preventDefault();
                    alert('Vui lòng điền đầy đủ thông tin');
                }
            });
        });
    </script>
</body>
</html>