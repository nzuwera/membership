package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.repository.PlanRepository;
import com.nzuwera.membership.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service(IPlanService.NAME)
public class PlanService implements IPlanService {

    @Autowired
    PlanRepository planRepository;

    @Value("${membership.expirydate}")
    private int membershipExpireDate;


    @Override
    public Plan createPlan(Plan plan) {
        plan.setStartDate(new Date());
        if (plan.getType().equals(PlanType.LIMITED)) {
            plan.setEndDate(Utils.setEndDate(plan.getStartDate(), membershipExpireDate));
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
    public void deletePlanById(UUID id) {
        Plan toBeDeleted = planRepository.getOne(id);
        planRepository.delete(toBeDeleted);
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
