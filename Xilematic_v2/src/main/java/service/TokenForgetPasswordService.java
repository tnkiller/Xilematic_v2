/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.TokenForgetPasswordDAO;
import entity.TokenForgetPassword;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import model.Mail;
import utils.SendEmail;

/**
 *
 * @author ADMIN
 */
public class TokenForgetPasswordService {

    private TokenForgetPasswordDAO forgetPasswordDAO;

    private final int LIMIT_TIME = 10; //minutes

    public TokenForgetPasswordService() {
        forgetPasswordDAO = new TokenForgetPasswordDAO();

    }

    public boolean insertTokenForget(TokenForgetPassword token) {
        return forgetPasswordDAO.insertTokenForget(token);
    }

    public TokenForgetPassword getTokenForgetPassword(String token) {
        return forgetPasswordDAO.getTokenForgetPassword(token);
    }

    public void updateStatus(TokenForgetPassword token) {
        forgetPasswordDAO.updateStatus(token);
    }

    //method to generate token randomly
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    //method to set expiry time
    public LocalDateTime generateExpiryTime() {
        return LocalDateTime.now().plusMinutes(LIMIT_TIME);
    }

    //method to check expiry time is vald
    public boolean isExpired(LocalDateTime time) {
        return LocalDateTime.now().isAfter(time);
    }

    public boolean sendResetPasswordMail(String receiver, String link, String name) {
        String content = "<h1>HELLO " + name + "</h1>" + "<p>Click this link to reset password <a href = " + link + ">Click here</a></p>";

        Mail mail = new Mail("-RESET PASSWORD-", receiver, content);
        try {
            SendEmail.sendMail(mail);
        } catch (MessagingException ex) {
            Logger.getLogger(TokenForgetPasswordService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

}
