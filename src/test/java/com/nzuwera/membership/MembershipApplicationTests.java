package com.nzuwera.membership;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.service.IPlanService;
import com.nzuwera.membership.utils.ResponseObject;
import com.nzuwera.membership.utils.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
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
    private static final String plan001 = "Plan001";
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
    public void test_001_createPlan() throws AlreadyExistsException {
        Plan createdPlan = (Plan)planService.createPlan(plan).getData();
        Assert.assertEquals("CreatedPlan", plan001, createdPlan.getName());
    }

    /**
     * Positive test of updating a plan
     *
     * @throws Exception Exception
     */
    @Test
    public void test_002_updatePlan() throws Exception {
        Date startDate = new Date();
        Date updatedEndDate = Utils.setEndDate(startDate, 40);
        ResponseObject responseObject = planService.getPlanByName(plan001);
        Plan plan = (Plan) responseObject.getData();
        plan.setEndDate(updatedEndDate);
        planService.updatePlan(plan);
        ResponseObject updatedPlan = planService.getPlanByName(plan001);
        Plan plan1 = (Plan)updatedPlan.getData();
        assertEqualDates(plan1.getEndDate(), updatedEndDate);
    }

    /**
     * Method to check if two dates are equal
     *
     * @param expected expected date
     * @param value date value to be tested
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
    public void test_999_deletePlan() throws NotFoundException {
        planService.deletePlan(plan);
        ResponseObject deletedPlan = planService.getPlanByName(plan001);
        Assert.assertEquals("deletePlan", new Plan(), deletedPlan.getData());
    }
}
