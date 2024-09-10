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

@Service
public class PlanService implements IPlanService {

    private final PlanRepository planRepository;

    @Autowired
    public PlanService(PlanRepository planRepository){
        this.planRepository = planRepository;
    }

    @Value("${app.membership.expiry-date}")
    private int membershipExpireDate;

    private Boolean exists;

    /**
     * Create new plan
     *
     * @param plan new plan
     * @return created ResponseObject<Plan>
     */
    @Override
    public ResponseObject createPlan(Plan plan) throws AlreadyExistsException {
        exists = planRepository.existsByName(plan.getName());
        if (Boolean.TRUE.equals(exists)) {
            String message = String.format(Message.ALREADY_EXISTS, plan.getName());
            throw new AlreadyExistsException(message);
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
     * @param plan plan to be updated
     */
    @Override
    public void updatePlan(Plan plan) throws NotFoundException {
        exists = planRepository.existsByName(plan.getName());
        if (Boolean.TRUE.equals(exists)) {
            planRepository.save(plan);
        } else {
            throw new NotFoundException(plan.getName());
        }
    }

    /**
     * delete plan
     *
     * @param plan plan to be deleted
     */
    @Override
    public void deletePlan(Plan plan) {
        planRepository.delete(plan);
    }

    /**
     * return plan by name
     *
     * @param name plan name
     * @return ResponseObject<Plan>
     * @throws NotFoundException NotFoundException
     */
    @Override
    public ResponseObject getPlanByName(String name) throws NotFoundException {
        exists = planRepository.existsByName(name);
        if (Boolean.TRUE.equals(exists)) {
            return new ResponseObject(true, Message.FOUND, planRepository.findByName(name), 200);
        } else {
            String message = String.format(Message.NOT_FOUND, name);
            throw new NotFoundException(message);
        }
    }

    /**
     * return all plans
     *
     * @return ResponseObject<List<Plan>>
     */
    @Override
    public ResponseObject findAllPlan() {
        return new ResponseObject(true, Message.FOUND, planRepository.findAll(), 200);
    }
}
