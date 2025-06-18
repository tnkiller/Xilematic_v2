<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <<link rel="stylesheet" href="${pageContext.request.contextPath}/style/"/>
        <title>JSP Page</title>
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
            }

            textarea {
                resize: vertical;
                min-height: 100px;
            }

            input[type="checkbox"] {
                margin-left: 10px;
            }

            /* Buttons Styling */
            .func-btn {
                display: flex;
                justify-content: space-between;
                gap: 10px;
            }

            button {
                padding: 10px 20px;
                font-size: 14px;
                cursor: pointer;
                border: none;
                border-radius: 5px;
                width: 48%;
            }

            .update-btn {
                background-color: #4CAF50;
                color: white;
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
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Chi tiết phim</h2>
            <form action="<%=request.getContextPath()%>/movies" method="POST" class="form-row-btn">
                <input type="hidden" name="id" value="${movie.id}"/>
                <label>Tên phim:</label>
                <input type="text" name="title" value="${movie.movieName}" required="">

                <label>Trailer (URL):</label>
                <input type="url" name="trailer" value="${movie.trailer}" required>

                <label>Ảnh đại diện (image):</label>
                <input type="url" name="image" value="${movie.image}" required>

                <label>Mô tả (description):</label>
                <textarea name="description">${movie.description}</textarea>

                <label>Ngày phát hành (release date):</label>
                <input type="date" name="releaseDate" id="releaseDate" value="${movie.releaseDate}" required>

                <label>Đánh giá (rate):</label>
                <input type="number" name="rate" min="0" max="10" step="1" value="${movie.rate}" required placeholder="Nhập số từ 0 tới 10">

                <label>Trạng thái (status):</label>
                <select name="status" required>
                    <c:if test="${movie.status == 'true'}">
                        <option value="true" selected="">Đang chiếu</option>
                        <option value="false">Sắp chiếu</option>
                    </c:if> 
                    <c:if test="${movie.status == 'false'}">
                        <option value="true">Đang chiếu</option>
                        <option value="false" selected="">Sắp chiếu</option>
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

                <div class="func-btn">
                    <button type="submit" name="action" value="update" class="update-btn">Cập nhật</button>
                    <button type="submit" name="action" value="delete" class="delete-btn">Xóa</button>
                </div>
            </form>
        </div>
        <!--        <script>
                    // Lấy ngày hôm nay theo định dạng yyyy-mm-dd
                    const today = new Date().toISOString().split('T')[0];
                    document.getElementById('releaseDate').setAttribute('min', today);
                    document.getElementById('releaseDate').value = today;
                </script>-->
    </body>
</html>
