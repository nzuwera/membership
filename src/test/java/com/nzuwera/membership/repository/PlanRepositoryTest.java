package com.nzuwera.membership.repository;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class PlanRepositoryTest {
    @Autowired
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
    void findTestAll() {

        // Given
        List<Plan> plans = new ArrayList<>();
        plans.add(limitedPlan);
        plans.add(unlimitedPlan);

        // When
        repository.saveAll(plans);
        List<Plan> results = repository.findAll();

        // Then
        int expected = 2;
        assertThat(results.size()).isEqualTo(expected);
    }


    @Test
    void foundPlanByName() {
        String name = PlanType.LIMITED.name();
        repository.save(limitedPlan);
        Optional<Plan> optionalPlan = repository.findByName(name);
        assertThat(optionalPlan.isPresent()).isTrue();
        assertThat(optionalPlan.get().getName()).isEqualTo(name);
    }
    @Test
    void notFoundPlanByName() {
        String name = PlanType.LIMITED.name();
        Optional<Plan> optionalPlan = repository.findByName(name);
        assertThat(optionalPlan.isEmpty()).isTrue();
    }
}