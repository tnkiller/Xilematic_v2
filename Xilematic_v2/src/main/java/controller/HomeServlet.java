package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import model.Movie;
import service.MovieService;

@WebServlet("/homeservlet")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(HomeServlet.class.getName());

    private MovieService movieService;

    @Override
    public void init() throws ServletException {
        try {
            movieService = new MovieService();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing MovieService", e);
            throw new ServletException("Could not initialize MovieService", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy danh sách phim
            List<Movie> allMovies = fetchMovies();

            // Kiểm tra và xử lý trường hợp không có phim
            if (allMovies == null || allMovies.isEmpty()) {
                handleNoMovies(request, response);
                return;
            }

            // Sắp xếp phim theo ngày phát hành (mới nhất)
            allMovies.sort(Comparator.comparing(Movie::getReleaseDate).reversed());

            // Xử lý tìm kiếm (nếu có)
            String searchQuery = request.getParameter("search");
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                allMovies = searchMovies(searchQuery, allMovies);
            }

            // Lọc phim hot (banner)
            List<Movie> hotMovies = filterHotMovies(allMovies);

            // Lọc phim đang chiếu
            List<Movie> nowShowingMovies = filterNowShowingMovies(allMovies);

            // Lọc phim sắp chiếu
            List<Movie> upcomingMovies = filterUpcomingMovies(allMovies);

            // Phân trang
            int page = parsePageNumber(request);
            int pageSize = 8; // Số lượng phim trên mỗi trang
            List<Movie> pagedMovies = getPaginatedMovies(nowShowingMovies, page, pageSize);
            int totalPages = calculateTotalPages(nowShowingMovies.size(), pageSize);

            // Đặt thuộc tính cho JSP
            setRequestAttributes(request, allMovies, hotMovies,
                    nowShowingMovies, upcomingMovies,
                    pagedMovies, page, totalPages);

            // Chuyển hướng đến trang chủ
            request.getRequestDispatcher("/home/home.jsp").forward(request, response);

        } catch (Exception e) {
            // Xử lý ngoại lệ
            handleServerError(request, response, e);
        }
    }

    // Phương thức lấy danh sách phim
    private List<Movie> fetchMovies() {
        try {
            List<Movie> movies = movieService.getAllMovies();
            return movies != null ? movies : new ArrayList<>();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching movies", e);
            return new ArrayList<>();
        }
    }

    // Lọc phim hot
    private List<Movie> filterHotMovies(List<Movie> movies) {
        return movies.stream()
                .filter(Movie::isHot)
                .limit(5)
                .collect(Collectors.toList());
    }

    // Lọc phim đang chiếu
    private List<Movie> filterNowShowingMovies(List<Movie> movies) {
        return movies.stream()
                .filter(Movie::isStatus)
                .collect(Collectors.toList());
    }

    // Lọc phim sắp chiếu
    private List<Movie> filterUpcomingMovies(List<Movie> movies) {
        return movies.stream()
                .filter(movie -> !movie.isStatus())
                .collect(Collectors.toList());
    }

    // Phân trang
    private List<Movie> getPaginatedMovies(List<Movie> movies, int page, int pageSize) {
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, movies.size());

        return start >= movies.size() ? new ArrayList<>() : movies.subList(start, end);
    }

    // Tính tổng số trang
    private int calculateTotalPages(int totalMovies, int pageSize) {
        return (int) Math.ceil((double) totalMovies / pageSize);
    }

    // Xử lý số trang
    private int parsePageNumber(HttpServletRequest request) {
        try {
            String pageParam = request.getParameter("page");
            return pageParam != null ? Math.max(1, Integer.parseInt(pageParam)) : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    // Đặt thuộc tính cho request
    private void setRequestAttributes(HttpServletRequest request,
            List<Movie> allMovies,
            List<Movie> hotMovies,
            List<Movie> nowShowingMovies,
            List<Movie> upcomingMovies,
            List<Movie> pagedMovies,
            int currentPage,
            int totalPages) {
        request.setAttribute("allMovies", allMovies);
        request.setAttribute("hotMovies", hotMovies);
        request.setAttribute("nowShowingMovies", nowShowingMovies);
        request.setAttribute("upcomingMovies", upcomingMovies);
        request.setAttribute("pagedMovies", pagedMovies);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
    }

    // Xử lý trường hợp không có phim
    private void handleNoMovies(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", "Không có phim nào được tìm thấy");
        request.getRequestDispatcher("/home/error.jsp").forward(request, response);
    }

    // Xử lý lỗi máy chủ
    private void handleServerError(HttpServletRequest request,
            HttpServletResponse response,
            Exception e)
            throws ServletException, IOException {
        LOGGER.log(Level.SEVERE, "Lỗi máy chủ", e);
        request.setAttribute("errorMessage",
                "Đã xảy ra lỗi: " + e.getMessage());
        request.getRequestDispatcher("/home/error.jsp").forward(request, response);
    }

    // Tìm kiếm phim
    private List<Movie> searchMovies(String query, List<Movie> movies) {
        if (query == null || query.trim().isEmpty()) {
            return movies;
        }

        return movies.stream()
                .filter(movie
                        -> movie.getMovieName().toLowerCase().contains(query.toLowerCase())
                //                movie.getDirector().toLowerCase().contains(query.toLowerCase()) 
                //                movie.getActor().toLowerCase().contains(query.toLowerCase())
                )
                .collect(Collectors.toList());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý các yêu cầu POST (nếu cần)
        doGet(request, response);
    }
}
