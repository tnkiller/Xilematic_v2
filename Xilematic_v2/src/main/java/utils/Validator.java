package utils;

import entity.RequestAttribute;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;
import service.IUserService;
import service.UserService;

public class Validator {

    private static IUserService userService = new UserService();

    public static List<RequestAttribute<String>> validateUserInformation(User user, String confirmPassword) {
        List<RequestAttribute<String>> result = new ArrayList<>();
        boolean flag = true; //mac dinh du lieu dung het
        //username
        String check = isValidUsername(user.getUsername());
        if (check != null) {
            result.add(new RequestAttribute("errUsername", check));
            flag = false;
        } else {
            result.add(new RequestAttribute("username", user.getUsername()));
        }
        //fullname
        check = isValidFullname(user.getFullname());
        if (check != null) {
            result.add(new RequestAttribute("errFullname", check));
            flag = false;
        } else {
            result.add(new RequestAttribute("fullname", user.getFullname()));
        }
        //email
        check = isValidEmail(user.getEmail());
        if (check != null) {
            result.add(new RequestAttribute("errEmail", check));
            flag = false;
        } else {
            result.add(new RequestAttribute("email", user.getEmail()));
        }
        //phone number
        check = isValidPhoneNumber(user.getPhoneNumber());
        if (check != null) {
            result.add(new RequestAttribute("errPhoneNumber", check));
            flag = false;
        } else {
            result.add(new RequestAttribute("phoneNum", user.getPhoneNumber()));
        }
        //password
        check = isValidPassword(user.getPassword());
        if (check != null) {
            result.add(new RequestAttribute("errPassword", check));
            flag = false;
        }
        //confirm password
        check = isValidConfirmPassword(user.getPassword(), confirmPassword);
        if (check != null) {
            result.add(new RequestAttribute("errConfirmPassword", check));
            flag = false;
        }
        return flag ? null : result;
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
        return userService.isUsernameExist(username) ? "This username exited!" : null;
    }

    public static String isValidFullname(String fullname) {
        return !fullname.matches("^[A-Za-z]+(?: [A-Za-z]+)+$") ? "Invalid fullname!" : null;
    }

    public static String isValidEmail(String email) {
        return !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}") ? "Invalid email!" : null;
    }

    public static String isValidPhoneNumber(String phone) {
        return !phone.matches("^0\\d{9}$") ? "Invalid phone!" : null;
    }

    public static String isValidPassword(String password) {
        return !password.matches("^.{3,}$") ? "Password must be at least 3 characters" : null;
    }

    public static String isValidConfirmPassword(String password, String confirmPassword) {
        return !password.equals(confirmPassword) ? "Confirm password must match with password" : null;
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
