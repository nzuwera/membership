package com.nzuwera.membership.controller;

import com.nzuwera.membership.dto.ChangePasswordRequest;
import com.nzuwera.membership.dto.MemberDto;
import com.nzuwera.membership.dto.UpdateProfileInfoRequest;
import com.nzuwera.membership.service.IMemberService;
import com.nzuwera.membership.utils.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // user profile page
    @GetMapping("/profile/{username}")
    public String profile(Model model, @PathVariable String username) {
        MemberDto memberDto = (MemberDto) memberService.findMemberByEmail(username).getData();
        model.addAttribute("profileInfo", UpdateProfileInfoRequest.builder()
                .firstName(memberDto.getFirstName())
                .lastName(memberDto.getLastName())
                .email(memberDto.getEmail())
                .dateOfBirth(memberDto.getDateOfBirth())
                .build());
        model.addAttribute("passwordInfo", new ChangePasswordRequest());
        model.addAttribute("activeNav", "profile");
        model.addAttribute("pageTitle", "Profile");
        return "profile";
    }

    // update user profile
    @PostMapping("/profile/{username}")
    public String updateProfile(@PathVariable String username,
                                @Valid @ModelAttribute("profileInfo") UpdateProfileInfoRequest profileInfoRequest,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("passwordInfo", new ChangePasswordRequest());
            model.addAttribute("activeNav", "profile");
            model.addAttribute("pageTitle", "Profile");
            return "profile";
        }
        ResponseObject responseObject = memberService.updateInfo(username, profileInfoRequest);
        MemberDto memberDto = (MemberDto) responseObject.getData();
        model.addAttribute("profileInfo", UpdateProfileInfoRequest.builder()
                .firstName(memberDto.getFirstName())
                .lastName(memberDto.getLastName())
                .email(memberDto.getEmail())
                .dateOfBirth(memberDto.getDateOfBirth())
                .build());
        // Use RedirectAttributes instead of Model
        redirectAttributes.addFlashAttribute("message", responseObject.getMessage());
        return "redirect:/members/profile/" + username;
    }

    // change password
    @PostMapping("/profile/{username}/change-password")
    public String changePassword(@PathVariable String username,
                                 @Valid @ModelAttribute("passwordInfo") ChangePasswordRequest changePasswordRequest,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        // Re-add profileInfo when validation fails
        MemberDto memberDto = (MemberDto) memberService.findMemberByEmail(username).getData();
        model.addAttribute("profileInfo", UpdateProfileInfoRequest.builder()
                .firstName(memberDto.getFirstName())
                .lastName(memberDto.getLastName())
                .email(memberDto.getEmail())
                .dateOfBirth(memberDto.getDateOfBirth())
                .build());
        if (result.hasErrors()) {
            model.addAttribute("activeNav", "profile");
            model.addAttribute("pageTitle", "Profile");
            return "profile";
        }
        ResponseObject responseObject = memberService.updatePassword(username, changePasswordRequest);
        // Check if the operation was successful
        if (responseObject.isStatus()) {
            redirectAttributes.addFlashAttribute("message", responseObject.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("error", responseObject.getMessage());
        }
        return "redirect:/members/profile/" + username;
    }
}