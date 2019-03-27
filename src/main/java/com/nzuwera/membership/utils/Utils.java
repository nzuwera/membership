package com.nzuwera.membership.utils;

import java.util.Calendar;
import java.util.Date;

public class Utils {


    public static Date setEndDate(Date startDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH,days);
        return calendar.getTime();
    }

}
