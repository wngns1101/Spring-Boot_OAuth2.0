package com.example.loginApiStudy.Controller;

import com.example.loginApiStudy.DTO.GoogleLoginResponse;
import com.example.loginApiStudy.DTO.GoogleTokenRequestDto;
import com.example.loginApiStudy.Service.GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
public class GoogleLoginController {
    private final GoogleService googleService;

    @Value("${google-client-id}")
    private String GOOGLE_CLIENT_KEY;

    @Value("${google-redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    @Value("${google-secret-pw}")
    private String GOOGLE_SECRET_PW;

    @GetMapping("/login/google")
    public void googleLogin(@RequestParam(name = "code") String code) {
        GoogleTokenRequestDto googleTokenRequestDto = GoogleTokenRequestDto.builder().clientId(GOOGLE_CLIENT_KEY).clientSecret(GOOGLE_SECRET_PW).code(code).redirectUri(GOOGLE_REDIRECT_URI).grantType("authorization_code").build();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GoogleLoginResponse> apiResponse = restTemplate.postForEntity("https://oauth2.googleapis.com" + "/token", googleTokenRequestDto, GoogleLoginResponse.class);
        GoogleLoginResponse googleLoginResponse = apiResponse.getBody();


        System.out.println(googleLoginResponse.toString());

        String googleToken = googleLoginResponse.getId_token();

        //5.받은 토큰을 구글에 보내 유저정보를 얻는다.
        String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com" + "/tokeninfo").queryParam("id_token",googleToken).toUriString();

        String resultJson = restTemplate.getForObject(requestUrl, String.class);
        System.out.println(resultJson);
    }
}
