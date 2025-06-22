<%-- 
    Document   : modal
    Created on : Jun 22, 2025, 2:00:53 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel"><%= request.getParameter("title")%></h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!--bat dau form-->
                        <form action="<%=request.getContextPath()%>/intermediate.jsp" method="POST" id="myForm">
                            <!--thuoc tinh an gui kem-->
                            <input type="hidden" name="id"/>
                            <input type="hidden" name="action" value="add_movie"/>



                            <!--thuoc tinh hien de nguoi dung nhap-->
                            <!--movie_nam-->
                            <div class="mb-3">
                                <label for="movie-name" class="col-form-label">Tên phim:</label>
                                <input type="text" name="movieName" class="form-control" id="movie-name" required>
                            </div>
                            <!--trailer-->
                            <div class="mb-3">
                                <label for="trailer" class="col-form-label">Trailer (URL):</label>
                                <input type="url" name="trailer"  class="form-control" id="trailer" required>
                            </div>
                            <!--image-->
                            <div class="mb-3">
                                <label for="image" class="col-form-label">Ảnh đại diện (image):</label>
                                <input type="url" name="image"  class="form-control" id="image" required>
                            </div>
                            <!--description-->
                            <div class="mb-3">
                                <label for="description" class="col-form-label">Mô tả (description):</label>
                                <textarea name="description" required class="form-control" id="description"></textarea>
                            </div>
                            <!--release date-->
                            <div class="mb-3">
                                <label or="releaseDate" class="col-form-label">Ngày phát hành (release date):</label>
                                <input type="date" name="releaseDate" id="releaseDate" required class="form-control">
                            </div>
                            <!--date-->
                            <div class="mb-3">
                                <label for="rate" class="col-form-label">Đánh giá (rate):</label>
                                <input type="number" name="rate" min="0" max="10" step="1"  required class="form-control" id="releaseDate"  placeholder="Nhập số từ 0 tới 10">
                            </div>
                            <!--status-->
                            <div class="mb-3">
                                <label for="status" class="col-form-label">Trạng thái (status):</label>
                                <select name="status" required class="form-control" id="status">
                                    <option value="true">Đang chiếu</option>
                                    <option value="false">Sắp chiếu</option>
                                </select>
                            </div>
                            <!--hot-->
                            <div class="mb-3">
                                <div class="form-check">
                                    <input class="form-check-input" id="hot" type="checkbox" name="hot" class="form-control" id="hot">
                                    <label for="hot" class="form-check-label">
                                        Hot (phim nổi bật)
                                    </label>
                                </div>
                            </div>
                            <!--actor-->
                            <div class="mb-3">
                                <label for="actor" class="col-form-label">Diễn viên (actor):</label>
                                <input type="text" name="actor" required class="form-control" id="actor">
                            </div>
                            <!--director-->
                            <div class="mb-3">
                                <label for="director" class="col-form-label">Đạo diễn (director):</label>
                                <input type="text" name="director" required class="form-control" id="director">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" form="myForm" class="btn btn-primary"><%= request.getParameter("action")%></button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
