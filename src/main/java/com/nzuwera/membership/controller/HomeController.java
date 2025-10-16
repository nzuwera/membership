package com.nzuwera.membership.controller;

import com.nzuwera.membership.dto.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
class HomeController {

    @GetMapping("login")
    String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("activeNav", "home");
        model.addAttribute("pageTitle", "Home");
        return "home";
    }

}
