package com.test.store.conroller.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseDateFromString(String stringDate) {
        //TODO: clear
        stringDate += "-01-01";
        try {
            return DATE_FORMAT.parse(stringDate);
        } catch (ParseException initE) {
            initE.printStackTrace();
        }
        return null;
    }

    public static String parseStringFromDate(Date initDate){
        return DATE_FORMAT.format(initDate);
    }
    public static String parseYearFromDate(Date initDate){
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(initDate);
        return "" + localCalendar.get(Calendar.YEAR);
    }
}
