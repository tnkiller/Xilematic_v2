package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class GenerateInfor {

    public static String generateUsername() {
        return "user" + RandomStringUtils.randomAlphabetic(5);
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphabetic(5);

    }

}
