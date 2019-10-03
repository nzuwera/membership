package com.nzuwera.membership.controller;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.service.IPlanService;
import com.nzuwera.membership.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/plan")
public class PlanController {

    @Autowired
    private IPlanService planService;

    /**
     * Find plan by name
     *
     * @param name
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/get-plan/{name}")
    public ResponseEntity<ResponseObject<Plan>> findPlanByName(@PathVariable String name) throws NotFoundException {
        ResponseObject foundPlan = planService.getPlanByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(foundPlan);
    }

    /**
     * Find all plans
     *
     * @return
     */
    @GetMapping(path = "/get-plans")
    public ResponseEntity<ResponseObject<List<Plan>>> findAllPlans() {
        return ResponseEntity.status(HttpStatus.OK).body(planService.findAllPlan());
    }

    /**
     * Add new Plan
     *
     * @param planName
     * @param planType
     * @return
     */
    @PostMapping(path = "/create-plan")
    public ResponseEntity<ResponseObject<Plan>> createPlan(String planName, String planType) throws AlreadyExistsException {
        Plan newPlan = new Plan();
        newPlan.setId(UUID.randomUUID());
        newPlan.setName(planName);
        newPlan.setType(PlanType.valueOf(planType));
        ResponseObject<Plan> createdPlan = planService.createPlan(newPlan);
        return ResponseEntity.status(HttpStatus.OK).body(createdPlan);
    }
}
