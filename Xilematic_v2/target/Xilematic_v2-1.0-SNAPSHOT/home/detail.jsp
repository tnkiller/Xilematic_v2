<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>${movie.movieName} - Chi tiết phim</title>
        <link rel="icon" type="image/gif" href="asset/image/AnimatedLogo.gif" />
        <!-- Modern CSS Frameworks -->
        <link href="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/css/splide.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome 6 -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <!-- Modern Icons -->
        <link rel="stylesheet" href="https://unpkg.com/@phosphor-icons/web@2.0.3/src/bold/style.css" />
        <!-- Custom CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/detail.css" />
        <style>
            :root {
                --primary: #6e44ff;
                --secondary: #ff4d8d;
                --dark: #0f0f1a;
                --light: #f8f9ff;
                --gradient: linear-gradient(135deg, var(--primary), var(--secondary));
            }

            body {
                background-color: var(--dark);
                color: var(--light);
                font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
                line-height: 1.6;
            }

            .movie-hero {
                position: relative;
                height: 70vh;
                overflow: hidden;
                border-radius: 0 0 2rem 2rem;
                box-shadow: 0 20px 50px rgba(0, 0, 0, 0.3);
                margin-bottom: 3rem;
            }

            .movie-hero::before {
                content: '';
                position: absolute;
                inset: 0;
                background: linear-gradient(to top, var(--dark) 0%, transparent 50%);
                z-index: 1;
            }

            .movie-hero img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                filter: brightness(0.7);
            }

            .movie-content {
                position: relative;
                z-index: 2;
                margin-top: -150px;
                padding: 0 2rem;
            }

            .movie-poster {
                width: 220px;
                height: 330px;
                border-radius: 1rem;
                box-shadow: 0 15px 30px rgba(0, 0, 0, 0.5);
                transform: translateY(-50px);
                border: 3px solid rgba(255, 255, 255, 0.1);
                transition: all 0.3s ease;
            }

            .movie-poster:hover {
                transform: translateY(-50px) scale(1.03);
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.6);
            }

            .movie-title {
                font-size: 3rem;
                font-weight: 800;
                background: var(--gradient);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                margin-bottom: 1rem;
            }

            .movie-meta {
                display: flex;
                gap: 1.5rem;
                margin-bottom: 1.5rem;
                flex-wrap: wrap;
            }

            .meta-item {
                display: flex;
                align-items: center;
                gap: 0.5rem;
                font-size: 0.9rem;
            }

            .meta-item i {
                color: var(--primary);
            }

            .movie-actions {
                display: flex;
                gap: 1rem;
                margin: 2rem 0;
            }

            .btn-trailer,
            .btn-book {
                padding: 0.8rem 1.5rem;
                border-radius: 50px;
                font-weight: 600;
                display: flex;
                align-items: center;
                gap: 0.5rem;
                transition: all 0.3s ease;
            }

            .btn-trailer {
                background: rgba(255, 255, 255, 0.1);
                backdrop-filter: blur(10px);
                border: 1px solid rgba(255, 255, 255, 0.2);
                color: white;
            }

            .btn-trailer:hover {
                background: rgba(255, 255, 255, 0.2);
                transform: translateY(-2px);
            }

            .btn-book {
                background: var(--gradient);
                color: white;
                border: none;
            }

            .btn-book:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 20px rgba(110, 68, 255, 0.3);
            }

            .movie-description {
                max-width: 800px;
                line-height: 1.8;
                opacity: 0.9;
                margin-bottom: 2rem;
            }

            .section-title {
                font-size: 1.5rem;
                font-weight: 700;
                margin: 2rem 0 1rem;
                position: relative;
                display: inline-block;
            }

            .section-title::after {
                content: '';
                position: absolute;
                bottom: -5px;
                left: 0;
                width: 50px;
                height: 3px;
                background: var(--gradient);
                border-radius: 3px;
            }

            .comment-section {
                background: rgba(15, 15, 26, 0.7);
                backdrop-filter: blur(10px);
                border-radius: 1rem;
                padding: 2rem;
                margin: 3rem 0;
                border: 1px solid rgba(255, 255, 255, 0.05);
            }

            .comment-form {
                margin-bottom: 2rem;
            }

            .comment-input {
                width: 100%;
                background: rgba(255, 255, 255, 0.05);
                border: 1px solid rgba(255, 255, 255, 0.1);
                border-radius: 12px;
                padding: 1rem;
                color: white;
                margin-bottom: 1rem;
                resize: none;
                transition: all 0.3s ease;
            }

            .comment-input:focus {
                outline: none;
                border-color: var(--primary);
                background: rgba(110, 68, 255, 0.05);
            }

            .btn-comment {
                background: var(--gradient);
                color: white;
                border: none;
                padding: 0.7rem 1.5rem;
                border-radius: 50px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .btn-comment:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(110, 68, 255, 0.3);
            }

            .comment-list {
                display: flex;
                flex-direction: column;
                gap: 1.5rem;
            }

            .comment-card {
                background: rgba(255, 255, 255, 0.03);
                border-radius: 12px;
                padding: 1.5rem;
                border: 1px solid rgba(255, 255, 255, 0.05);
                transition: all 0.3s ease;
            }

            .comment-card:hover {
                background: rgba(255, 255, 255, 0.05);
                transform: translateY(-2px);
            }

            .comment-header {
                display: flex;
                align-items: center;
                gap: 1rem;
                margin-bottom: 0.5rem;
            }

            .comment-avatar {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                background: var(--gradient);
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-weight: bold;
            }

            .comment-user {
                font-weight: 600;
                font-size: 1rem;
            }

            .comment-date {
                font-size: 0.8rem;
                opacity: 0.6;
                margin-left: 0.5rem;
            }

            .comment-content {
                line-height: 1.6;
                padding-left: 55px;
            }

            .login-prompt {
                text-align: center;
                padding: 1.5rem;
                background: rgba(255, 255, 255, 0.03);
                border-radius: 12px;
                border: 1px dashed rgba(255, 255, 255, 0.1);
            }

            .login-link {
                color: var(--primary);
                text-decoration: none;
                font-weight: 600;
                transition: all 0.3s ease;
            }

            .login-link:hover {
                color: var(--secondary);
                text-decoration: underline;
            }

            /* Trailer Modal */
            .trailer-modal {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.8);
                backdrop-filter: blur(10px);
                display: flex;
                align-items: center;
                justify-content: center;
                z-index: 1000;
                opacity: 0;
                visibility: hidden;
                transition: all 0.3s ease;
            }

            .trailer-modal.active {
                opacity: 1;
                visibility: visible;
            }

            .modal-content {
                width: 80%;
                max-width: 1000px;
                position: relative;
            }

            .close-modal {
                position: absolute;
                top: -40px;
                right: 0;
                color: white;
                font-size: 2rem;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .close-modal:hover {
                color: var(--secondary);
                transform: rotate(90deg);
            }

            .trailer-iframe {
                width: 100%;
                height: 0;
                padding-bottom: 56.25%;
                position: relative;
                border-radius: 1rem;
                overflow: hidden;
            }

            .trailer-iframe iframe {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                border: none;
            }

            .fav {
                font-weight: 100;
                cursor: pointer;
                font-size: 30px;
                position: relative;
                top: 12px;
                cursor: pointer;
                transition: all 0.25s ease-in-out;
            }

            .fav:hover {
                color: #ff4d8d;
                transform: scale(1.1);
            }

            .fav:active {
                font-weight: 900;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .movie-hero {
                    height: 50vh;
                }

                .movie-poster {
                    width: 160px;
                    height: 240px;
                    margin: 0 auto;
                    transform: translateY(-30px);
                }

                .movie-title {
                    font-size: 2rem;
                    text-align: center;
                }

                .movie-meta {
                    justify-content: center;
                }

                .movie-actions {
                    justify-content: center;
                }

                .movie-description {
                    text-align: center;
                }

                .modal-content {
                    width: 95%;
                }
            }
        </style>
    </head>

    <body>
        <jsp:include page="${request.getContextPath()}/components/header.jsp" />

        <div class="movie-hero">
            <img src="${movie.image}" alt="${movie.movieName}" />
        </div>

        <div class="container movie-content">
            <div class="row">
                <div class="col-md-3">
                    <img src="${movie.image}" class="movie-poster" alt="${movie.movieName}">
                </div>
                <div class="col-md-9">
                    <h1 class="movie-title">${movie.movieName}</h1>

                    <div class="movie-meta">
                        <div class="meta-item">
                            <i class="ph-bold ph-star"></i>
                            <span>${movie.rate != null ? movie.rate : 'N/A'}/10</span>
                        </div>
                        <div class="meta-item">
                            <i class="ph-bold ph-calendar"></i>
                            <span>
                                <c:choose>
                                    <c:when test="${movie.releaseDate != null}">
                                        ${movie.releaseDate}
                                    </c:when>
                                    <c:otherwise>Chưa xác định</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        <div class="meta-item">
                            <i class="ph-bold ph-ticket"></i>
                            <span>
                                <c:choose>
                                    <c:when test="${movie.status}">Đang chiếu</c:when>
                                    <c:otherwise>Sắp chiếu</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                    </div>

                    <div class="movie-actions">
                        <button class="btn-trailer" id="playTrailerBtn">
                            <i class="ph-bold ph-play-circle"></i> Xem Trailer
                        </button>
                        <button class="btn-book"
                                onclick="window.location.href = '${pageContext.request.contextPath}/SelectCalendar?id=${movie.id}&movieName=${movie.movieName}'">
                            <i class="ph-bold ph-ticket"></i> Đặt vé ngay
                            <input type="hidden" id="movieId" value="${movie.id}" />
                        </button>
                        <c:if test="${not empty sessionScope.userInfor}">
                            <i class="fa-solid fa-heart fav" id="fav-btn"></i>
                            <input type="hidden" id="isFavorited" value="${requestScope.isFavorited}" />
                        </c:if>
                    </div>

                    <p class="movie-description">
                        ${movie.description != null ? movie.description : 'Không có mô tả chi tiết'}
                    </p>

                    <div class="movie-details">
                        <div class="row">
                            <div class="col-md-6">
                                <p><strong><i class="ph-bold ph-user"></i> Đạo diễn:</strong>
                                    <span>${movie.director != null ? movie.director : 'Chưa cập nhật'}</span>
                                </p>
                            </div>
                            <div class="col-md-6">
                                <p><strong><i class="ph-bold ph-users"></i> Diễn viên:</strong>
                                    <span>${movie.actor != null ? movie.actor : 'Chưa cập nhật'}</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="comment-section">
                <h3 class="section-title">Bình luận</h3>

                <div class="comment-form">
                    <c:choose>
                        <c:when test="${not empty sessionScope.userInfor}">
                            <textarea class="comment-input" id="comment-input"
                                      placeholder="Chia sẻ cảm nhận của bạn về phim..."></textarea>
                            <button class="btn-comment" onclick="addComment(${movie.id})">
                                <i class="ph-bold ph-paper-plane-right"></i> Gửi bình luận
                            </button>
                        </c:when>
                        <c:otherwise>
                            <div class="login-prompt">
                                <p>Vui lòng <a href="${pageContext.request.contextPath}/login.jsp"
                                               class="login-link">đăng nhập</a> để bình luận</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="comment-list" id="comments-container">
                    <c:choose>
                        <c:when test="${not empty comments}">
                            <c:forEach var="comment" items="${comments}">
                                <div class="comment-card">
                                    <div class="comment-header">
                                        <div class="comment-avatar">${comment.tenNguoiDung.charAt(0)}</div>
                                        <div>
                                            <span class="comment-user">${comment.tenNguoiDung}</span>
                                            <span class="comment-date">${comment.ngayTao}</span>
                                        </div>
                                    </div>
                                    <div class="comment-content">
                                        <p>${comment.noiDung}</p>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="comment-card">
                                <p>Chưa có bình luận nào. Hãy là người đầu tiên bình luận!</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Trailer Modal -->
        <div class="trailer-modal" id="trailerModal">
            <div class="modal-content">
                <span class="close-modal" id="closeModal">&times;</span>
                <div class="trailer-iframe">
                    <iframe id="trailerIframe" src="${movie.trailer}"
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                            allowfullscreen></iframe>
                </div>
            </div>
        </div>

        <%@ include file="/components/footer.jsp" %>

        <!-- Modern JavaScript Libraries -->
        <script src="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/js/splide.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                document.addEventListener('DOMContentLoaded', () => {
                                    const isFavorited = document.getElementById('isFavorited');
                                    if (isFavorited.value === 'true') {
                                        const favBtn = document.getElementById('fav-btn');
                                        favBtn.style.fontWeight = '900';
                                        favBtn.style.color = '#b548c7'; // Set color for favorited state
                                    } else {
                                        const favBtn = document.getElementById('fav-btn');
                                        favBtn.style.fontWeight = '100';
                                        favBtn.style.color = ''; // Reset color for non-favorited state
                                    }
                                });


                                const favBtn = document.getElementById('fav-btn');

                                favBtn.addEventListener('click', () => {

                                    //chuyen trang thai cua button
                                    if (favBtn.style.fontWeight === '900') {
                                        favBtn.style.fontWeight = '100';
                                        favBtn.style.color = '';
                                    } else {
                                        favBtn.style.fontWeight = '900';
                                        favBtn.style.color = '#b548c7';
                                    }


                                    // Lay movieId tu input hidden
                                    const movieId = document.getElementById('movieId').value;

                                    const data = {
                                        movieId: movieId,
                                        action: favBtn.style.fontWeight === '900' ? 'add' : 'remove',
                                        userId: '${sessionScope.userInfor.id}' // Assuming user ID is stored in session
                                    }; // Data to send

                                    fetch('${pageContext.request.contextPath}/favorites?', {
                                        method: 'POST',
                                        headers: {
                                            'Content-Type': 'application/json',
                                        },
                                        body: JSON.stringify(data)
                                    })
                                            .then(response => response.json())
                                })
        </script>
        <script>
            // Trailer Modal
            const trailerModal = document.getElementById('trailerModal');
            const playTrailerBtn = document.getElementById('playTrailerBtn');
            const closeModal = document.getElementById('closeModal');
            playTrailerBtn.addEventListener('click', () => {
                trailerModal.classList.add('active');
                document.body.style.overflow = 'hidden';
            });
            closeModal.addEventListener('click', () => {
                trailerModal.classList.remove('active');
                document.body.style.overflow = 'auto';
                // Reset the iframe to stop video playback
                const iframe = document.getElementById('trailerIframe');
                const src = iframe.src;
                iframe.src = '';
                iframe.src = src;
            });
            // Close modal when clicking outside
            trailerModal.addEventListener('click', (e) => {
                if (e.target === trailerModal) {
                    trailerModal.classList.remove('active');
                    document.body.style.overflow = 'auto';
                    // Reset the iframe
                    const iframe = document.getElementById('trailerIframe');
                    const src = iframe.src;
                    iframe.src = '';
                    iframe.src = src;
                }
            });
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
                                return response.json().then(err => {
                                    throw err;
                                });
                            }
                            return response.json();
                        })
                        .then(data => {
                            if (data.success) {
                                const commentsContainer = document.getElementById('comments-container');
                                const newComment = document.createElement('div');
                                newComment.classList.add('comment-card');
                                // Format the date
                                const commentDate = new Date(data.commentDate).toLocaleString('vi-VN', {
                                    day: '2-digit',
                                    month: '2-digit',
                                    year: 'numeric',
                                    hour: '2-digit',
                                    minute: '2-digit'
                                });
                                newComment.innerHTML = `
                    <div class="comment-header">
                        <div class="comment-avatar">${data.username.charAt(0)}</div>
                        <div>
                            <span class="comment-user">${data.username}</span>
                            <span class="comment-date">${commentDate}</span>
                        </div>
                    </div>
                    <div class="comment-content">
                        <p>${commentContent}</p>
                    </div>
                `;
                                // Add new comment to the top of the list
                                commentsContainer.insertBefore(newComment, commentsContainer.firstChild);
                                // Clear input
                                commentInput.value = '';
                                // Show success animation
                                newComment.style.animation = 'fadeIn 0.5s ease';
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