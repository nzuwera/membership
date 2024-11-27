package com.nzuwera.membership.restapi;

import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.service.IMemberService;
import com.nzuwera.membership.utils.ResponseObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/member")
@RequiredArgsConstructor
public class MemberController {


    private final IMemberService memberService;

    @PostMapping(path = "/create-member")
    public ResponseEntity<ResponseObject> createNewMember(@RequestBody MemberDto memberDto) throws AlreadyExistsException {
        ResponseObject responseObject = memberService.createMember(memberDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }
}