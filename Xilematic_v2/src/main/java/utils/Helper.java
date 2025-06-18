package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Helper {

    public String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter CUSTOM_FORMATTER
                = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(CUSTOM_FORMATTER);
    }

}
