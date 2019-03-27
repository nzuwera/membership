package com.nzuwera.membership.controller;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.service.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(path = "/plan")
public class PlanController {

    @Autowired
    private IPlanService planService;

    @GetMapping(path = "/get-plan/{name}")
    public Plan findPlanByName(@PathVariable String name) throws Exception{
        return planService.getPlanByName(name);
    }

    @PostMapping(path = "/create-plan")
    public Plan createPlan(String planName,String planType){
        Plan newPlan = new Plan();
        newPlan.setId(UUID.randomUUID());
        newPlan.setName(planName);
        newPlan.setType(PlanType.valueOf(planType));
        return planService.createPlan(newPlan);
    }
}
