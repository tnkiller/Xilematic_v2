package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import service.IUserService;
import service.UserService;

public class Validator {

    private static IUserService userService = new UserService();

    public static String isEmpty(String str) {
        if (str == null || str.trim().isEmpty()) {
            return "PLease fill form correctly!";
        }
        return null;
    }

    public static String isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            return "Invalid format!";
        } else if (isUsernameExisted(username) != null) {
            return isUsernameExisted(username);
        }
        return null;
    }

    private static String isUsernameExisted(String username) {
        if (userService.isUsernameExist(username)) {
            return "This username exited!";
        }
        return null;
    }

    public static String isValidFullname(String fullname) {
        if (!fullname.matches("^[A-Za-z]+(?: [A-Za-z]+)+$")) {
            return "Invalid fullname";
        }
        return null;
    }

    public static String isValidEmail(String email) {
        if (isEmpty(email) != null || !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            return "Invalid email";
        }
        return null;
    }

    public static String isValidPhoneNumber(String phone) {
        if (!phone.matches("^0\\d{9}$")) {
            return "Invalid phone";
        }
        return null;
    }

    public static String isValidPassword(String password) {
        if (!password.matches("^.{3,}$")) {
            return "Password must be at least 3 characters";
        }
        return null;
    }

    public static String isValidConfirmPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "Invalid confirm password";

        }
        return null;
    }

    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Không cho phép ngày ảo
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidDateOfBirth(Date dob) {
        Date today = new Date();
        return dob != null && dob.before(today);
    }

    public static boolean isPositiveInteger(String value) {
        try {
            int number = Integer.parseInt(value);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInRange(int number, int min, int max) {
        return number >= min && number <= max;
    }

    public static boolean isPositiveFloat(String value) {
        try {
            float number = Float.parseFloat(value);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLengthValid(String str, int minLength, int maxLength) {
        return str != null && str.length() >= minLength && str.length() <= maxLength;
    }

}
