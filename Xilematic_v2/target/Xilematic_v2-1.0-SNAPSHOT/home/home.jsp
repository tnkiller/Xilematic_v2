<%@page import="constant.PageLink"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Rạp Phim Điện Ảnh</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">

        <!-- Font Awesome -->
        <link rel="stylesheet" 
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" 
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" 
              crossorigin="anonymous" 
              referrerpolicy="no-referrer">
        <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <script
            src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"
        ></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/home.css"/>
        


    </head>

    <body>

        <!-- Header -->
        <jsp:include page="${request.getContextPath()}/components/header.jsp">
            <jsp:param name="page" value="home"/>
        </jsp:include>

        <!-- Banner Carousel -->
        <div id="carouselExampleFade" class="carousel slide carousel-fade" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active"  data-bs-interval="4000">
                    <img src="https://iguov8nhvyobj.vcdn.cloud/media/catalog/product/cache/1/image/1800x/71252117777b696995f01934522c402d/6/4/640x396-ttk.jpg" class="d-block w-100" alt="THÁM TỬ KIÊN: KỲ ÁN KHÔNG ĐẦU">
                </div>
                <c:forEach var="movie" items="${nowShowingMovies}">
                    <div class="carousel-item" data-bs-interval="2000">
                        <a href="${pageContext.request.contextPath}/DetailServlet?id=${movie.id}">
                            <img src="${movie.image}" alt="${movie.movieName}" class="d-block w-100" style="height:380px">
                        </a>
                    </div>
                </c:forEach>
            </div>

            <!-- Phân Trang -->
            <c:if test="${totalPages > 1}">
                <nav aria-label="Phân trang phim" class="mt-4">
                    <ul class="pagination justify-content-center">
                        <c:forEach begin="1" end="${totalPages}" var="pageNum">
                            <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                <a class="page-link" href="?page=${pageNum}">${pageNum}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>



        <!-- CHAT WIDGET CONTAINER -->
        <div id="chat-widget">
            <!-- CHAT BOX (HIDDEN BY DEFAULT) -->
            <div id="chat-box">
                <div class="chat-header">
                    <h3>Trợ lý ảo 2025</h3>
                    <button class="close-btn" onclick="toggleChat()">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <div class="chat-body" id="chat-messages">
                    <!-- Messages will appear here -->
                    <div class="message bot-message">
                        Xin chào! Tôi có thể giúp gì cho bạn? 😊
                    </div>
                </div>
                <div class="chat-footer">
                    <input type="text" id="user-input" placeholder="Nhập tin nhắn..." autocomplete="off">
                    <button onclick="sendMessage()">
                        <i class="fas fa-paper-plane"></i>
                    </button>
                </div>
            </div>
            <!-- CHAT TRIGGER BUTTON -->
            <button id="chat-trigger" onclick="toggleChat()">
                <i class="fas fa-comment-dots"></i>
            </button>
        </div>

        <!--phim sap chieu--> 
        <div class="carousel-container">
            <button class="btn left" onclick="moveSlide(-1)">&#10094;</button>
            <div class="carousel" id="upcomingCarousel">
                <c:choose>
                    <c:when test="${not empty upcomingMovies}">
                        <%-- Clone cuối (ảnh cuối cùng) --%>
                        <c:set var="lastMovie" value="${upcomingMovies[upcomingMovies.size() - 1]}" />
                        <a href="${pageContext.request.contextPath}/DetailServlet?id=${lastMovie.id}">
                            <img src="${lastMovie.image}" alt="${lastMovie.movieName}">
                        </a>

                        <%-- Ảnh thật --%>
                        <c:forEach var="movie" items="${upcomingMovies}">
                            <a href="${pageContext.request.contextPath}/DetailServlet?id=${movie.id}">
                                <img src="${movie.image}" alt="${movie.movieName}">
                            </a>

                        </c:forEach>

                        <%-- Clone đầu (ảnh đầu tiên) --%>
                        <c:set var="firstMovie" value="${upcomingMovies[0]}" />
                        <a href="${pageContext.request.contextPath}/DetailServlet?id=${firstMovie.id}">
                            <img src="${firstMovie.image}" alt="${firstMovie.movieName}">
                        </a>

                    </c:when>
                    <c:otherwise>
                        <div class="carousel-item">
                            <img src="${pageContext.request.contextPath}/img/default-upcoming.jpg" 
                                 class="d-block w-100" 
                                 alt="Không có phim sắp chiếu">
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <button class="btn right" onclick="moveSlide(1)">&#10095;</button>
        </div>


        <!-- Phim Đang Chiếu -->
        <div class="tab_content on">
            <h2 class="text-center mb-4">Phim Đang Chiếu</h2>
            <ul class="curr_list movie_clist" id="ulMovieList">
                <c:choose>
                    <c:when test="${not empty nowShowingMovies}">
                        <c:forEach var="movie" items="${nowShowingMovies}" varStatus="status">
                            <li>
                                <div class="curr_box">
                                    <span class="num">${status.count}</span>
                                    <span class="img">
                                        <img src="${movie.image != null ? movie.image : 'default-poster.jpg'}" 
                                             class="card-img-top" 
                                             alt="${movie.movieName != null ? movie.movieName : 'Phim'}">
                                        <span class="heart-icon" data-movie-id="${movie.id}">❤️</span>
                                    </span>
                                </div>
                                <div class="layer_hover">
                                    <a href="${pageContext.request.contextPath}/DetailServlet?id=${movie.id}" class="btn_View">Chi tiết</a>
                                    
                                </div> 
                                <dl class="list_text">
                                    <dt>
                                        <a href="${pageContext.request.contextPath}/DetailServlet?id=${movie.id}" class="btn_View">
                                            ${movie.movieName}
                                        </a>
                                    </dt>
                                    <dd>
                                        <span class="rate">
                                            <c:set var="randomDuration">
                                                <%= (int) (Math.random() * (140 - 110 + 1) + 110)%>
                                            </c:set>
                                            ${randomDuration} Phút
                                        </span>
                                        <span class="grade"><em>${movie.releaseDate != null ? movie.releaseDate : 'Sắp ra mắt'}</em></span>
                                    </dd>
                                </dl>      
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <li class="col-12 text-center">
                            <p>Hiện tại không có phim đang chiếu</p>
                        </li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div>

        <!-- Phân Trang -->
        <c:if test="${totalPages > 1}">
            <nav aria-label="Phân trang phim" class="mt-4">
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${totalPages}" var="pageNum">
                        <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${pageNum}">${pageNum}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </c:if>
    </div>



    <!-- Footer -->
    <div class="footer-namespace">
        <%@ include file="/components/footer.jsp" %>
    </div>

    <script>
        // TOGGLE CHAT BOX
        function toggleChat() {
            const chatBox = document.getElementById('chat-box');
            const chatTrigger = document.getElementById('chat-trigger');

            if (chatBox.style.display === 'flex') {
                chatBox.style.display = 'none';
                chatTrigger.style.transform = 'scale(1)';
            } else {
                chatBox.style.display = 'flex';
                chatTrigger.style.transform = 'scale(0)';
            }
        }

        // SEND MESSAGE FUNCTION
        function sendMessage() {
            const input = document.getElementById('user-input');
            const messages = document.getElementById('chat-messages');

            if (input.value.trim() !== '') {
                // Add user message
                messages.innerHTML += `
                      <div class="message user-message">
        ${input.value}
                      </div>
                  `;

                // Simulate bot typing
                messages.innerHTML += `
                      <div class="typing-indicator">
                          <div class="typing-dot"></div>
                          <div class="typing-dot"></div>
                          <div class="typing-dot"></div>
                      </div>
                  `;

                // Scroll to bottom
                messages.scrollTop = messages.scrollHeight;

                // Simulate bot reply after 1-2s
                setTimeout(() => {
                    // Remove typing indicator
                    document.querySelector('.typing-indicator')?.remove();

                    // Add bot reply
                    const replies = [
                        "Tôi đang xử lý yêu cầu của bạn...",
                        "Bạn cần thêm thông tin gì nữa không?",
                        "Tôi có thể giúp gì thêm? 🤖",
                        "Câu hỏi hay đấy! Để tôi kiểm tra...",
                    ];
                    const randomReply = replies[Math.floor(Math.random() * replies.length)];

                    messages.innerHTML += `
                          <div class="message bot-message">
        ${randomReply}
                          </div>
                      `;

                    // Scroll to bottom again
                    messages.scrollTop = messages.scrollHeight;
                }, 1000 + Math.random() * 1000);

                // Clear input
                input.value = '';
            }
        }

        // Allow sending message with Enter key
        document.getElementById('user-input').addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                sendMessage();
            }
        });
    </script>


    <!--ioiconic lib-->
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>



    <!-- Bootstrap JS Bundle (includes Popper) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script src="${pageContext.request.contextPath}/script/carousel.js"></script>

</body>
</html>