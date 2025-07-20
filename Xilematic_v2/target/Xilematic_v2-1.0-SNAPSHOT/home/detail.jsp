
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${movie.movieName} - Chi tiết phim</title>
    <link rel="stylesheet" href="https://ionic.io/ionicons" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/detail.css" />
    
  </head>

  <body>
      <%@ include file="/components/header.jsp" %>
    <div class="detail">
      <div class="detail-content">
        <div class="detail-banner">
          <img
            src="${movie.image}"
            alt="${movie.movieName}"
          />
        </div>
        <div class="detail-information">
          <h2>${movie.movieName}</h2>
          <p class="movie-desc">
            ${movie.description != null ? movie.description : 'Không có mô tả chi tiết'}
          </p>
          <p class="movice-director">
            Đạo diễn: 
            <span>${movie.director != null ? movie.director : 'Chưa cập nhật'}</span>
          </p>
          <p class="movie-actors">
            Diễn viên chính: 
            <span>${movie.actor != null ? movie.actor : 'Chưa cập nhật'}</span>
          </p>
          <p class="movie-rating">
            Đánh giá: 
            <span>${movie.rate != null ? movie.rate : 'Chưa có'}/10</span>
          </p>
          <p class="movie-timeline">
            Ngày phát hành: 
            <span>
              <c:choose>
                <c:when test="${movie.releaseDate != null}">
                  ${movie.releaseDate}
                </c:when>
                <c:otherwise>Chưa xác định</c:otherwise>
              </c:choose>
            </span>
          </p>
          <p class="movie-status">
            Trạng thái: 
            <span>
              <c:choose>
                <c:when test="${movie.status}">Đang chiếu</c:when>
                <c:otherwise>Sắp chiếu</c:otherwise>
              </c:choose>
            </span>
          </p>
          <div class="btn-detail">
              <button id="playTrailerBtn">Xem Trailer
              <span>
                <ion-icon name="caret-forward-circle-outline"></ion-icon>
              </span></button>

            <div id="overlay" class="overlay">
              <div class="popup">
                <span id="closeBtn" class="close-btn">&times;</span>
                <iframe id="trailerIframe" src="${movie.trailer}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
              </div>
            </div>
            <button onclick="window.location.href='${pageContext.request.contextPath}/SelectCalendar?id=${movie.id}&movieName=${movie.movieName}'">
    Đặt vé
</button>

          </div>
        </div>
      </div>
    </div>

    <div id="comment-form">
      <h3>Viết bình luận</h3>
      <c:choose>
        <c:when test="${not empty sessionScope.userInfor}">
          <textarea
            id="comment-input"
            placeholder="Nhập bình luận..."
            rows="3"
            style="width: 100%"
          ></textarea>
          <button onclick="addComment(${movie.id})">Gửi bình luận</button>
        </c:when>
        <c:otherwise>
          <p>Vui lòng <a href="${pageContext.request.contextPath}/login.jsp">đăng nhập</a> để bình luận</p>
        </c:otherwise>
      </c:choose>
    </div>

    <div id="comments-container">
    <c:choose>
        <c:when test="${not empty comments}">
            <c:forEach var="comment" items="${comments}">
                <div class="comment">
                    <p>
                        <strong>${comment.tenNguoiDung}</strong> - 
                        ${comment.ngayTao}
                    </p>
                    <p>${comment.noiDung}</p>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>Chưa có bình luận nào</p>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/components/footer.jsp" %>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    <script src="${pageContext.request.contextPath}/script/detail.js"></script>
    <script >
        function bookTicket(id) {
        window.location.href = '${pageContext.request.contextPath}/SelectCalendar?id=' + movieId;
      }

     function addComment(movieId) {
    const commentInput = document.getElementById('comment-input');
    const commentContent = commentInput.value.trim();

    if (commentContent === '') {
        alert('Vui lòng nhập nội dung bình luận');
        return;
    }

    fetch('${pageContext.request.contextPath}/DetailServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            movieId: movieId,
            content: commentContent
        })
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw err; });
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            const commentsContainer = document.getElementById('comments-container');
            const newComment = document.createElement('div');
            newComment.classList.add('comment');
            
            // Sử dụng dữ liệu từ phản hồi của server
            newComment.innerHTML = `
                <p>
                    <strong>${data.username}</strong> - 
                    ${data.commentDate}
                </p>
                <p>${commentContent}</p>
            `;
            
            // Thêm comment mới vào đầu danh sách
            commentsContainer.insertBefore(newComment, commentsContainer.firstChild);
            
            // Xóa nội dung input
            commentInput.value = '';
        } else {
            alert(data.message || 'Gửi bình luận thất bại');
        }
    })
    .catch(error => {
        console.error('Lỗi:', error);
        alert(error.message || 'Đã xảy ra lỗi khi gửi bình luận');
    });
}

    </script>
  </body>
</html>
