package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.utils.ResponseObject;

import java.util.List;

public interface IPlanService {
    String NAME = "PlanService";

    ResponseObject<Plan> createPlan(Plan plan) throws AlreadyExistsException;

    Plan updatePlan(Plan plan);

    ResponseObject getPlanByName(String name) throws NotFoundException;

    void deletePlan(Plan plan);

    ResponseObject<List<Plan>> findAllPlan();
}
