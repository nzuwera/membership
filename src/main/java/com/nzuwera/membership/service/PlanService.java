package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.repository.PlanRepository;
import com.nzuwera.membership.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(IPlanService.NAME)
public class PlanService implements IPlanService {

    @Autowired
    PlanRepository planRepository;


    @Override
    public Plan createPlan(Plan plan) {
        if (plan.getType().equals(PlanType.LIMITED) && plan.getEndDate().equals(null)) {
            plan.setEndDate(Utils.setEndDate(plan.getStartDate(),30));
        }
        return planRepository.save(plan);
    }

    @Override
    public Plan updatePlan(Plan plan) {
        if (planRepository.existsByName(plan.getName())) {
            return planRepository.save(plan);
        } else {
            return plan;
        }
    }

    @Override
    public Plan getPlanByName(String name) throws Exception {
        if (planRepository.existsByName(name)) {
            return planRepository.findByName(name);
        } else {
            throw new Exception();
        }
    }
}
