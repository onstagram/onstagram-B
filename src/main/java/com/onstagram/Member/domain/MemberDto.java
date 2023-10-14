package com.onstagram.Member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MemberDto {

    @NotEmpty(message = "회원이름은 필수")
    private String userName;

    @NotEmpty(message = "이메일은 필수")
    private String email;

    @NotEmpty(message = "비밀번호는 필수")
    private String password;

    @NotEmpty(message = "핸드폰번호는 필수")
    private String userPhone;

    private String roles; //권한

    private String userImg; //회원 이미지

    private String introduction; //회원 소개

    private LocalDate userDate;

}