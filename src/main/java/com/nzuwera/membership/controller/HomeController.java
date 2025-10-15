package com.nzuwera.membership.controller;

import com.nzuwera.membership.dto.LoginForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class HomeController {

    @Value("${spring.application.name}")
    String appName;

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
