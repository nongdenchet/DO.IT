package apidez.com.doit.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by nongdenchet on 2/11/16.
 */
public class DateUtils {

    public static String getDayFromDate(Date date) {
        String formatDate = DateFormat.format("dd/MM/yyyy", date).toString();
        return formatDate.substring(0, 2);
    }

    public static String getMonthFromDate(Date date) {
        String formatDate = DateFormat.format("dd/MM/yyyy", date).toString();
        return formatDate.substring(3, 5);
    }

    public static String getMonthYearFromDate(Date date) {
        String formatDate = DateFormat.format("dd/MM/yyyy", date).toString();
        return formatDate.substring(3, 10);
    }

    public static String getYearFromDate(Date date) {
        String formatDate = DateFormat.format("dd/MM/yyyy", date).toString();
        return formatDate.substring(6, 10);
    }

    public static Date create(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DATE, dayOfMonth);
        return calendar.getTime();
    }
}
