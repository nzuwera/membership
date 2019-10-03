package com.nzuwera.membership.utils;

import com.nzuwera.membership.message.Message;

import java.util.Calendar;
import java.util.Date;

public class Utils {

    private Utils() {
        // Empty private Constructor
    }

    public static Date setEndDate(Date startDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public static ResponseObject setSuccessResponse(Object object){
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatus(true);
        responseObject.setErrorCode(200);
        responseObject.setMessage(Message.SUCCESS);
        responseObject.setData(object);
        return responseObject;

    }

}
