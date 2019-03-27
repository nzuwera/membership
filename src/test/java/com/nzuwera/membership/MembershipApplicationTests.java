package com.nzuwera.membership;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.service.IPlanService;
import com.nzuwera.membership.utils.ResponseObject;
import com.nzuwera.membership.utils.Utils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MembershipApplicationTests {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final UUID planId = UUID.randomUUID();
    private static final String planName = "Plan003";
    private Plan plan;
    private MemberDto memberDto;

    @Value("${membership.expirydate}")
    private int membershipExpireDate;

    @Autowired
    private IPlanService planService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    /**
     * Initialize plan object
     */
    @Before
    public void initialize() {
        plan = new Plan();
        plan.setId(planId);
        plan.setName(planName);
        plan.setType(PlanType.LIMITED);
        memberDto = new MemberDto();
        memberDto.setDateOfBirth(new Date(1974,8,28));
        memberDto.setEmail("member@gmail.com");
        memberDto.setFirstName("John");
        memberDto.setLastName("Doe");
        memberDto.setPlanName("LIMITED");


        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    /**
     * Positive test of creating a plan
     */
    @Test
    public void test_001_createPlan() throws AlreadyExistsException {
        plan = planService.createPlan(plan).getData();
        assertEquals("CreatedPlan", plan.getName(), planName);
    }

    /**
     * Test findPlanByName endpoint
     * and generate asciidoc using SpringRest Docs
     *
     * @throws Exception
     */
    @Test
    public void test_001_testEndPoint_findPlanByName() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/plan/get-plan/" + planName))
                .andExpect(status().isOk())
                .andDo(document("{ClassName}/{methodName}"));
    }

    /**
     * Positive test of updating a plan
     *
     * @throws Exception
     */
    @Test
    public void test_002_updatePlan() throws Exception {
        Date startDate = new Date();
        Date updatedEndDate = Utils.setEndDate(startDate, membershipExpireDate);
        ResponseObject<Plan> planResponseObject = planService.getPlanByName(planName);
        plan = planResponseObject.getData();
        plan.setEndDate(updatedEndDate);
        planService.updatePlan(plan);
        ResponseObject<Plan> updatedPlan = planService.getPlanByName(planName);
        assertEqualDates(updatedPlan.getData().getEndDate(), updatedEndDate);
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
    public void test_999_deletePlan() throws NotFoundException {
        planService.deletePlan(plan);
        ResponseObject<Plan> deletedPlan = planService.getPlanByName(planName);
        Assert.assertEquals("deletePlan", planName, deletedPlan.getData());
    }
}
