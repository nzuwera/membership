package com.nzuwera.membership.service;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.message.Message;
import com.nzuwera.membership.repository.PlanRepository;
import com.nzuwera.membership.utils.ResponseObject;
import com.nzuwera.membership.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService implements IPlanService {

    private final PlanRepository planRepository;

    @Autowired
    public PlanService(PlanRepository planRepository) {
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
    public Plan createPlan(Plan plan) {
        Optional<Plan> optional = planRepository.findByName(plan.getName());
        if (optional.isPresent())
            return optional.get();
        plan.setStartDate(new Date());
        if (plan.getType().equals(PlanType.LIMITED)) {
            plan.setEndDate(Utils.setEndDate(plan.getStartDate(), membershipExpireDate));
        }
        return planRepository.save(plan);
    }

    /**
     * update plan
     *
     * @param plan plan to be updated
     */
    @Override
    public void updatePlan(Plan plan) {
        planRepository.findByName(plan.getName()).ifPresent(plan1 -> {
            planRepository.save(plan);
        });

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
    public ResponseObject getPlanByName(String name) {
        Optional<Plan> exists = planRepository.findByName(name);
        if (exists.isPresent()) {
            return new ResponseObject(true, Message.FOUND, exists.get(), HttpStatus.OK.value());
        } else {
            String message = String.format(Message.NOT_FOUND, name);
            return new ResponseObject(new NotFoundException(message));
        }
    }

    /**
     * return all plans
     *
     * @return ResponseObject<List < Plan>>
     */
    @Override
    public List<Plan> findAllPlan() {
        return planRepository.findAll();
    }
}
