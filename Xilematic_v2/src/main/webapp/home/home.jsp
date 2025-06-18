<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Rạp Phim Điện Ảnh</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/home.css">
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
        <style>
            html,
            body,
            div,
            span,
            applet,
            object,
            iframe,
            h1,
            h2,
            h3,
            h4,
            h5,
            h6,
            p,
            blockquote,
            pre,
            a,
            abbr,
            acronym,
            address,
            big,
            cite,
            code,
            del,
            dfn,
            em,
            img,
            ins,
            kbd,
            q,
            s,
            samp,
            small,
            strike,
            strong,
            sub,
            sup,
            tt,
            var,
            b,
            u,
            i,
            center,
            dl,
            dt,
            dd,
            ol,
            ul,
            li,
            fieldset,
            form,
            label,
            legend,
            table,
            caption,
            tbody,
            tfoot,
            thead,
            tr,
            th,
            td,
            article,
            aside,
            canvas,
            details,
            embed,
            figure,
            figcaption,
            footer,
            header,
            hgroup,
            menu,
            nav,
            output,
            ruby,
            section,
            summary,
            time,
            mark,
            audio,
            video {
                margin: 0;
                padding: 0;
                border: 0;
                vertical-align: baseline;
            }
            header {
                background-color: #e0e0e0;
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 0 20px;
            }

            .header-logo {
                display: flex;
                align-items: center;
                padding: 20px 0;
            }
            .logo img {
                width: 70px;
            }
            .navbar {
                margin-left: 30px;
            }
            .navbar a {
                text-decoration: none;
                color: black;
                font-size: 18px;
                margin-left: 20px;
            }

            header button {
                width: 100px;
                padding: 10px 0;
                font-size: 16px;
                border-radius: 6px;
                background-color: #b71c1c;
                color: white;
                border: none;
                margin-left: 5px;
                cursor: pointer;
                transition: all 0.5s;
            }
            header button:hover {
                background-color: #c62828;
            }
            /* search */
            #search-box {
                background-color: #fff;
                border-radius: 30px;
            }
            #search-box #search-text {
                border: none;
                outline: none;
                background: none;
                /* padding: 10px 15px; */
                font-size: 16px;
                width: 420px;
                padding: 10px 15px;
            }
            /* #search-box:hover #search-text {
              width: 300px;
              padding: 10px 15px;
              transition: width 0.25s ease-in-out;
            } */
            #search-box #search-btn {
                background-color: #fff;
                cursor: pointer;
                padding: 10px;
                border-radius: 50%;
                width: 40px;
            }

            /* end search  */

            /* end header */

            /* banner */
            .carousel.slide.carousel-fade {
                position: relative;
                width: 100%;
                height: 500px;
            }
            .carousel-inner {
                height: 100%;
            }
            .carousel-item {
                height: 100%;
            }
            .carousel-item img {
                object-fit: cover;
                height: 100%;
            }

            /* end banner */

            /* carousel */
            .carousel-container {
                width: 100%;
                max-width: 1055px;
                margin: auto;
                position: relative;
                overflow: hidden;
                margin-top: 25px;
            }
            .carousel {
                display: flex;
                transition: transform 0.4s ease-in-out;
            }

            .carousel img {
                width: 284px;
                height: auto;
                padding: 5px;
                /* object-fit: cover; */
                flex-shrink: 0;
            }
            .btn.left {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background: rgba(0, 0, 0, 0.5);
                background-color: #c62828;
                color: rgb(236, 227, 227);
                border: none;
                padding: 10px;
                cursor: pointer;
                z-index: 2;
                left: 0;
            }
            .btn.right {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background: rgba(0, 0, 0, 0.5);
                background-color: #c62828;
                color: rgb(236, 227, 227);
                border: none;
                padding: 10px;
                cursor: pointer;
                z-index: 2;
                right: 0;
            }

            /* end carousel */

            .tab_content {
                display: none;
                padding-bottom: 10px;
            }
            .tab_content.on {
                display: block;
                width: 1020px;
                margin: 0 auto;
                padding-top: 40px;
            }
            div {
                display: block;
                unicode-bidi: isolate;
            }
            ::-webkit-scrollbar-thumb {
                background-color: #231f20;
                border-radius: 0;
                border: none;
            }
            ::-webkit-scrollbar-corner {
                background: 0 0;
            }
            ::-webkit-scrollbar {
                width: 8px;
                height: 8px;
            }
            .curr_list:after {
                content: "";
                display: block;
                clear: both;
            }
            body {
                font-size: 12px;
                font-family: "Arial", "Helvetica", "sans-serif";
                font-weight: 400;
                color: #555;
                line-height: 1;
            }
            ul {
                display: block;
                list-style-type: disc;
                margin-block-start: 1em;
                margin-block-end: 1em;
                padding-inline-start: 40px;
                unicode-bidi: isolate;
            }
            * {
                scrollbar-width: thin;
                scrollbar-color: #231f20 transparent;
            }
            ol,
            ul,
            li {
                list-style: none;
            }
            .curr_list {
                margin-left: -20px;
                min-height: 441px;
            }
            li {
                display: list-item;
                text-align: -webkit-match-parent;
                unicode-bidi: isolate;
            }
            .curr_list > li {
                position: relative;
                float: left;
                width: 180px;
                height: 340px;
                margin: 0 0 30px 20px;
                border: 1px solid #dedede;
                box-sizing: border-box;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
            }
            .movie_clist.curr_list > li {
                width: 230px;
                height: 411px;
                margin: 0 0 40px 20px;
                background: #fff;
                position: relative;
                /* display: flex; */
                /* align-items: flex-end;/ */
                box-shadow: 0px 7px 10px rgba(0, 0, 0, 0.5);
                transition: 0.5s ease-in-out;
                border-radius: 10px;
            }
            .movie_clist.curr_list > li:hover {
                transform: translateY(20px);
            }
            .movie_clist.curr_list > li ::before {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                display: block;
                width: 100%;
                height: 100%;
                background: linear-gradient(
                    to bottom,
                    rgba(113, 134, 174, 0.204),
                    rgba(73, 203, 69, 0.116)
                    );
                z-index: 2;
                transition: 0.5s all;
                opacity: 0;
            }
            .movie_clist.curr_list > li:hover::before {
                opacity: 1;
            }
            .curr_list .curr_box {
                position: relative;
            }
            .curr_list .num {
                position: absolute;
                left: 0;
                bottom: 0;
                z-index: 11;
                display: block;
                width: 100%;
                height: 49px;
                background: url(/LCHS/Image/Bg/bg_w_mk.png) repeat-y left bottom;
                font-size: 28px;
                color: #fff;
                font-family: "linlivertine";
                font-weight: bold;
                padding: 5px 0 0 20px;
                font-style: italic;
                line-height: 45px;
                box-sizing: border-box;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                text-align: left;
            }
            a {
                text-decoration: none;
                color: #555;
            }
            .curr_list a {
                display: block;
            }
            img {
                border: 0;
                vertical-align: middle;
            }
            .curr_list .img img {
                width: 178px;
                height: 262px;
            }
            .movie_clist.curr_list .img img {
                width: 228px;
                height: 334px;
            }
            .curr_list .layer_hover {
                display: none;
                position: absolute;
                left: 0;
                top: 0;
                width: 178px;
                height: 262px;
                padding: 73px 10px 0;
                background: url(/LCHS/Image/Bg/bg_mask06.png) repeat;
                color: #fff;
                text-align: center;
                font-size: 14px;
                line-height: 27px;
                box-sizing: border-box;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                z-index: 11;
            }
            .movie_clist.curr_list .layer_hover {
                width: 228px;
                height: 335px;
                padding: 114px 20px 0;
                background-image: url(/LCHS/Image/Bg/bg_mask06_2.png);
            }
            .curr_list .layer_hover [class^="btn_"] {
                width: 100%;
                height: 50px;
                border: 1px solid #efc50c;
                color: #d4b219;
                text-align: center;
                font-size: 16px;
                line-height: 50px;
                font-weight: bold;
                box-sizing: border-box;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                font-weight: bold;
            }

            .movie_clist.curr_list .layer_hover [class^="btn_"] {
                font-size: 15px;
            }
            .curr_list .layer_hover .btn_View {
                margin-top: 10px;
            }
            .movie_clist.curr_list .layer_hover [class^="btn_"] {
                font-size: 15px;
            }
            .curr_list .list_text {
                border-top: 1px solid #e5e5e5;
                box-sizing: border-box;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
            }
            .curr_list .list_text dt {
                padding: 12px 9px 11px 9px;
                border-bottom: 1px solid #dedede;
            }
            .movie_clist.curr_list .list_text dt {
                padding: 12px 15px 11px 15px;
            }
            .curr_list .list_text dt a {
                overflow: hidden;
                width: 100%;
                white-space: nowrap;
                text-overflow: ellipsis;
                font-size: 16px;
                color: #231f20;
                font-weight: bold;
            }
            .movie_clist.curr_list .list_text dt a {
                font-size: 14px;
                line-height: 20px;
            }
            .curr_list .list_text dd {
                padding: 0 10px;
                color: #666;
                font-size: 12px;
                line-height: 30px;
            }
            .movie_clist.curr_list .list_text dd {
                padding: 0 46px;
                font-size: 13px;
                color: #777;
            }
            .curr_list .list_text dd .rate {
                display: inline-block;
            }
            .curr_list .list_text dd .grade {
                position: relative;
                display: inline-block;
                margin-left: 6px;
                padding-left: 8px;
            }
            .curr_list .list_text dd .grade:before {
                content: "";
                position: absolute;
                left: 0;
                top: 10px;
                width: 1px;
                height: 11px;
                background: #dfdfdf;
            }
            .curr_list .list_text dd .grade:before {
                content: "";
                position: absolute;
                left: 0;
                top: 10px;
                width: 1px;
                height: 11px;
                background: #dfdfdf;
            }
            address,
            caption,
            em {
                font-weight: normal;
                font-style: normal;
            }
            .curr_list .list_text dd .grade em {
                display: inline-block;
            }
            .curr_list .list_text dd:after {
                content: "";
                display: block;
                clear: both;
            }
            .curr_list > li {
                position: relative;
                overflow: hidden;
            }

            .curr_list > li:hover .layer_hover {
                display: block;
            }

            .curr_list > li:hover .img img {
                filter: brightness(60%) blur(4px); /* làm tối + mờ ảnh */
                transition: all 0.3s ease;
            }
            .curr_list > li:hover .num {
                /* background: url(/LCHS/Image/Bg/bg_w_mk.png) repeat-y left bottom; */
                filter: brightness(80%) blur(2px); /* làm tối + mờ ảnh */
                transition: all 0.3s ease;
            }

            .curr_list .img img {
                transition: all 0.3s ease;
            }
            /* <!--footer--> */
            .footer-content {
                background-color: rgb(11, 6, 32);
            }
            .footer-information {
                display: flex;
                justify-content: space-around;
                padding: 50px 0;
            }
            .footer-information h5 {
                color: white;
                font-size: 25px;
                margin: 0;
            }
            .footer-desc {
                color: rgb(146, 144, 144);
            }
            .footer-about ul {
                padding-left: 0;
            }
            .footer-about ul li {
                list-style: none;
                margin: 5px;
            }

            .footer-about ul li a {
                color: rgb(146, 144, 144);
                text-decoration: none;
                transition: all 0.5s;
            }
            .footer-about ul li a:hover {
                color: white;
            }

            .footer-social p {
                color: rgb(146, 144, 144);
            }
            .social-icons {
                margin-top: 20px;
                display: flex;
                gap: 15px;
            }

            .social-icon {
                font-size: 30px;
                transition: all 0.3s ease;
                color: rgb(146, 144, 144);
                transition: all 0.5s;
            }
            .social-icon:hover {
                color: white;
                transform: translateY(-2px);
            }

            .footer-social input {
                background-color: rgb(146, 144, 144);
                padding: 10px;
                border: none;
            }
            input::placeholder {
                color: black;
            }
            .btn-subcribe {
                padding: 10px;
                background-color: #b71c1c;
                border: none;
                color: white;
                cursor: pointer;
                transition: all 0.5s;
            }
            .btn-subcribe:hover {
                opacity: 0.9;
                transform: translateY(-2px);
            }

        </style>

    </head>
    <body>
        <%@ include file="/components/header.jsp" %>

        <!-- Banner Carousel -->
        <div id="carouselExampleFade" class="carousel slide carousel-fade">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="https://iguov8nhvyobj.vcdn.cloud/media/catalog/product/cache/1/image/1800x/71252117777b696995f01934522c402d/6/4/640x396-ttk.jpg" class="d-block w-100" alt="THÁM TỬ KIÊN: KỲ ÁN KHÔNG ĐẦU">
                </div>
                <div class="carousel-item">
                    <img src="https://cdn.thuviennhadat.vn/upload/hinh-anh-bai-viet/VHNH/thang-04-2025/12/lich-chieu-som-lat-mat-8-vong-tay-nang-bao-nhieu-tuoi-duoc-xem-lat-mat-8-vong-tay-nang.jpg" class="d-block w-100" alt="LẬT MẶT 8: VÒNG TAY NẮNG">
                </div>
                <div class="carousel-item">
                    <img src="https://media.lottecinemavn.com/Media/MovieFile/MovieImg/202505/11778_103_100003.jpg" class="d-block w-100" alt="YADANG: BA MẶT LẬT KÈO">
                </div>
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
                        <img src="${lastMovie.image}" alt="${lastMovie.movieName}">

                        <%-- Ảnh thật --%>
                        <c:forEach var="movie" items="${upcomingMovies}">
                            <img src="${movie.image}" alt="${movie.movieName}">
                        </c:forEach>

                        <%-- Clone đầu (ảnh đầu tiên) --%>
                        <c:set var="firstMovie" value="${upcomingMovies[0]}" />
                        <img src="${firstMovie.image}" alt="${firstMovie.movieName}">
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
                                    <a href="javascript:void(0)" onclick="goToTiketing('${movie.id}');" class="btn_reserve">Đặt vé</a>
                                    <a href="javascript:void(0)" 
                                       data-movie-id="${movie.id}" 
                                       class="btn_View movie-detail-link">
                                        Chi tiết
                                    </a>
                                </div> 
                                <dl class="list_text">
                                    <dt>
                                        <a href="javascript:void(0);" onclick="goToMovie('${movie.id}');">${movie.movieName}</a>
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
    <%@ include file="/components/footer.jsp" %>

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
    <script>
                                            function goToTiketing(movieId) {
                                                // Chuyển hướng đến trang đặt vé cho phim cụ thể
                                                window.location.href = '${pageContext.request.contextPath}/BookingServlet?id=' + movieId;
                                            }

                                            function goToMovie(movieId) {
                                                // Chuyển hướng đến trang chi tiết phim
                                                window.location.href = '${pageContext.request.contextPath}/DetailServlet?id=' + movieId;
                                            }

                                            document.addEventListener('DOMContentLoaded', function () {
                                                // Event delegation cho các liên kết chi tiết phim
                                                document.querySelector('.movie_clist').addEventListener('click', function (event) {
                                                    // Tìm phần tử gần nhất có class 'btn_View' hoặc 'movie-detail-link'
                                                    const detailLink = event.target.closest('.btn_View, .movie-detail-link');

                                                    if (detailLink) {
                                                        // Lấy movieId từ data attribute hoặc từ hàm onclick
                                                        const movieId = detailLink.getAttribute('data-movie-id') ||
                                                                (detailLink.getAttribute('onclick') ?
                                                                        detailLink.getAttribute('onclick').match(/'(\d+)'/)[1] : null);

                                                        if (movieId) {
                                                            // Chuyển hướng đến trang chi tiết
                                                            window.location.href = '${pageContext.request.contextPath}/DetailServlet?id=' + movieId;
                                                        }
                                                    }
                                                });
                                            });

                                            // Carousel slider script
                                            const carousel = document.getElementById('upcomingCarousel');
                                            const slides = carousel.children;
                                            const totalSlides = slides.length;
                                            const slideWidth = slides[0].clientWidth;

                                            let currentIndex = 1; // vì clone đầu ở vị trí 0
                                            carousel.style.transform = `translateX(${-slideWidth * currentIndex}px)`;

                                            function moveSlide(direction) {
                                                // Đảm bảo transition được áp dụng
                                                carousel.style.transition = carousel.style.transition === "none"
                                                        ? "transform 0.4s ease-in-out"
                                                        : carousel.style.transition;

                                                currentIndex += direction;
                                                carousel.style.transform = `translateX(${-slideWidth * currentIndex}px)`;

                                                // Đợi animation chạy xong
                                                setTimeout(() => {
                                                    if (currentIndex === 0) {
                                                        // Nếu tới clone cuối → nhảy về ảnh thật cuối
                                                        carousel.style.transition = "none";
                                                        currentIndex = totalSlides - 2;
                                                        carousel.style.transform = `translateX(${-slideWidth * currentIndex}px)`;
                                                    } else if (
                                                            currentIndex === totalSlides - 1 ||
                                                            currentIndex === totalSlides - 2 ||
                                                            currentIndex === totalSlides - 3
                                                            ) {
                                                        // Nếu tới clone đầu → nhảy về ảnh thật đầu
                                                        carousel.style.transition = "none";
                                                        currentIndex = 1;
                                                        carousel.style.transform = `translateX(${-slideWidth * currentIndex}px)`;
                                                    }
                                                }, 400); // khớp với thời gian transition
                                            }
    </script>
</body>
</html>