/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import constant.SessionAttribute;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;

/**
 *
 * @author ADMIN
 */
public class SessionUtil {

    public static void initializeSession(HttpSession session, User userInfor)
            throws ServletException, IOException {
        session.setAttribute(SessionAttribute.USER_INFOR, userInfor);
        session.setAttribute(SessionAttribute.COLOR_CODE, Helper.generateColorCode());
    }

}
