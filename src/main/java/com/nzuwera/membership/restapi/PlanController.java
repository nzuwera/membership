package com.nzuwera.membership.restapi;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.service.IPlanService;
import com.nzuwera.membership.utils.ResponseObject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/plan")
public class PlanController {

    private final IPlanService planService;

    @Autowired
    public PlanController(IPlanService planService) {
        this.planService = planService;
    }

    /**
     * Find plan by name
     *
     * @param name plan name
     * @return ResponseEntity<ResponseObject   <   Plan>>
     * @throws NotFoundException NotFoundException
     */
    @GetMapping(path = "/get-plan/{name}")
    public ResponseEntity<ResponseObject> findPlanByName(@PathVariable String name) throws NotFoundException {
        ResponseObject responseObject = new ResponseObject();
        try {
            responseObject = planService.getPlanByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        } catch (EntityNotFoundException ex) {
            responseObject.setData(ex);
            responseObject.setErrorCode(404);
            responseObject.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
        }
    }

    /**
     * Find all plans
     *
     * @return ResponseEntity<ResponseObject>
     */
    @GetMapping(path = "/get-plans")
    public ResponseEntity<ResponseObject> findAllPlans() {
        return ResponseEntity.status(HttpStatus.OK).body(planService.findAllPlan());
    }

    /**
     * Add new Plan
     *
     * @param planName plan name
     * @param planType plan type
     * @return ResponseEntity<ResponseObject>
     */
    @PostMapping(path = "/create-plan")
    public ResponseEntity<ResponseObject> createPlan(String planName, String planType) throws AlreadyExistsException {
        ResponseObject responseObject = new ResponseObject();
        try {
            Plan newPlan = new Plan();
            newPlan.setId(UUID.randomUUID());
            newPlan.setName(planName);
            newPlan.setType(PlanType.valueOf(planType));
            responseObject = planService.createPlan(newPlan);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        } catch (EntityExistsException ex) {
            responseObject.setData(ex);
            responseObject.setErrorCode(409);
            responseObject.setMessage(new AlreadyExistsException(planName).getMessage());
            return ResponseEntity.status(responseObject.getErrorCode()).body(responseObject);
        }
    }
}
