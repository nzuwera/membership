package com.nzuwera.membership.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {

    @Value("${spring.application.name}")
    String appName;

    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }
}
