package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.utils.ResponseObject;

public interface IPlanService {

    ResponseObject createPlan(Plan plan) throws AlreadyExistsException;

    void updatePlan(Plan plan) throws NotFoundException;

    ResponseObject getPlanByName(String name) throws NotFoundException;

    void deletePlan(Plan plan);

    ResponseObject findAllPlan();
}
