package com.nzuwera.membership.controller;

import com.nzuwera.membership.dto.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class HomeController {

    @GetMapping("login")
    String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

}
