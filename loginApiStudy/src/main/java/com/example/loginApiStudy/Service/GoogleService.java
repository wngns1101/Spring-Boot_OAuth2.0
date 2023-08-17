package com.example.loginApiStudy.Service;

import com.example.loginApiStudy.Entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class GoogleService {

    @Value("${google-client-id}")
    private String GOOGLE_CLIENT_KEY;

    @Value("${google-redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    @Value("${google-secret-pw}")
    private String GOOGLE_SECRET_PW;

    String authorizeURL = "https://oauth2.googleapis.com";
    String resourceURL = "https://kapi.kakao.com";

    public void googleLogin(String code) {
        System.out.println("code = " + code);
    }

    public String requestAccessToken(String code) throws Exception {
        if(code == null) new Exception("Failed APi Call");
        try {
            WebClient webClient = WebClient.builder().baseUrl(authorizeURL).build();
            String token = webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/token")
                            .queryParam("grant_type", "authorization_code")
                            .queryParam("code", code)
                            .queryParam("client_id", GOOGLE_CLIENT_KEY)
                            .queryParam("client_secret", GOOGLE_SECRET_PW)
                            .queryParam("redirect_uri", GOOGLE_REDIRECT_URI)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return token;
        } catch (Exception e) {
            throw new Exception("API call failed");
        }
    }

//    public User requestResourceToken(String code) {
//        User user = User.createUser()
//    }
//
//    public void join(User user) {
//
//    }
}
