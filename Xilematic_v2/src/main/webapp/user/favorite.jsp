<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>My Favorites</title>
        <!-- Ion Icons -->
        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
        <!-- Link to CSS external -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/favorite_style.css"/>
    </head>
    <body>
        <header>
            <jsp:include page="${request.getContextPath()}/components/header.jsp">
                <jsp:param name="page" value="favorite"/>
            </jsp:include>
        </header>
        <div class="container">
            <div class="header">
                <h1 class="title">My Favourite</h1>
                <div class="meta-container">
                    <div class="total-films">Total: ${movieCount} film(s)</div>
                    <div class="sort-container">
                        <span class="sort-label">Sort by:</span>
                        <select class="sort-dropdown" id="sortSelect">
                            <option value="recent">Recently Added</option>
                            <option value="rating">Highest Rating</option>
                            <option value="title">Alphabetical</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="movies-grid" id="moviesContainer">
                <c:forEach var="movie" items="${favoriteList}" varStatus="loop">
                    <div class="movie-card" 
                         data-title="${movie.movieName.toLowerCase()}" 
                         data-rating="${movie.rate}" 
                         data-added="${loop.index}">
                        <div class="movie-poster" style="background-image: url('${movie.image}');"></div>
                        <div class="movie-info">
                            <div class="movie-title">${movie.movieName}</div>
                            <c:choose>
                                <c:when test="${movie.director.length() > 20}">
                                    <div class="movie-director">Director: ${movie.director.substring(0,21)}...</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="movie-director">Director: ${movie.director}</div>
                                </c:otherwise>
                            </c:choose>
                            <div class="movie-meta">
                                <span class="movie-duration">144m</span>
                                <span class="movie-views">144m views</span>
                            </div>
                            <div class="movie-actions">
                                <a href="${pageContext.request.contextPath}/SelectCalendar?id=${movie.id}&movieName=${movie.movieName}" class="btn btn-book">
                                    <ion-icon name="ticket-outline"></ion-icon>
                                    BOOK THIS
                                </a>
                                <a href="${pageContext.request.contextPath}/favorites?action=delete&id=${movie.id}" class="btn btn-delete" onclick="return confirm('Are you sure to remove this movie from your favorite?');">
                                    <ion-icon name="trash-outline"></ion-icon>
                                    REMOVE
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const sortSelect = document.getElementById('sortSelect');
                const moviesContainer = document.getElementById('moviesContainer');
                let movieCards = Array.from(moviesContainer.getElementsByClassName('movie-card'));

                // Store original order for "Recently Added" sorting
                const originalOrder = movieCards.map(card => card.cloneNode(true));

                sortSelect.addEventListener('change', function () {
                    const sortValue = this.value;

                    // Clone the array to avoid modifying the original during sort
                    const cardsToSort = Array.from(movieCards);

                    switch (sortValue) {
                        case 'recent':
                            // Restore original order
                            moviesContainer.innerHTML = '';
                            originalOrder.forEach(card => {
                                moviesContainer.appendChild(card.cloneNode(true));
                            });
                            break;

                        case 'rating':
                            cardsToSort.sort((a, b) => {
                                const ratingA = parseFloat(a.dataset.rating);
                                const ratingB = parseFloat(b.dataset.rating);
                                return ratingB - ratingA; // Descending order
                            });
                            reorderMovies(cardsToSort);
                            break;

                        case 'title':
                            cardsToSort.sort((a, b) => {
                                const titleA = a.dataset.title;
                                const titleB = b.dataset.title;
                                return titleA.localeCompare(titleB); // Ascending order
                            });
                            reorderMovies(cardsToSort);
                            break;
                    }

                    // Update the movieCards reference after sorting
                    movieCards = Array.from(moviesContainer.getElementsByClassName('movie-card'));
                });

                function reorderMovies(sortedCards) {
                    moviesContainer.innerHTML = '';
                    sortedCards.forEach(card => {
                        moviesContainer.appendChild(card);
                    });
                }
            });
        </script>
    </body>
</html>