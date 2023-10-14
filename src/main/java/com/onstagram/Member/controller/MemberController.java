package com.onstagram.Member.controller;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Log4j2
//@RequestMapping("/user")
public class MemberController {
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MemberService memberService;

    @PostMapping("/signup") //회원가입
    public ResponseEntity<MemberEntity> join(@Valid @RequestBody MemberDto memberDto, BindingResult result) {

        log.info("회원가입 실행");

        if (result.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);//400 에러코드
        }
        
        //비밀번호 암호화 시작
        String password = memberDto.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(password);//기존 비밀번호 암호화

        MemberEntity memberEntity = MemberEntity.builder()
                .userName(memberDto.getUserName())
                .email(memberDto.getEmail())
                .password(encodedPassword)
                .userPhone(memberDto.getUserPhone())
                .roles("USER") //USER라는 권한 부여
                .build();

        // 회원 가입 로직을 수행합니다.
        try {
            memberService.join(memberEntity); // MemberService에서 회원 가입 처리
            return new ResponseEntity<>(memberEntity, HttpStatus.OK); //200 성공

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); //400 에러코드
        }
    }

//    @PostMapping("join")
//    public JoinStatus join(@Valid @RequestBody UserDto userDto, BindingResult result) {
//
//        log.info("회원가입 실행");
//
//        if (result.hasErrors()) {
//            return JoinStatus.builder()
//                    .status(401)
//                    .errormessage("입력하지 않은 항목이 있습니다.")
//                    .build();
//        }
//
//        UserEntity userEntity = UserEntity.builder()
//                .userName(userDto.getUserName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .userPhone(userDto.getUserPhone())
//                .build();
//
//        // 회원 가입 로직을 수행합니다.
//        try {
//            userService.join(userEntity); // MemberService에서 회원 가입 처리
//            return JoinStatus.builder()
//                    .status(200)
//                    .errormessage("회원가입 성공")
//                    .userEntity(userEntity)
//                    .build();
//
//        } catch (Exception e) {
//            return JoinStatus.builder()
//                    .status(500)
//                    .errormessage("회원가입에 실패했습니다.")
//                    .build();
//        }
//
//    }

}