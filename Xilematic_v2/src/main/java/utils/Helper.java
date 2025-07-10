package utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

public class Helper {

    public String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter CUSTOM_FORMATTER
                = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(CUSTOM_FORMATTER);
    }

    public static String generateRandomString() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static String generateColorCode() {
        String letters = "0123456789ABCDEF";
        String color = "#";
        Random rd = new Random();
        for (var i = 0; i < 6; i++) {
            color += letters.charAt(rd.nextInt(0, letters.length()));
        }
        return color;
    }

    public static String generateUsername() {
        return "user" + RandomStringUtils.randomAlphabetic(5);
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphabetic(5);

    }
}
