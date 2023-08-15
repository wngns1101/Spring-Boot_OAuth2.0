package com.example.loginApiStudy.DTO;

import com.example.loginApiStudy.Enums.JoinGender;
import com.example.loginApiStudy.Enums.JoinType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Data
public class UserDto {
    private JoinType regularUserDto;
    private JoinType kakaoUserDto;
    private JoinType googleUserDto;
    private String userDtoId;
    private String userDtoPw;
    private String userDtoName;
    private String userDtoEmail;
    private String userDtoPhone;
    private JoinGender userDtoGender;

}
