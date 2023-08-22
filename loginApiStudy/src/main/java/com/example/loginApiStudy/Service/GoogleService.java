package com.example.loginApiStudy.Service;

import com.example.loginApiStudy.Entity.User;
import com.example.loginApiStudy.Repository.GoogleRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class GoogleService {

    @Value("${google-client-id}")
    private String GOOGLE_CLIENT_KEY;

    @Value("${google-redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    @Value("${google-secret-pw}")
    private String GOOGLE_SECRET_PW;

    String authorizeURL = "https://oauth2.googleapis.com";
    String resourceURL = "https://www.googleapis.com";

    private final GoogleRepository googleRepository;

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
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(token);
            String accessToken = jsonElement.getAsJsonObject().get("access_token").getAsString();
            return accessToken;
        } catch (Exception e) {
            throw new Exception("API call failed");
        }
    }

    public User requestResourceToken(String token) {
        WebClient webClient = WebClient.builder().baseUrl(resourceURL).defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
        String resource = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2/v2/userinfo")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(resource);
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(resource);
        String email = jsonElement.getAsJsonObject().get("email").getAsString();
        String name = jsonElement.getAsJsonObject().get("name").getAsString();

        User user = User.createUser(name, email, "google");
        return user;
    }

    @Transactional
    public void join(User user) {
        googleRepository.join(user);
    }
}
