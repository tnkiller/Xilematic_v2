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
                            <h4 class="card-title mb-0">Update User Information</h4>
                        </div>
                        <div class="card-body">
                            <form action="<%=request.getContextPath()%>/intermediate.jsp" method="POST">
                                <input type="hidden" name="id" value="${user.id}"/>
                                <input type="hidden" name="password" value="${user.password}"/>
                                <input type="hidden" name="action" value="update_user"/>

                                <div class="mb-3">
                                    <label for="username" class="form-label">Username</label>
                                    <input type="text" class="form-control" value="${user.username}" id="username" name="username" required>
                                </div>

                                <div class="mb-3">
                                    <label for="fullname" class="form-label">Fullname</label>
                                    <input type="text" class="form-control" value="${user.fullname}" id="fullname" name="fullname" required>
                                </div>

                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" value="${user.email}" id="email" name="email" required>
                                </div>

                                <div class="mb-3">
                                    <label for="phoneNumber" class="form-label">Phone number</label>
                                    <input type="tel" class="form-control" value="${user.phoneNumber}" id="phoneNumber" name="phoneNumber" required>
                                </div>

                                <div class="mb-3">
                                    <label for="loai_nguoi_dung" class="form-label">Role</label>
                                    <select class="form-select" id="loai_nguoi_dung" name="typeOfUser" required>
                                        <option value="" disabled>Chọn loại người dùng</option>
                                        <c:choose>
                                            <c:when test="${user.typeOfUser eq 'admin'}">
                                                <option value="admin" selected="">Admin</option>
                                                <option value="user">User</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="admin" >Admin</option>
                                                <option value="user" selected="">User</option>
                                            </c:otherwise>
                                        </c:choose>
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