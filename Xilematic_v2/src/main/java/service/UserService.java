package service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import dao.UserDAO;

public class UserService implements IUserService {

    private UserDAO userDao = new UserDAO();

    @Override
    public void insertUser(User user) {
        try {
            userDao.insertUser(user);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean register(User user) {
        try {
            if (userDao.isUsernameExist(user.getUsername())) {
                return false;
            }
            userDao.insertUser(user);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public User login(String username, String password) {
        try {
            return userDao.login(username, password);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User getUser(int id) {
        try {
            return userDao.selectUser(id);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userDao.selectAllUsers();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            return userDao.deleteUser(id);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            return userDao.updateUser(user);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean isUsernameExist(String username) {
        try {
            return userDao.isUsernameExist(username);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<User> getUsersForPage(int currentPage, int pageSize) {
        try {
            return userDao.getUsersForPage(currentPage, pageSize);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getTotalUsersCount() {
        try {
            return userDao.getTotalUsersCount();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
