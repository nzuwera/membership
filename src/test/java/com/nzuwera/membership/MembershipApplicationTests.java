package com.nzuwera.membership;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.service.IPlanService;
import com.nzuwera.membership.utils.ResponseObject;
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
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MembershipApplicationTests {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final UUID PLAN_ID = UUID.randomUUID();
    private static final String PLAN_001 = "Plan001";
    private Plan plan;

    @Autowired
    private IPlanService planService;

    /**
     * Initialize plan object
     */
    @Before
    public void initialize() {
        plan = new Plan();
        plan.setId(PLAN_ID);
        plan.setName("Plan001");
        plan.setType(PlanType.LIMITED);
    }

    /**
     * Cleanup after testing
     */
    @After
    public void tearDown() {
        plan = null;
    }

    /**
     * Positive test of creating a plan
     */
    @Test
    public void test_001_createPlan() throws AlreadyExistsException {
        Plan createdPlan = (Plan) planService.createPlan(plan).getData();
        Assert.assertEquals("CreatedPlan", PLAN_001, createdPlan.getName());
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
        assertNotNull(updatedEndDate);
        ResponseObject currentPlanResponse = planService.getPlanByName(PLAN_001);
        Plan currentPlan = (Plan) currentPlanResponse.getData();
        currentPlan.setEndDate(updatedEndDate);
        planService.updatePlan(currentPlan);
        ResponseObject updatePlanResponse = planService.getPlanByName(PLAN_001);
        Plan updatedPlan = (Plan) updatePlanResponse.getData();
        assertEqualDates(updatedPlan.getEndDate(), updatedEndDate);
    }

    /**
     * Method to check if two dates are equal
     *
     * @param expected expected date
     * @param value    date value to be tested
     */
    private static void assertEqualDates(Date expected, Date value) {
        DateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        String strExpected = formatter.format(expected);
        String strValue = formatter.format(value);
        assertEquals(strExpected, strValue);
    }

    /**
     * Testing delete plan
     *
     * @throws NotFoundException NotFoundException
     */
    @Test
    public void test_999_deletePlan() {
        assertNotNull(plan);
        planService.deletePlan(plan);
    }
}
