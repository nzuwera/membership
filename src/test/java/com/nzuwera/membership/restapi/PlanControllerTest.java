package com.nzuwera.membership.restapi;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.service.PlanService;
import com.nzuwera.membership.utils.ResponseObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanControllerTest {
    @InjectMocks
    private PlanController controller;

    @Mock
    private PlanService service;

    Plan limitedPlan = new Plan();
    private final static UUID id = UUID.randomUUID();
    private Date startDate = new Date();
    private Date endDate = new Date(System.currentTimeMillis() + 1_000_000);

    @BeforeEach
    void setUp() {
        limitedPlan.setName(PlanType.LIMITED.name());
        limitedPlan.setType(PlanType.LIMITED);
        limitedPlan.setStartDate(startDate);
        limitedPlan.setEndDate(endDate);
    }

    @Test
    public void createPlan() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContentType(MediaType.APPLICATION_JSON_VALUE);
        request.setMethod(HttpMethod.POST.name());
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        limitedPlan.setId(id);
        when(service.createPlan(any(Plan.class))).thenReturn(
                Plan.builder()
                        .id(id)
                        .name(PlanType.LIMITED.name())
                        .type(PlanType.LIMITED)
                        .startDate(startDate)
                        .endDate(endDate).build()
        );

        ResponseEntity<ResponseObject> responseEntity = controller.createPlan(PlanType.LIMITED.name(), PlanType.LIMITED.name());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getErrorCode(), 200);
    }

}