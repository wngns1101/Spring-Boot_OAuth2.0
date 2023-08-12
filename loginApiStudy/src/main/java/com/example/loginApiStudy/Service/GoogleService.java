package com.example.loginApiStudy.Service;

import org.springframework.stereotype.Service;

@Service
public class GoogleService {
    public void googleLogin(String code, String registrationId) {
        System.out.println("code = " + code);
        System.out.println("registrationId = " + registrationId);
    }
}
