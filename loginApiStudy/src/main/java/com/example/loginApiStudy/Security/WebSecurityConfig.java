package com.example.loginApiStudy.Security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        // 컨트롤러에서 파일 화일명을 return하여 페이지를 이동하는 경우 추가해야한다.
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated() // 어떠한 요청이라도 인증필요
                )
                .formLogin(login -> login
//                        .defaultSuccessUrl("/", true) // 성공했을 때 root로 컨트롤러 매핑
//                        .permitAll()
                        .loginPage("/login")
                                .loginProcessingUrl("/login-process")
                                .usernameParameter("userid")
                                .passwordParameter("userpw")
                                .defaultSuccessUrl("/", true)
                                .permitAll()
                );
        return http.build();
    }
}
