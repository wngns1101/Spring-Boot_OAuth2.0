package com.example.loginApiStudy.Service;

import org.springframework.stereotype.Service;

@Service
public class GoogleService {
    public void googleLogin(String code) {
        System.out.println("code = " + code);
    }
}
