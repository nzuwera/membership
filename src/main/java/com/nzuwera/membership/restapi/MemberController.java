package com.nzuwera.membership.restapi;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.service.IMemberService;
import com.nzuwera.membership.service.IPlanService;
import com.nzuwera.membership.utils.ResponseObject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/member")
public class MemberController {


    private final IMemberService memberService;
    private final IPlanService planService;

    @Autowired
    public MemberController(IMemberService memberService, IPlanService planService) {
        this.memberService = memberService;
        this.planService = planService;
    }

    @PostMapping(path = "/create-member")
    public ResponseEntity<ResponseObject> createNewMember(@RequestBody MemberDto memberDto) throws NotFoundException, AlreadyExistsException {
        try {
            ResponseObject plan = planService.getPlanByName(memberDto.getPlanName());
            Member member = new Member();
            member.setId(UUID.randomUUID());
            member.setLastName(memberDto.getLastName());
            member.setFirstName(memberDto.getFirstName());
            member.setEmail(memberDto.getEmail());
            member.setPlan((Plan) plan.getData());
            member.setDateOfBirth(memberDto.getDateOfBirth());
            ResponseObject createdMember = memberService.createMember(member);
            return ResponseEntity.status(HttpStatus.OK).body(createdMember);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(new NotFoundException(memberDto.getEmail())));
        } catch (EntityExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject(new AlreadyExistsException(memberDto.getEmail())));
        }
    }
}