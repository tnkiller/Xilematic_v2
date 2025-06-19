<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Rạp Phim Điện Ảnh</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/home.css">
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" 
        rel="stylesheet" 
        integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" 
        crossorigin="anonymous">
  
  <!-- Font Awesome -->
  <link rel="stylesheet" 
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" 
        integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" 
        crossorigin="anonymous" 
        referrerpolicy="no-referrer">
  <script
      src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
      integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
      crossorigin="anonymous"
    ></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/home.css"/>
  
</head>
<body>
  <!-- Header -->
  <header>
        <div class="header-logo">
            <div class="logo">
                <a href="#"><img src="./img/LOGO.png" alt=""></a>
            </div>
            <div class="navbar">
                <a href="homeservlet">Home</a>
                <a href="#">News</a>
                <a href="${pageContext.request.contextPath}/SelectCalendar">Booking</a>
            </div>
        </div>
        <form action="homeservlet" method="GET" id="search-box">
            <input type="text" id="search-text" name="search" placeholder="Search for movies..." />
            <button type="submit" id="search-btn">
              <i class="fa-solid fa-magnifying-glass" style="color: #000;"></i>
            </button>
        </form>
        <div class="btn-authen">
            <button>Login</button>
            <button>Register</button>
        </div>
        
    </header>

  <!-- Banner Carousel -->
  <div id="carouselExampleFade" class="carousel slide carousel-fade">
  <div class="carousel-inner">
      <div class="carousel-item active">
      <img src="https://iguov8nhvyobj.vcdn.cloud/media/catalog/product/cache/1/image/1800x/71252117777b696995f01934522c402d/6/4/640x396-ttk.jpg" class="d-block w-100" alt="THÁM TỬ KIÊN: KỲ ÁN KHÔNG ĐẦU">
    </div>
      <c:forEach var="movie" items="${nowShowingMovies}">
          <div class="carousel-item">
          <a href="${pageContext.request.contextPath}/DetailServlet?id=${movie.id}">
            <img src="${movie.image}" alt="${movie.movieName}" class="d-block w-100">
          </a>
          </div>
          
        </c:forEach>
    
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
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

                    </span>
                </div>
                <div class="layer_hover">
                    <a href="${pageContext.request.contextPath}/SelectCalendar?id=${movie.id}" class="btn_reserve">Đặt vé nek</a>
                    <a href="${pageContext.request.contextPath}/DetailServlet?id=${movie.id}" class="btn_View">
                        Chi tiết
                    </a>
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
                                <%= (int)(Math.random() * (140 - 110 + 1) + 110) %>
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
 <footer>
        <div class="footer-content">
            <div class="footer-information">
                <div class="footer-desc">
                    <img src="https://images.unsplash.com/photo-1440404653325-ab127d49abc1?w=150" alt="">
                    <p class="text-muted">Your ultimate destination for movie tickets and entertainment.</p>
                </div>
                <div class="footer-about">
                    <h5 class="">Quick Links</h5>
                    <ul class="">
                        <li><a href="#" class="footer-link">Movie Categories</a></li>
                        <li><a href="#" class="footer-link">Now Showing</a></li>
                        <li><a href="#" class="footer-link">Upcoming Films</a></li>
                        <li><a href="#" class="footer-link">Theater Locations</a></li>
                        <li><a href="#" class="footer-link">Ticket Booking</a></li>
                    </ul>
                </div>
                <div class="footer-about">
                    <h5>Customer Support</h5>
                    <ul>
                        <li><a href="#" class="footer-link">Help Center</a></li>
                        <li><a href="#" class="footer-link">Contact Us</a></li>
                        <li><a href="#" class="footer-link">FAQ</a></li>
                        <li><a href="#" class="footer-link">Refund Policy</a></li>
                        <li><a href="#" class="footer-link">+1 (555) 123-4567</a></li>
                    </ul>
                </div>
                <div class="footer-social">
                    <h5>Newsletter</h5>
                    <p>Subscribe for exclusive movie updates and offers!</p>
                    <div>
                        <input type="email" placeholder="Your email">
                        <button class="btn-subcribe" type="button">Subscribe</button>
                    </div>
                    <div class="social-icons">
                        <a href="#" class="social-icon"><span><ion-icon name="logo-facebook"></ion-icon></span></a>
                        <a href="#" class="social-icon"><span><ion-icon name="logo-instagram"></ion-icon></span></a>
                        <a href="#" class="social-icon"><span><ion-icon name="logo-twitter"></ion-icon></span></a>
                        <a href="#" class="social-icon"><span><ion-icon name="logo-youtube"></ion-icon></span></a>
                        <a href="#" class="social-icon"><span><ion-icon name="logo-linkedin"></ion-icon></span></i></a>
                    </div>
                </div>
            </div>
        </div>
    </footer>

  <!-- Bootstrap JS Bundle (includes Popper) -->
   <script
    src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
    integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
    crossorigin="anonymous"
  ></script>
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.min.js"
    integrity="sha384-RuyvpeZCxMJCqVUGFI0Do1mQrods/hhxYlcVfGPOfQtPJh0JCw12tUAZ/Mv10S7D"
    crossorigin="anonymous"
  ></script>
  <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
  <script src="${pageContext.request.contextPath}/script/carousel.js"></script>
  
</body>
</html>