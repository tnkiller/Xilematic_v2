<%@page import="constant.PageLink"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý phim</title>
        <style>
            /* General Body Styling */
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }

            /* Content Container */
            .container {
                padding: 30px;
                margin: 0 auto;
                max-width: 900px;
            }

            /* Form Styling */
            form {
                display: flex;
                flex-direction: column;
                gap: 15px;
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            label {
                font-size: 14px;
                color: #555;
            }

            input[type="text"],
            input[type="url"],
            input[type="date"],
            input[type="number"],
            textarea,
            select {
                padding: 10px;
                margin: 5px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
                width: 100%;
                box-sizing: border-box;
            }

            textarea {
                resize: vertical;
                min-height: 100px;
            }

            input[type="checkbox"] {
                margin-left: 10px;
            }

            /* Buttons Styling */
            .func-btn-container {
                display: flex;
                justify-content: space-between;
                gap: 10px;
            }

            .func-btn {
                flex: 1;
            }

            button {
                padding: 10px 20px;
                font-size: 14px;
                cursor: pointer;
                border: none;
                border-radius: 5px;
                width: 100%;
            }

            .update-btn {
                background-color: #4CAF50;
                color: white;
                font-size: 30px;
            }

            .delete-btn {
                background-color: #f44336;
                color: white;
            }

            button:hover {
                opacity: 0.8;
            }

            /* Close Button Styling */
            .close-btn {
                font-size: 20px;
                color: #333;
                cursor: pointer;
            }

            /* Form Delete Styling */
            form.delete-form {
                padding: 0;
                background: none;
                box-shadow: none;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Chi tiết phim</h2>
            <!-- Form cho Update -->
            <form action="<%=request.getContextPath()%>/movies" method="POST" class="form-row-btn">
                <input type="hidden" name="id" value="${movie.id}"/>
                <input type="hidden" name="action" value="update"/>

                <label>Tên phim:</label>
                <input type="text" name="movieName" value="${movie.movieName}" required>

                <label>Trailer (URL):</label>
                <input type="url" name="trailer" value="${movie.trailer}" required>

                <label>Ảnh đại diện (image):</label>
                <input type="url" name="image" value="${movie.image}" required>

                <label>Mô tả (description):</label>
                <textarea name="description" required>${movie.description}</textarea>

                <label>Ngày phát hành (release date):</label>
                <input type="date" name="releaseDate" id="releaseDate" value="${movie.releaseDate}" required>

                <label>Đánh giá (rate):</label>
                <input type="number" name="rate" min="0" max="10" step="1" value="${movie.rate}" required placeholder="Nhập số từ 0 tới 10">

                <label>Trạng thái (status):</label>
                <select name="status" required>
                    <c:if test="${movie.status == 'true'}">
                        <option value="true" selected>Đang chiếu</option>
                        <option value="false">Sắp chiếu</option>
                    </c:if> 
                    <c:if test="${movie.status == 'false'}">
                        <option value="true">Đang chiếu</option>
                        <option value="false" selected>Sắp chiếu</option>
                    </c:if> 
                </select>

                <label>
                    Hot (phim nổi bật)
                    <c:choose>
                        <c:when test="${movie.hot == 'true'}">
                            <input type="checkbox" name="hot" checked>
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="hot">
                        </c:otherwise>
                    </c:choose>
                </label>

                <label>Diễn viên (actor):</label>
                <input type="text" name="actor" value="${movie.actor}" required>

                <label>Đạo diễn (director):</label>
                <input type="text" name="director" value="${movie.director}" required>
                <button type="submit" class="update-btn">Update</button>
            </form>
        </div>
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