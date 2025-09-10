package com.nzuwera.membership.controller;

import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.service.IMemberService;
import com.nzuwera.membership.utils.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
class MemberPageController {
    private static final Logger log = LoggerFactory.getLogger(MemberPageController.class);

    private final IMemberService memberService;

    @GetMapping
    String list(Model model) {
        ResponseObject response = memberService.findAllMembers();
        model.addAttribute("members", response.getData());
        return "members/list";
        }

    @GetMapping("/new")
    String createForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "members/new";
    }

    @PostMapping
    String create(@Valid @ModelAttribute("member") MemberDto memberDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Validation error: {}", result.getAllErrors());
            return "members/new";
        }
        memberService.createMember(memberDto);
        return "redirect:/members";
    }

    @GetMapping("/{email}")
    String view(@PathVariable String email, Model model) {
        ResponseObject response = memberService.findMemberByEmail(email);
        model.addAttribute("member", response.getData());
        return "members/view";
    }

    @PostMapping("/{email}")
    String update(@PathVariable String email, @Valid @ModelAttribute("member") MemberDto memberDto, BindingResult result) {
        if (result.hasErrors()) {
            return "members/view";
        }
        memberService.updateMember(email, memberDto);
        return "redirect:/members";
    }

    @PostMapping("/{email}/delete")
    String delete(@PathVariable String email) {
        memberService.deleteMember(email);
        return "redirect:/members";
    }
}
