package com.onstagram.member.controller;

import com.onstagram.member.domain.JoinStatus;
import com.onstagram.member.domain.MemberDto;
import com.onstagram.member.entity.MemberEntity;
import com.onstagram.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Log4j2
//@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("join")
    public JoinStatus join(@Valid @RequestBody MemberDto memberDto, BindingResult result) {

        log.info("회원가입 실행");

        if(result.hasErrors()) {
            return JoinStatus.builder()
                    .status(401)
                    .errormessage("입력하지 않은 항목이 있습니다.")
                    .build();
        }

        MemberEntity memberEntity = MemberEntity.builder()
                .userName(memberDto.getUserName())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .userPhone(memberDto.getUserPhone())
                .build();

        // 회원 가입 로직을 수행합니다.
        try {
            memberService.join(memberEntity); // MemberService에서 회원 가입 처리
            return JoinStatus.builder()
                    .status(200)
                    .errormessage("회원가입 성공")
                    .memberEntity(memberEntity)
                    .build();

        } catch (Exception e) {
            return JoinStatus.builder()
                    .status(500)
                    .errormessage("회원가입에 실패했습니다.")
                    .build();
        }

    }

    @GetMapping("/user/fileForm")
    public String fileForm() {
        return "/user/fileForm";
    }


}