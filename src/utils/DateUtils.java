package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
