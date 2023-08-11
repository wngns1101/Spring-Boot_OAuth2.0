package com.example.loginApiStudy.Service;

import com.example.loginApiStudy.KakaoDTO;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpHeaders;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoService {
    String access_Token="";
    String refresh_Token ="";
    String postURL = "https://kauth.kakao.com/oauth/token";
    String getURL = "https://kapi.kakao.com/v2/user/me";

    @Value("${kakao-client-id}")
    private String KAKAO_CLIENT_KEY;
    public String getKakaoAccessToken(String code) throws Exception {
        if (code == null) throw new Exception("Faild get code");

        try {
            URL url = new URL(postURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + KAKAO_CLIENT_KEY); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:8080/login/kakao"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_Token: " + access_Token);
            System.out.println("refresh_Token: " + refresh_Token);

            br.close();
            bw.close();

            return access_Token;
        } catch (Exception e) {
            throw new Exception("API call failed");
        }
    }


    public void createKakaoUser(String token) throws Exception {
        if(token == null) throw new Exception("failed get Id_Token");
        try {
            URL url = new URL(getURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            String[] str = result.split(",");
            for (String i: str) {
                System.out.println(i);
            }
//            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

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

            br.close();

        } catch (Exception e) {
            throw new Exception("API call failed");
        }

    }
}
