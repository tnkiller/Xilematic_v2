
package dao;

import java.sql.SQLException;
import java.util.List;
import model.User;


public interface IUserDAO {

    public void insertUser(User user) throws SQLException;
    
    public User login(String username, String password) throws SQLException;

    public User selectUser(int id) throws SQLException;

    public List<User> selectAllUsers() throws SQLException;

    public boolean deleteUser(int id) throws SQLException;

    public boolean updateUser(User user) throws SQLException;

    public boolean isUsernameExist(String username) throws SQLException;

    public List<User> getUsersForPage(int currentPage, int pageSize) throws SQLException;

    public int getTotalUsersCount() throws SQLException;
}
