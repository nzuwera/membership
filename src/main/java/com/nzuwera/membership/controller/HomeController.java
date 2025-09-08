package com.nzuwera.membership.controller;

import com.nzuwera.membership.dto.LoginForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("login")
    public String doLogin(@Valid LoginForm loginForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        // TODO: replace with real authentication
        // For now just echo email and redirect to a fake home page.
        model.addAttribute("userEmail", loginForm.getEmail());
        return "redirect:/home";
    }

    @GetMapping("home")
    public String home() {
        return "home"; // simple placeholder page we will add
    }

}
