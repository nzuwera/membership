package com.nzuwera.membership.controller;

import com.nzuwera.membership.domain.MemberStatus;
import com.nzuwera.membership.domain.Role;
import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.service.IMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
class RegistrationController {

    private final IMemberService memberService;

    @GetMapping
    String showForm(Model model) {
        MemberDto dto = new MemberDto();
        dto.setRole(Role.USER);
        dto.setStatus(MemberStatus.ACTIVE);
        model.addAttribute("member", dto);
        return "register";
    }

    @PostMapping
    String register(@Valid @ModelAttribute("member") MemberDto memberDto, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        // Enforce self-registration constraints: ACTIVE status and USER role only
        memberDto.setRole(Role.USER);
        memberDto.setStatus(MemberStatus.ACTIVE);
        memberService.createMember(memberDto);
        return "redirect:/login";
    }
}
