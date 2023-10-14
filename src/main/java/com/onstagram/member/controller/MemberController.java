package com.onstagram.member.controller;

import com.onstagram.member.domain.MemberDto;
import com.onstagram.member.entity.MemberEntity;
import com.onstagram.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("join") //회원가입
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

    @GetMapping("users") //전체 회원 출력
    public ResponseEntity<List<MemberEntity>> usersList() {
        List<MemberEntity> users = memberService.findUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }


}