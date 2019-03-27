package com.nzuwera.membership;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.service.IPlanService;
import com.nzuwera.membership.service.PlanService;
import com.nzuwera.membership.utils.Utils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MembershipApplicationTests {

    private static final Logger LOGGER = Logger.getLogger(MembershipApplicationTests.class.getName());
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final UUID planId = UUID.randomUUID();
    private Plan plan;
    @Autowired
    private IPlanService planService;

    @Before
    public void initialize(){
        plan = new Plan();
        plan.setId(planId);
        plan.setName("Plan 001");
        plan.setType(PlanType.LIMITED);
    }

    @Test
    public void test_001_createPlan() {
        Plan createdPlan = planService.createPlan(plan);
        assertEquals("CreatedPlan",createdPlan.getName(),"Plan 001");
    }

    @Test
    public void test_002_updatePlan() throws Exception {
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

    @Test
    public void test_999_deletePlan(){
        planService.deletePlanById(planId);
    }
}
