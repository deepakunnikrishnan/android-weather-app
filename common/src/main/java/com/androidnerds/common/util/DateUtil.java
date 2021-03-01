package com.androidnerds.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    private static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
    private static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
    private static final String ISO_DATE_TIME_FORMAT_WITH_OFFSET = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";


    public static Date stringToDate(String dateString) {
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            OffsetDateTime odt = OffsetDateTime.parse(dateString);
            Instant instant = odt.toInstant();
            return Date.from(instant);
        } else {

        }*/
        DateFormat dateFormat = new SimpleDateFormat(ISO_DATE_TIME_FORMAT, Locale.US);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date stringToDate(String dateString, String timeZone) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        DateFormat dateFormat = new SimpleDateFormat(ISO_DATE_FORMAT, Locale.US);
        dateFormat.setCalendar(calendar);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDayOfWeek(Date date) {
        if(null != date) {
            Calendar calendar = Calendar.getInstance(Locale.US);
            calendar.setTime(date);
            return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        }
        return "";
    }
}
