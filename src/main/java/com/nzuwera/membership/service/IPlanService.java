package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.utils.ResponseObject;

import java.util.List;

public interface IPlanService {

    Plan createPlan(Plan plan);

    void updatePlan(Plan plan);

    ResponseObject getPlanByName(String name) ;

    void deletePlan(Plan plan);

    List<Plan> findAllPlan();
}
