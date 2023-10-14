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
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("signup") //회원가입
    public HttpStatus join(@Valid @RequestBody MemberDto memberDto, BindingResult result) {

        if (result.hasErrors()) {
            return HttpStatus.NO_CONTENT; //빈칸에러
        }

        boolean idCheck = memberService.validateDuplicateMember(memberDto.getEmail()); //아이디 중복체크(true : 중복x , false : 중복)

        if (!idCheck) {
            return HttpStatus.CONFLICT; //서버 충돌 에러(중복 = 일종의 서버 충돌)
        }

        MemberEntity memberEntity = memberDto.toEntity();

        try {
            memberService.join(memberEntity); //회원가입 시작
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST; // 잘못된 요청
        }
        return HttpStatus.OK;
    }

    @PostMapping("/login") //로그인
    public ResponseEntity<String> login(@RequestBody MemberEntity memberEntity) {

        try {

            //아이디 체크
            boolean IdCheck = memberService.validateDuplicateMember(memberEntity.getEmail());
            if(IdCheck) { //true:아이디 존재x
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }

            //로그인 아이디 존재할 경우
            //비밀번호 체크
            boolean passwordCheck = memberService.checkPassword(memberEntity);

            if(passwordCheck) {
                //토큰생성
                return ResponseEntity.ok("로그인 성공");
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 중 오류가 발생했습니다.");
        }

    }

}