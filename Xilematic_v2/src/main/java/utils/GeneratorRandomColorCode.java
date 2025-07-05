/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Random;

/**
 *
 * @author ADMIN
 */
public class GeneratorRandomColorCode {

    public static String generateColorCode() {
        String letters = "0123456789ABCDEF";
        String color = "#";
        Random rd = new Random();
        for (var i = 0; i < 6; i++) {
            color += letters.charAt(rd.nextInt(0, letters.length()));
        }
        return color;
    }

}
