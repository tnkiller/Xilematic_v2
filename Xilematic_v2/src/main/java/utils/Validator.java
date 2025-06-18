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

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Hàm kiểm tra tên người dùng có hợp lệ hay không
    public static boolean validateUsername(String username) {
        // Sử dụng biểu thức chính quy để kiểm tra ký tự đặc biệt
        String regex = "^[a-zA-Z0-9]+$";  // Chỉ cho phép chữ cái và số, không có khoảng trắng hay ký tự đặc biệt
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);

        if (!matcher.matches()) {
            System.out.println("Username không được chứa ký tự đặc biệt hoặc khoảng trắng.");
            return false;
        }

        if (userService.isUsernameExist(username)) {

        }

        // Nếu không có lỗi, tên người dùng hợp lệ
        System.out.println("Username hợp lệ.");
        return true;
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}");
    }

    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6
                && password.matches(".*[A-Za-z].*")
                && password.matches(".*\\d.*");
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
