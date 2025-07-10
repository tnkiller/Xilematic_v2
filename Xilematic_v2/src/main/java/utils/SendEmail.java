/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import config.AppConfig;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Mail;

/**
 *
 * @author ADMIN
 */
public class SendEmail {

    public static void sendMail(Mail mail) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", AppConfig.SMTP_HOST);
        props.put("mail.smtp.port", AppConfig.SMTP_PORT);
        props.put("mail.debug", "true");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.connectiontimeout", "5000");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(AppConfig.EMAIL, AppConfig.EMAIL_PASSWORD);
            }
        });

        try {
            System.out.println("Attempting to send email to: " + mail.getTo() + " at " + new Date());
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(AppConfig.EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
            message.setSubject(mail.getSubject());

            message.setContent(mail.getBody(), "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Email sent successfully to " + mail.getTo() + " at " + new Date());

        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
            throw e;
        }
    }

    //test
    public static void main(String[] args) throws MessagingException {
        Mail mail = new Mail("Xin chao TNguyen", "nguyenthuy010605@gmail.com", "chao cai cu lon!");
        SendEmail.sendMail(mail);
    }

}
