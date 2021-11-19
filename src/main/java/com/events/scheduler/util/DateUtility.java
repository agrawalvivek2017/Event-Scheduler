package com.events.scheduler.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {

    private static final String YYYY_MM_DD_hh_mm_ss = "yyyy-MM-dd hh:mm:ss";
    public static Date StringToDate(String dateValue) {
        if (dateValue.isEmpty()) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_hh_mm_ss);
        try {
            Date date  = sdf.parse(dateValue);
            return date;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return null;
    }

    public static long getDurationInMinutes(String date1, String date2) {
        if (date1 == null || date1.isEmpty() || date2==null || date2.isEmpty()) return -1;
        Date d1 = StringToDate(date1);
        Date d2 = StringToDate(date2);
        if (d1 == null || d2 == null) return -1;
        return ((d2.getTime() - d1.getTime())/60000);
    }
}
