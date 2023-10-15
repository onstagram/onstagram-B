package com.onstagram.member.controller;

import com.onstagram.member.domain.MemberDto;
import com.onstagram.member.domain.ModifyDto;
import com.onstagram.member.domain.SigninDto;
import com.onstagram.member.entity.MemberEntity;
import com.onstagram.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/signin") //로그인
    public ResponseEntity<String> validatePassword(@RequestBody SigninDto signinDto) {
        boolean validatePassword = memberService.checkPassword(signinDto);

        if (validatePassword) {
            return ResponseEntity.ok("비밀번호가 일치합니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }
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

    @GetMapping("userinfo/{name}") // 이름으로 회원정보출력
    public ResponseEntity<List<MemberEntity>> userinfo(@PathVariable String name) {
        List<MemberEntity> usersByName = memberService.findByName(name);

        if (usersByName.isEmpty()) { // 이름에 맞는 데이터가 없다면
            return new ResponseEntity<>(usersByName, HttpStatus.NOT_FOUND);
        }

        // 만약 이름에 맞는 데이터가 존재한다면, HttpStatus.OK와 데이터를 반환
        return new ResponseEntity<>(usersByName, HttpStatus.OK);
//        return ResponseEntity.status(HttpStatus.OK).body(usersByName);
    }

    @PutMapping(value = "modify/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //회원정보 수정
    public HttpStatus modify(@PathVariable String email, ModifyDto modifyDto, @RequestParam MultipartFile uploadProfileImg) {

        try {
            memberService.updateUser(email, modifyDto, uploadProfileImg);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    @GetMapping("search/{name}") // 찾기(oracle like 써서)
    public ResponseEntity<List<MemberEntity>> searchUser(@PathVariable String name) {
        List<MemberEntity> usersByName = memberService.findByNameWithLike(name);

        if (usersByName.isEmpty()) { // 이름에 맞는 데이터가 없다면
            return new ResponseEntity<>(usersByName, HttpStatus.NOT_FOUND);
        }

        // 만약 이름에 맞는 데이터가 존재한다면, HttpStatus.OK와 데이터를 반환
        return new ResponseEntity<>(usersByName, HttpStatus.OK);
    }
}