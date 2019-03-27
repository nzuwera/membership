package com.nzuwera.membership.utils;

import com.nzuwera.membership.service.IPlanService;
import com.nzuwera.membership.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Utils {


    public static Date setEndDate(Date startDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH,days);
        // Long endDate = startDate.getTime() + days * 24 * 60 * 60 * 1000;
        return calendar.getTime(); // new Date(endDate);
    }

    public static void deletePlan(UUID id){
        PlanService planService = new PlanService();
        planService.deletePlanById(id);
    }
}
