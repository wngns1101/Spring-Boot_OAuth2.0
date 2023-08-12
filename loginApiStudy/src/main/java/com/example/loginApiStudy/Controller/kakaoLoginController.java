package com.example.loginApiStudy.Controller;

import com.example.loginApiStudy.Service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class kakaoLoginController {
    private final KakaoService kakaoService;

    @Value("${kakao-client-id}")
    private String KAKAO_CLIENT_KEY;

    @Value("${google-client-id}")
    private String GOOGLE_CLIENT_KEY;

    @Value("${google-redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("apiKey", KAKAO_CLIENT_KEY);
        model.addAttribute("google", GOOGLE_CLIENT_KEY);
        model.addAttribute("uri", GOOGLE_REDIRECT_URI);
        return "home";
    }

    @GetMapping("/login/kakao")
    public void kakaoCallBack(@RequestParam String code) throws Exception {
        System.out.println(code);
        String kakaoAccessToken = kakaoService.getKakaoAccessToken(code);
        kakaoService.createKakaoUser(kakaoAccessToken);
    }
}
