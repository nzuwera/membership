package com.nzuwera.membership.controller;

import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.service.IMemberService;
import com.nzuwera.membership.utils.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
class MemberPageController {

    private final IMemberService memberService;

    @GetMapping
    String listMembers(Model model) {
        ResponseObject response = memberService.findAllMembers();
        model.addAttribute("members", response.getData());
        model.addAttribute("activeNav", "members");
        model.addAttribute("pageTitle", "Members");
        return "members/list";
    }

    @GetMapping("/create")
    String showCreateForm(Model model) {
        model.addAttribute("member", new MemberDto());
        model.addAttribute("activeNav", "members");
        model.addAttribute("pageTitle", "New Member");
        return "members/new";
    }

    @PostMapping
    String createMember(@Valid @ModelAttribute("member") MemberDto memberDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("activeNav", "members");
            model.addAttribute("pageTitle", "New Member");
            return "members/new";
        }
        ResponseObject responseObject = memberService.createMember(memberDto);
        model.addAttribute("message", responseObject.getMessage());
        return "redirect:/members";
    }

    @GetMapping("/{email}")
    String viewMember(@PathVariable String email, Model model) {
        ResponseObject response = memberService.findMemberByEmail(email);
        model.addAttribute("member", response.getData());
        model.addAttribute("activeNav", "members");
        model.addAttribute("pageTitle", "View Member");
        return "members/view";
    }

    @GetMapping("/{email}/edit")
    String showEditForm(@PathVariable String email, Model model) {
        ResponseObject response = memberService.findMemberByEmail(email);
        model.addAttribute("member", response.getData());
        model.addAttribute("activeNav", "members");
        model.addAttribute("pageTitle", "Edit Member");
        return "members/new";
    }

    @PostMapping("/{email}/edit")
    String updateMember(@PathVariable String email, @Valid @ModelAttribute("member") MemberDto memberDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("activeNav", "members");
            model.addAttribute("pageTitle", "Edit Member");
            return "members/new";
        }
        ResponseObject responseObject = memberService.updateMember(email, memberDto);
        model.addAttribute("message", responseObject.getMessage());
        return "redirect:/members";
    }

    @PostMapping("/{email}/delete")
    String deleteMember(@PathVariable String email) {
        memberService.deleteMember(email);
        return "redirect:/members";
    }
}