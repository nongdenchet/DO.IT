package apidez.com.doit.utils;

import android.text.TextUtils;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by nongdenchet on 2/11/16.
 */
public class DateUtils {
    private static long fakeCurrentMilis = 1455423466991L;

    public static String getDayFromDate(Date date) {
        String formatDate = DateFormat.format("dd/MM/yyyy", date).toString();
        return formatDate.substring(0, 2);
    }

    public static String formatDate(Date date) {
        return DateFormat.format("dd/MM/yyyy", date).toString();
    }

    public static boolean isToday(Date date) {
        return sameDate(date, new Date());
    }

    public static boolean sameDate(Date date1, Date date2) {
        return TextUtils.equals(formatDate(date1), formatDate(date2));
    }

    public static boolean isTomorrow(Date date) {
        return sameDate(date, getTomorrow());
    }

    public static Date getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static String getMonthYearFromDate(Date date) {
        String formatDate = DateFormat.format("dd/MM/yyyy", date).toString();
        return formatDate.substring(3, 10);
    }

    public static Date create(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DATE, dayOfMonth);
        return calendar.getTime();
    }

    public static Date createRandomDate() {
        Random random = new Random();
        return create(random.nextInt(21) + 2000, random.nextInt(12) + 1, random.nextInt(20) + 1);
    }

    public static Date createPastDate() {
        Random random = new Random();
        return create(2000, 9, 10);
    }

    public static Date fakeDate() {
        return new Date(fakeCurrentMilis);
    }
}
