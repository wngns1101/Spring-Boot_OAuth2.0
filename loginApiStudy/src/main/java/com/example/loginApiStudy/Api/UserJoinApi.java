package com.example.loginApiStudy.Api;

import com.example.loginApiStudy.DTO.UserDto;
import com.example.loginApiStudy.Entity.User;
import com.example.loginApiStudy.Repository.UserRepository;
import com.example.loginApiStudy.Service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserJoinApi {

    private final KakaoService kakaoService;
    private final UserRepository userRepository;


    @GetMapping("/login/kakao")
    public String kakaoCallBack(@RequestParam String code, Model model) throws Exception {
        System.out.println(code);
        String kakaoAccessToken = kakaoService.getKakaoAccessToken(code);
        User user = kakaoService.receiveKakaoUser(kakaoAccessToken);

        kakaoService.joinUser(user);
        return "success";
    }

}
