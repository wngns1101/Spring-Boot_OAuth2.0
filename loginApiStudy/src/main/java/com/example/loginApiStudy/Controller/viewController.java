package com.example.loginApiStudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class viewController {
    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-process")
    public String loginProcess() {
        return "success";
    }
}
