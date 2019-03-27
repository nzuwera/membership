package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Plan;

public interface IPlanService {
    String NAME = "PlanService";
    Plan createPlan(Plan plan);
    Plan updatePlan(Plan plan);
    Plan getPlanByName(String name) throws Exception;
}
