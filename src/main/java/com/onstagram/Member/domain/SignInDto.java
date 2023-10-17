package com.onstagram.Member.domain;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SignInDto {
    private String email; //로그인 아이디 값
    private String password; //로그인 비밀번호 값
}
