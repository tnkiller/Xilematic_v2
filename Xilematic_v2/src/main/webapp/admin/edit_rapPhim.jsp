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
                            <h4 class="card-title mb-0">Cập nhật thông tin Rạp Phim</h4>
                        </div>
                        <div class="card-body">
                            <form action="<%=request.getContextPath()%>/intermediate.jsp" method="POST">
                                <input type="hidden" name="action" value="update_rapPhim"/>
                                <input type="hidden" name="maRap" value="${rap.maRap}" />
                                <div class="mb-3">
                                    <label for="maRap" class="form-label" >Mã rạp</label>
                                    <input type="number" disabled class="form-control" value="${rap.maRap}" id="maRap" name="maRap" disabled>
                                </div>

                                <div class="mb-3">
                                    <label for="tenRap" class="form-label">Tên rạp</label>
                                    <input type="text" class="form-control" value="${rap.tenRap}" id="tenRap" name="tenRap" required>
                                </div>

                                <div class="mb-3">
                                    <label for="maCumRap" class="form-label">Cụm Rạp</label>
                                    <select class="form-select" id="cumRap" name="maCumRap" required>
                                        <option value="" disabled>Chọn cụm rạp</option>
                                        <c:forEach var="cumRap" items="${cumRaps}">
                                            <option value="${cumRap.maCumRap}"
                                                    <c:if test="${cumRap.maCumRap == rap.maCumRap}">selected</c:if>>
                                                ${cumRap.tenCumRap}
                                            </option>
                                        </c:forEach>
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