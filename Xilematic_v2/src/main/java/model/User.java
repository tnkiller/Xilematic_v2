package model;

public class User {

    private int id;
    private String username;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String password;
    private String typeOfUser;

    public User() {
    }

    public User(int id, String username, String fullname, String email, String phoneNumber, String password, String typeOfUser) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.typeOfUser = typeOfUser;
    }

    public User(String username, String fullname, String email, String phoneNumber, String password, String typeOfUser) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.typeOfUser = typeOfUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", fullname=" + fullname + ", email=" + email + ", phoneNumber=" + phoneNumber + ", password=" + password + ", typeOfUser=" + typeOfUser + '}';
    }

}
