package com.example.loginApiStudy.Controller;

import com.example.loginApiStudy.Service.GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class GoogleLoginController {
    private final GoogleService googleService;

    @GetMapping("/login/google")
    public void googleLogin(@RequestParam String code, @RequestParam String registrationId) {
        googleService.googleLogin(code, registrationId);
    }
}
