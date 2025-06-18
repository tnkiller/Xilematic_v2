
package dao;

import dao.IUserDAO;
import context.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;
import org.mindrot.jbcrypt.BCrypt;


public class UserDAO implements IUserDAO {

    private static final String LOGIN = "SELECT * from NguoiDung where ten_tai_khoan LIKE ?";
//    private static final String REGISTER = "INSERT INTO NguoiDung (ten_tai_khoan, ho_ten, email, so_dt, mat_khau, loai_nguoi_dung) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_USER = "INSERT INTO NguoiDung (ten_tai_khoan, ho_ten, email, so_dt, mat_khau, loai_nguoi_dung) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM NguoiDung WHERE ma_nguoi_dung = ?";
    private static final String SELECT_ALL_USERS = "select * from NguoiDung";
    private static final String UPDATE_USER = "UPDATE NguoiDung SET ten_tai_khoan = ?, ho_ten = ?, email = ?, so_dt = ?, mat_khau = ?, loai_nguoi_dung = ? WHERE ma_nguoi_dung = ?";
    private static final String DELETE_USER = "DELETE FROM NguoiDung WHERE ma_nguoi_dung = ?";
    private static final String SEARCH_USER = "SELECT * FROM NguoiDung WHERE username LIKE ?";
    private static final String CHECK_USERNAME_ISEXIST = "SELECT * FROM NguoiDung WHERE ten_tai_khoan LIKE ?";
    private static final String PAGING_USER = "SELECT * FROM NguoiDung ORDER BY ma_nguoi_dung OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String GET_TOTAL_OF_USER = "SELECT COUNT(*) FROM NguoiDung";

    @Override
    public User login(String username, String password) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(LOGIN);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (BCrypt.checkpw(password, rs.getString("mat_khau"))) {
                    return new User(
                            rs.getInt("ma_nguoi_dung"),
                            rs.getString("ten_tai_khoan"),
                            rs.getString("ho_ten"),
                            rs.getString("email"),
                            rs.getString("so_dt"),
                            rs.getString("mat_khau"),
                            rs.getString("loai_nguoi_dung")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        PreparedStatement ps;
        try (Connection con = DBConnection.getConnection()) {
            ps = con.prepareStatement(INSERT_USER);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, hashedPassword);
            ps.setString(6, user.getTypeOfUser());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User selectUser(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("ma_nguoi_dung"),
                        rs.getString("ten_tai_khoan"),
                        rs.getString("ho_ten"),
                        rs.getString("email"),
                        rs.getString("so_dt"),
                        rs.getString("mat_khau"),
                        rs.getString("loai_nguoi_dung")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> selectAllUsers() throws SQLException {
        List<User> result = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_ALL_USERS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new User(
                        rs.getInt("ma_nguoi_dung"),
                        rs.getString("ten_tai_khoan"),
                        rs.getString("ho_ten"),
                        rs.getString("email"),
                        rs.getString("so_dt"),
                        rs.getString("mat_khau"),
                        rs.getString("loai_nguoi_dung")
                ));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DELETE_USER);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(UPDATE_USER);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getTypeOfUser());
            ps.setInt(7, user.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isUsernameExist(String username) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(CHECK_USERNAME_ISEXIST);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getUsersForPage(int currentPage, int pageSize) throws SQLException {
        List<User> result = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(PAGING_USER);
            ps.setInt(1, (currentPage - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new User(
                        rs.getInt("ma_nguoi_dung"),
                        rs.getString("ten_tai_khoan"),
                        rs.getString("ho_ten"),
                        rs.getString("email"),
                        rs.getString("so_dt"),
                        rs.getString("mat_khau"),
                        rs.getString("loai_nguoi_dung"))
                );
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int getTotalUsersCount() throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement pt = c.prepareStatement(GET_TOTAL_OF_USER);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

}
