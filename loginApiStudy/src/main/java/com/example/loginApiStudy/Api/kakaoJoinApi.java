package com.example.loginApiStudy.Api;

import com.example.loginApiStudy.Entity.User;
import com.example.loginApiStudy.Service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class kakaoJoinApi {

    private final KakaoService kakaoService;
    private final UserRepository userRepository;

    @GetMapping("/login/kakao")
    public String kakaoCallResource(@RequestParam("code") String code) throws Exception {
        System.out.println(code);
        String resourceToken = kakaoService.getKakaoAccessToken(code);
        User user = kakaoService.receiveKakaoUser(resourceToken);
        kakaoService.joinUser(user);
        return "success";
    }
}
