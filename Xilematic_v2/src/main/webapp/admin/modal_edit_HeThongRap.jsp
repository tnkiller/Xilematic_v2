<%@page import="constant.PageLink"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Quản lý hệ thống rạp</title>
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
      <style>
          body {
              background-color: #f8f9fa;
              padding-top: 20px;
          }

          .theater-form {
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

          .form-control {
              border-radius: 8px;
              padding: 12px 15px;
              border: 1px solid #ced4da;
              transition: all 0.3s;
          }

          .form-control:focus {
              border-color: #86b7fe;
              box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
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

          .btn-delete {
              background-color: #dc3545;
              border: none;
              padding: 12px;
              font-size: 18px;
              font-weight: 500;
              letter-spacing: 0.5px;
              transition: all 0.3s;
          }

          .btn-delete:hover {
              background-color: #c82333;
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
              <h2><i class="bi bi-building me-2"></i>Quản Lý Hệ Thống Rạp</h2>
              <a href="javascript:history.back()" class="btn btn-back">
                  <i class="bi bi-arrow-left me-2"></i>Quay lại
              </a>
          </div>

          <!-- Form cho Quản Lý Hệ Thống Rạp -->
          <form action="<%=request.getContextPath()%>/sendTheaterServlet.jsp" method="POST" class="theater-form">
              <input type="hidden" name="typeEdit" value="heThongRap"/>
              <input type="hidden" name="id" value="${heThongRap.maHeThongRap}"/>

              <div class="row g-3 mb-4">
                  <div class="col-md-6">
                      <label for="maHeThongRap" class="form-label">Mã Hệ Thống Rạp:</label>
                      <input type="number" class="form-control" id="maHeThongRap" name="maHeThongRap" 
                             value="${heThongRap.maHeThongRap}" required>
                  </div>

                  <div class="col-md-6">
                      <label for="tenHeThongRap" class="form-label">Tên Hệ Thống Rạp:</label>
                      <input type="text" class="form-control" id="tenHeThongRap" name="tenHeThongRap" 
                             value="${heThongRap.tenHeThongRap}" required>
                  </div>
              </div>

              <div class="mb-4">
                  <label for="logo" class="form-label">Logo (URL):</label>
                  <input type="url" class="form-control" id="logo" name="logo" 
                         value="${heThongRap.logo}" required>
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
          // Optional: Add any client-side validation or dynamic behavior here
          document.addEventListener('DOMContentLoaded', function() {
              const form = document.querySelector('form');
              form.addEventListener('submit', function(e) {
                  // Optional: Add form validation
                  const maHeThongRap = document.getElementById('maHeThongRap');
                  const tenHeThongRap = document.getElementById('tenHeThongRap');
                  const logo = document.getElementById('logo');

                  if (!maHeThongRap.value || !tenHeThongRap.value || !logo.value) {
                      e.preventDefault();
                      alert('Vui lòng điền đầy đủ thông tin');
                  }
              });
          });
      </script>
  </body>
</html>