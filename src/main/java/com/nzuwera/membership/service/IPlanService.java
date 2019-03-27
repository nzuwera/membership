package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Plan;

import java.util.List;
import java.util.UUID;

public interface IPlanService {
    String NAME = "PlanService";
    Plan createPlan(Plan plan);
    Plan updatePlan(Plan plan);
    Plan getPlanByName(String name);
    void deletePlan(Plan plan);
    List<Plan> findAllPlan();
}
