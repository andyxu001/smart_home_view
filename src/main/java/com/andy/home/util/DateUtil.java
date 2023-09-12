package com.andy.home.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_YYYY_MM_DD);

    public static Date parse(Date object) throws ParseException {
        Date date = dateFormat.parse(dateFormat.format(object));
        return dateFormat.parse(dateFormat.format(object));
    }




}
