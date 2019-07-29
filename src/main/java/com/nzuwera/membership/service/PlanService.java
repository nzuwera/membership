package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.message.Message;
import com.nzuwera.membership.repository.PlanRepository;
import com.nzuwera.membership.utils.ResponseObject;
import com.nzuwera.membership.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service(IPlanService.NAME)
public class PlanService implements IPlanService {

    @Autowired
    PlanRepository planRepository;

    @Value("${membership.expirydate}")
    private int membershipExpireDate;

    /**
     * Create new plan
     *
     * @param plan
     * @return
     */
    @Override
    public ResponseObject<Plan> createPlan(Plan plan) throws AlreadyExistsException {

        if (planRepository.existsByName(plan.getName())) {
            String message = String.format(Message.ALREADY_EXISTS, plan.getName());
            return new ResponseObject(false, message, plan.getName(), 600);
        } else {
            plan.setStartDate(new Date());
            if (plan.getType().equals(PlanType.LIMITED)) {
                plan.setEndDate(Utils.setEndDate(plan.getStartDate(), membershipExpireDate));
            }
            return new ResponseObject(true, Message.FOUND, planRepository.save(plan), 200);
        }

    }

    /**
     * update plan
     *
     * @param plan
     * @return
     */
    @Override
    public Plan updatePlan(Plan plan) {
        if (planRepository.existsByName(plan.getName())) {
            return planRepository.save(plan);
        } else {
            return plan;
        }
    }

    /**
     * delete plan
     *
     * @param plan
     */
    @Override
    public void deletePlan(Plan plan) {
        planRepository.delete(plan);
    }

    /**
     * return plan by name
     *
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public ResponseObject<Plan> getPlanByName(String name) throws NotFoundException {
        if (planRepository.existsByName(name)) {
            return new ResponseObject(true, Message.FOUND, planRepository.findByName(name), 200);
        } else {
            String message = String.format(Message.NOT_FOUND, name);
            return new ResponseObject(false, message, name, 400);
        }
    }

    /**
     * return all plans
     *
     * @return
     */
    @Override
    public ResponseObject<List<Plan>> findAllPlan() {
        return new ResponseObject(true, Message.FOUND, planRepository.findAll(), 200);
    }
}
