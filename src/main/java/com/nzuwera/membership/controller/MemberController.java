package com.nzuwera.membership.controller;

import com.nzuwera.membership.domain.Member;
import com.nzuwera.membership.domain.Plan;
import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import com.nzuwera.membership.service.IMemberService;
import com.nzuwera.membership.service.IPlanService;
import com.nzuwera.membership.utils.ResponseObject;
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

    @Autowired
    IMemberService memberService;

    @Autowired
    IPlanService planService;

    @PostMapping(path = "/create-member")
    public ResponseEntity<Member> createNewMember(@RequestBody MemberDto memberDto) throws NotFoundException, AlreadyExistsException {
        ResponseObject<Plan> plan = planService.getPlanByName(memberDto.getPlanName());
        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setLastName(memberDto.getLastName());
        member.setFirstName(memberDto.getFirstName());
        member.setEmail(memberDto.getEmail());
        member.setPlan(plan.getData());
        member.setDateOfBirth(memberDto.getDateOfBirth());
        Member createdMember = memberService.createMember(member);
        return ResponseEntity.status(HttpStatus.OK).body(createdMember);
    }
}