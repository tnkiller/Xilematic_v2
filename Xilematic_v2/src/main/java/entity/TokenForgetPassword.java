/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDateTime;

/**
 *
 * @author ADMIN
 */
public class TokenForgetPassword {

    private int id;
    private int userId;
    private String token;
    private boolean used;
    private LocalDateTime expiryTime;

    public TokenForgetPassword() {
    }

    public TokenForgetPassword(int id, String token, LocalDateTime expiryTime, boolean used, int userId) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.used = used;
        this.expiryTime = expiryTime;
    }

    public TokenForgetPassword(int userId, String token, boolean used, LocalDateTime expiryTime) {
        this.userId = userId;
        this.token = token;
        this.used = used;
        this.expiryTime = expiryTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public String toString() {
        return "TokenForgetPassword{" + "id=" + id + ", userId=" + userId + ", token=" + token + ", used=" + used + ", expiryTime=" + expiryTime + '}';
    }

}
