package com.nzuwera.membership.utils;

import java.util.Date;

public class Utils {
    public static Date setEndDate(Date startDate, int days) {
        Long endDate = startDate.getTime() + days * 24 * 60 * 60 * 1000;
        return new Date(endDate);
    }
}
