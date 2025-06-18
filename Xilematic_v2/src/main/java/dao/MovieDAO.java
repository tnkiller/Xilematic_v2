package dao;

import context.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import model.Movie;

public class MovieDAO implements IMovieDAO {

    private final String INSERT_MOVIE = """
                                        INSERT INTO [dbo].[Phim]
                                                   ([ten_phim]
                                                   ,[trailer]
                                                   ,[hinh_anh]
                                                   ,[mo_ta]
                                                   ,[ngay_khoi_chieu]
                                                   ,[danh_gia]
                                                   ,[hot]
                                                   ,[dang_chieu]
                                                   ,[sap_chieu]
                                                   ,[dien_vien_chinh]
                                                   ,[dao_dien])
                                             VALUES
                                                   (?,?,?,?,?,?,?,?,?,?,?)""";
    private final String DELETE_MOVIE = "UPDATE Phim SET is_active = 0 WHERE ma_phim = ?";

    private final String UPDATE_MOVIE = """
                                        UPDATE [dbo].[Phim]
                                           SET [ten_phim] = ?
                                              ,[trailer] = ?
                                              ,[hinh_anh] = ?
                                              ,[mo_ta] = ?
                                              ,[ngay_khoi_chieu] = ?
                                              ,[danh_gia] = ?
                                              ,[hot] = ?
                                              ,[dang_chieu] = ?
                                              ,[sap_chieu] = ?
                                              ,[dien_vien_chinh] = ?
                                              ,[dao_dien] = ?
                                         WHERE ma_phim = ?""";
    private final String SELECT_MOVIE = "SELECT * FROM Phim WHERE ma_phim = ?";
    private final String SELECT_ALL_MOVIES = "SELECT * FROM Phim";
    private final String SEARCH_MOVIE_BY_NAME = "SELECT * FROM Phim WHERE ten_phim LIKE";
    private static final String SELECT_MOVIE_SHOWTIME_BY_CINEMA = "SELECT ngay_gio_chieu FROM LichChieu l\n"
            + "JOIN Phim p ON l.ma_phim = p.ma_phim\n"
            + "JOIN RapPhim r on r.ma_rap = l.ma_rap\n"
            + "WHERE p.ma_phim = ? and r.ma_rap = ?";
    private static final String PAGING_MOVIE = "SELECT * FROM Phim WHERE is_active IS NULL ORDER BY ma_phim OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String GET_TOTAL_OF_MOVIE = "SELECT COUNT(*) FROM Phim WHERE is_active IS NULL";

    @Override
    public void insertMovie(Movie movie) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(INSERT_MOVIE);
            ps.setString(1, movie.getMovieName());
            ps.setString(2, movie.getTrailer());
            ps.setString(3, movie.getImage());
            ps.setString(4, movie.getDescription());
            ps.setDate(5, java.sql.Date.valueOf(movie.getReleaseDate()));
            ps.setInt(6, movie.getRate());
            ps.setBoolean(7, movie.isHot());
            ps.setBoolean(8, movie.isStatus());
            ps.setBoolean(9, !movie.isStatus());
            ps.setString(10, movie.getActor());
            ps.setString(11, movie.getDirector());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Movie selectMovie(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_MOVIE);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Movie(
                        rs.getInt("ma_phim"),
                        rs.getString("ten_phim"),
                        rs.getString("trailer"),
                        rs.getString("hinh_anh"),
                        rs.getString("mo_ta"),
                        rs.getDate("ngay_khoi_chieu").toLocalDate(),
                        rs.getInt("danh_gia"),
                        rs.getBoolean("hot"),
                        rs.getBoolean("dang_chieu"),
                        rs.getString("dien_vien_chinh"),
                        rs.getString("dao_dien")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Movie> selectAllMovies() throws SQLException {
        List<Movie> result = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_ALL_MOVIES);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Movie(
                        rs.getInt("ma_phim"),
                        rs.getString("ten_phim"),
                        rs.getString("trailer"),
                        rs.getString("hinh_anh"),
                        rs.getString("mo_ta"),
                        rs.getDate("ngay_khoi_chieu").toLocalDate(),
                        rs.getInt("danh_gia"),
                        rs.getBoolean("hot"),
                        rs.getBoolean("dang_chieu"),
                        rs.getString("dien_vien_chinh"),
                        rs.getString("dao_dien"))
                );
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteMovie(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DELETE_MOVIE);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMovie(Movie movie) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(UPDATE_MOVIE);
            ps.setString(1, movie.getMovieName());
            ps.setString(2, movie.getTrailer());
            ps.setString(3, movie.getImage());
            ps.setString(4, movie.getDescription());
            ps.setDate(5, java.sql.Date.valueOf(movie.getReleaseDate()));
            ps.setInt(6, movie.getRate());
            ps.setBoolean(7, movie.isHot());
            ps.setBoolean(8, movie.isStatus());
            ps.setBoolean(9, !movie.isStatus());
            ps.setString(10, movie.getActor());
            ps.setString(11, movie.getDirector());
            ps.setInt(12, movie.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Movie> getMovieForPage(int currentPage, int pageSize) throws SQLException {
        List<Movie> result = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(PAGING_MOVIE);
            ps.setInt(1, (currentPage - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Movie(
                        rs.getInt("ma_phim"),
                        rs.getString("ten_phim"),
                        rs.getString("trailer"),
                        rs.getString("hinh_anh"),
                        rs.getString("mo_ta"),
                        rs.getDate("ngay_khoi_chieu").toLocalDate(),
                        rs.getInt("danh_gia"),
                        rs.getBoolean("hot"),
                        rs.getBoolean("dang_chieu"),
                        rs.getString("dien_vien_chinh"),
                        rs.getString("dao_dien"))
                );
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int getTotalMoviesCount() throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement pt = c.prepareStatement(GET_TOTAL_OF_MOVIE);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public LocalDateTime getMovieShowtimeByCinema(int ma_rap, int ma_phim) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(SELECT_MOVIE_SHOWTIME_BY_CINEMA);
            ps.setInt(1, ma_phim);
            ps.setInt(2, ma_rap);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                java.sql.Timestamp ts = rs.getTimestamp(1);
                if (ts != null) {
                    return ts.toLocalDateTime();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        MovieDAO m = new MovieDAO();
        System.out.println(m.selectMovie(1).toString());
    }

}
