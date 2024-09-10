package com.nzuwera.membership.restapi;

import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.domain.PlanType;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.service.IPlanService;
import com.nzuwera.membership.utils.ResponseObject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/plans")
@RequiredArgsConstructor
public class PlanController {

    private final IPlanService planService;

    /**
     * Find plan by name
     *
     * @param name plan name
     * @return ResponseEntity<ResponseObject < Plan>>
     * @throws NotFoundException NotFoundException
     */
    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Plan>> findAllPlans() {
        return ResponseEntity.status(HttpStatus.OK).body(planService.findAllPlan());
    }

    /**
     * Add new Plan
     *
     * @param planName plan name
     * @param planType plan type
     * @return ResponseEntity<ResponseObject>
     */
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> createPlan(String planName, String planType) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Plan newPlan = new Plan();
            newPlan.setName(planName);
            newPlan.setType(PlanType.valueOf(planType));
            Plan createdPlan = planService.createPlan(newPlan);
            responseObject = new ResponseObject(true, "Plan created successfully", createdPlan, HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        } catch (EntityExistsException ex) {
            return ResponseEntity.status(responseObject.getErrorCode()).body(new ResponseObject(ex));
        }
    }
}
