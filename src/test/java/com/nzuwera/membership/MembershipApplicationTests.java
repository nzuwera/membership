package com.nzuwera.membership;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.service.IPlanService;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MembershipApplicationTests {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final UUID planId = UUID.randomUUID();
    private Plan plan;

    @Autowired
    private IPlanService planService;

    /**
     * Initialize plan object
     */
    @Before
    public void initialize() {
        plan = new Plan();
        plan.setId(planId);
        plan.setName("Plan001");
        plan.setType(PlanType.LIMITED);
    }

    /**
     * Positive test of creating a plan
     */
    @Test
    public void test_001_createPlan() {
        Plan createdPlan = planService.createPlan(plan);
        Assert.assertEquals("CreatedPlan", createdPlan.getName(), "Plan001");
    }

    /**
     * Positive test of updating a plan
     *
     * @throws Exception
     */
    @Test
    public void test_002_updatePlan() throws Exception {
        Date startDate = new Date();
        Date updatedEndDate = Utils.setEndDate(startDate, 40);
        Plan plan001 = planService.getPlanByName("Plan001");
        plan001.setEndDate(updatedEndDate);
        planService.updatePlan(plan001);
        Plan updatedPlan = planService.getPlanByName("Plan001");
        assertEqualDates(updatedPlan.getEndDate(), updatedEndDate);
    }

    /**
     * Method to check if two dates are equal
     *
     * @param expected
     * @param value
     */
    private static void assertEqualDates(Date expected, Date value) {
        DateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        String strExpected = formatter.format(expected);
        String strValue = formatter.format(value);
        assertEquals(strExpected, strValue);
    }

    /**
     * Test delete plan method
     */
    @Test
    public void test_999_deletePlan() {
        planService.deletePlanById(planId);
        Plan deletedPlan = planService.getPlanByName("Plan001");
        Assert.assertEquals("deletePlan",new Plan(),deletedPlan);
    }
}
