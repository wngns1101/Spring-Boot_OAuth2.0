package com.example.loginApiStudy.Service;

import com.example.loginApiStudy.Entity.User;
import com.example.loginApiStudy.Repository.KakaoRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class KakaoService {
    String access_Token="";
    String refresh_Token ="";
    String authorizeURL = "https://kauth.kakao.com";
    String resourceURL = "https://kapi.kakao.com";

    private final KakaoRepository kakaoRepository;

    @Value("${kakao-client-id}")
    private String KAKAO_CLIENT_KEY;
    public String getKakaoAccessToken(String code) throws Exception {
        if (code == null) throw new Exception("Faild get code");
        try {
            WebClient webClient = WebClient.builder().baseUrl(authorizeURL).build();
            String token = webClient.get()
                    .uri("/oauth/token?grant_type=authorization_code&client_id=" + KAKAO_CLIENT_KEY
                            + "&redirect_uri=http://localhost:8080/login/kakao"
                            + "&code=" + code)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println(token);

            // JSON 파싱을 통해 Access Token 및 Refresh Token 추출
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(token);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            return access_Token;
        } catch (Exception e) {
            throw new Exception("API call failed");
        }
    }


    public User receiveKakaoUser(String token) throws Exception {
        if(token == null) throw new Exception("failed get Id_Token");
        try {
            WebClient webClient = WebClient.builder().baseUrl(resourceURL).defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();

            String resource = webClient.get()
                    .uri("/v2/user/me")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println(resource);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(resource);

            Long id = element.getAsJsonObject().get("id").getAsLong();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            System.out.println("id : " + id);
            System.out.println("email : " + email);
            System.out.println("nickname : " + nickname);

            User dto = User.createUser(nickname, email, "kakao");

            return dto;
        } catch (Exception e) {
            throw new Exception("API call failed");
        }
    }

    @Transactional
    public void joinUser(User user){
        kakaoRepository.join(user);
    }
}
