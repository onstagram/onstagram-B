package com.onstagram.Member.domain;

import com.onstagram.Member.entity.MemberEntity;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
//@Builder
//@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long userId;

    @NotEmpty(message = "이메일은 필수")
    private String email;

    @NotEmpty(message = "비밀번호는 필수")
    private String password;

    @NotEmpty(message = "회원이름은 필수")
    private String userName;

    @NotEmpty(message = "핸드폰번호는 필수")
    private String userPhone;

    private String userImg; //회원 이미지

    private String introduction; //회원 소개

    private LocalDate userDate;

    @Builder
    public MemberDto(MemberEntity memberEntity) {
        userId = memberEntity.getUserId();
        email = memberEntity.getEmail();
        password = memberEntity.getPassword();
        userName = memberEntity.getUserName();
        userPhone = memberEntity.getUserPhone();
        userImg = memberEntity.getUserImg();
        introduction = memberEntity.getIntroduction();
        userDate = memberEntity.getUserDate();
    }

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .userPhone(userPhone)
                .build();
    }

}