package com.nzuwera.membership;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.service.IPlanService;
import com.nzuwera.membership.utils.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MembershipApplicationTests {

    private static final Logger LOGGER = Logger.getLogger(MembershipApplicationTests.class.getName());
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final UUID planId = UUID.randomUUID();
    @Autowired
    private IPlanService planService;

    @Test
    public void createPlan() {
        Date startDate = new Date();
        Plan plan = new Plan();
        plan.setId(planId);
        plan.setName("Plan 002");
        plan.setStartDate(startDate);
        plan.setEndDate(Utils.setEndDate(startDate,30));
        plan.setType(PlanType.LIMITED);
        LOGGER.log(Level.INFO,"createdPlan {0}",plan);
        Plan createdPlan = planService.createPlan(plan);
        assertEquals("CreatedPlan",createdPlan.getName(),"Plan 002");
    }

    @Test
    public void updatePlan() throws Exception {
        Date startDate = new Date();
        Date updatedEndDate = Utils.setEndDate(startDate,40);
        Plan plan001 = planService.getPlanByName("Plan 001");
        plan001.setEndDate(updatedEndDate);
        planService.updatePlan(plan001);
        Plan updatedPlan = planService.getPlanByName("Plan 001");
        assertEqualDates(updatedPlan.getEndDate(),updatedEndDate);
    }

    private static void assertEqualDates(Date expected, Date value) {
        DateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        String strExpected = formatter.format(expected);
        String strValue = formatter.format(value);
        assertEquals(strExpected, strValue);
    }

}
