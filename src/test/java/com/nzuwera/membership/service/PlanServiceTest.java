package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.repository.PlanRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

    @InjectMocks
    private PlanService planService;

    @Mock
    private PlanRepository repository;

    Plan limitedPlan = new Plan();
    Plan unlimitedPlan = new Plan();

    @BeforeEach
    void setUp() {
        limitedPlan.setName(PlanType.LIMITED.name());
        limitedPlan.setType(PlanType.LIMITED);
        limitedPlan.setStartDate(new Date());
        limitedPlan.setEndDate(new Date(System.currentTimeMillis() + 1_000_000));

        unlimitedPlan.setName(PlanType.UNLIMITED.name());
        unlimitedPlan.setType(PlanType.UNLIMITED);
        unlimitedPlan.setStartDate(new Date());
        unlimitedPlan.setEndDate(new Date(System.currentTimeMillis() + 1_000_000));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void createPlan() {
        // simulate execution of save method in the repository, and tells what
        // to return
        UUID id = UUID.randomUUID();
        when(repository.save(limitedPlan)).thenReturn(Plan.builder()
                .id(id)
                .name(PlanType.LIMITED.name())
                .type(PlanType.LIMITED)
                .startDate(new Date())
                .endDate(new Date(System.currentTimeMillis() + 1_000_000))
                .build());

        Plan savedBook = planService.createPlan(limitedPlan);
        assertEquals(savedBook.getId(), id);
    }

    @Test
    void updatePlan() {
    }

    @Test
    void deletePlan() {
    }

    @Test
    void getPlanByName() {
    }

    @Test
    void findAllPlan() {
    }
}