<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${movie.movieName} - Chi tiết phim</title>
    <link rel="stylesheet" href="https://ionic.io/ionicons" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detail.css" />
    <style>
        body {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  font-family: Arial, Helvetica, sans-serif;
}
.detail {
  margin-bottom: 40px;
}
.detail-content {
  display: flex;
  padding: 30px 50px;
}
.detail-banner {
  margin-right: 20px;
}
.detail-banner img {
  height: 100%;
  width: 350px;
  margin-right: 50px;
}

.detail-information {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.detail-information p,
h2 {
  margin: 0;
}

.detail-information h2 {
  font-size: 40px;
}
.detail-information p {
  font-size: 20px;
}
.btn-detail button {
  background-color: #c62828;
  border: none;
  color: white;
  padding: 15px 30px;
  cursor: pointer;
  font-size: 20px;
  transition: all 0.5s;
}
.btn-detail button:hover {
  transform: translateY(-2px);
}

.btn-detail button:first-of-type {
  background-color: rgb(19, 18, 18);
  margin-right: 20px;
}
#comment-form {
  margin-top: 20px;
  padding: 15px;
  background-color: #f7f7f7;
  border-radius: 10px;
}

#comment-form button {
  margin-top: 10px;
  padding: 6px 12px;
  background-color: black;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 6px;
}

.user-comment {
  margin-top: 10px;
}
#toggle-comments {
  background-color: #000; /* màu nền đen */
  color: #fff; /* chữ trắng */
  padding: 5px 15px; /* khoảng cách bên trong */
  font-size: 15px; /* cỡ chữ */
  font-weight: 600; /* chữ đậm */
  border: none; /* bỏ viền */
  border-radius: 8px; /* bo góc */
  cursor: pointer; /* đổi con trỏ khi hover */
  transition: all 0.3s ease; /* hiệu ứng mượt */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* đổ bóng */
  margin-right: 10px; /* khoảng cách bên trái */
  margin-top: 10px; /* khoảng cách bên trên */
}

#toggle-comments:hover {
  background-color: #333; /* khi hover chuyển sang màu xám đậm hơn */
  transform: scale(1.05); /* hiệu ứng phóng to nhẹ */
}

.heart-button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 18px; /* hoặc to hơn nếu cần */
  padding: 0;
  margin: 0;
  color: red;
  transition: transform 0.2s ease;
}

.heart-button:hover {
  transform: scale(1.2);
}
      /* Màn mờ phía sau */
.overlay {
  display: none;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7); /* Làm mờ các yếu tố phía sau */
  justify-content: center;
  align-items: center;
}

/* Popup chứa video */
.popup {
  background-color: white;
  position: relative;
  width: 80%;
  max-width: 800px;
  padding: 20px;
}

#trailerIframe {
  width: 100%;
  height: 450px;
  border: none;
}

/* Nút đóng */
.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 30px;
  color: black;
  cursor: pointer;
  background-color: transparent;
  border: none;
}

button {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  cursor: pointer;
} 
    </style>
  </head>

  <body>
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
            <button onclick="bookTicket(${movie.id})">Đặt vé</button>
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


    <button id="toggle-comments" onclick="toggleCommentSection()">Ẩn bình luận</button>

    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    <script>
      function playTrailer(trailer) {
        if (trailer) {
          window.open(trailer, '_blank');
        } else {
          alert('Trailer chưa được cập nhật');
        }
      }

      function bookTicket(id) {
        window.location.href = '${pageContext.request.contextPath}/booking?movieId=' + movieId;
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

function toggleCommentSection() {
    const commentsContainer = document.getElementById('comments-container');
    const toggleButton = document.getElementById('toggle-comments');
    
    // Sửa logic ẩn/hiện comments
    if (commentsContainer.classList.contains('hidden')) {
        commentsContainer.classList.remove('hidden');
        toggleButton.textContent = 'Ẩn bình luận';
    } else {
        commentsContainer.classList.add('hidden');
        toggleButton.textContent = 'Hiện bình luận';
    }
}
document.getElementById("playTrailerBtn").addEventListener("click", function() {
  // Chỉ định URL của video YouTube (thay thế bằng link video của bạn)
  var videoURL = "https://www.youtube.com/embed/dQw4w9WgXcQ"; // Thay đổi URL này

  // Hiển thị overlay và iframe
  document.getElementById("overlay").style.display = "flex";
  document.getElementById("trailerIframe").src = videoURL;
});

document.getElementById("closeBtn").addEventListener("click", function() {
  // Ẩn overlay khi đóng
  document.getElementById("overlay").style.display = "none";
  document.getElementById("trailerIframe").src = ""; // Dừng video khi đóng
});


    </script>
  </body>
</html>
