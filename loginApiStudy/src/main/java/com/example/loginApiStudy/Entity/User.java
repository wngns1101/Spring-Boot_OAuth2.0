package com.example.loginApiStudy.Entity;

import com.example.loginApiStudy.Enums.JoinGender;
import com.example.loginApiStudy.Enums.JoinType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "userKey")
    private int userKey;
    @Column(name = "regularUser")
    private JoinType regularUser;
    @Column(name = "kakaoUser")
    private JoinType kakaoUser;
    @Column(name = "googleUser")
    private JoinType googleUser;
    @Column(name = "userId")
    private String userId;
    @Column(name = "userPw")
    private String userPw;
    @Column(name = "userName")
    private String userName;
    @Column(name = "userEmail")
    private String userEmail;
    @Column(name = "userPhone")
    private String userPhone;
    @Column(name = "userGender")
    private JoinGender userGender;

    public static User createUser(String name, String email) {
        User user = new User();
        user.kakaoUser = JoinType.Y;
        user.googleUser = JoinType.N;
        user.regularUser = JoinType.N;
        user.userEmail = email;
        user.userName = name;
        return user;
    }
}
