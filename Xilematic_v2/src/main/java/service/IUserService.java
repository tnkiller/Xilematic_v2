package service;

import java.util.List;
import model.User;

public interface IUserService {

    public void insertUser(User user);

    public boolean register(User user);

    public User login(String username, String password);

    public User getUser(int id);

    public List<User> getAllUsers();

    public boolean deleteUser(int id);

    public boolean updateUser(User user);

    public boolean isUsernameExist(String username);

    public List<User> getUsersForPage(int currentPage, int pageSize);

    public int getTotalUsersCount();

}
