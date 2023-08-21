package com.example.loginApiStudy.Api;

import com.example.loginApiStudy.DTO.GoogleLoginResponse;
import com.example.loginApiStudy.DTO.GoogleTokenRequestDto;
import com.example.loginApiStudy.Entity.User;
import com.example.loginApiStudy.Service.GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class GoogleJoinApi {
    private final GoogleService googleService;

    @GetMapping("/login/google")
    public void googleLogin(@RequestParam(name = "code") String code) throws Exception {
        System.out.println(code);

        String accessToken = googleService.requestAccessToken(code);
        System.out.println(accessToken);
        User user = googleService.requestResourceToken(accessToken);
        System.out.println(user);
        googleService.join(user);
    }
}
