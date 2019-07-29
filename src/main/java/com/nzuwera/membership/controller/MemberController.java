package com.nzuwera.membership.controller;

import com.nzuwera.membership.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/member")
public class MemberController {

    @Autowired
    IMemberService memberService;


}
