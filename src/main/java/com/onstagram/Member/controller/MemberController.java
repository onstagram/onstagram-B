package com.onstagram.Member.controller;

import com.onstagram.DtoData;
import com.onstagram.Member.domain.*;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.service.MemberService;
import com.onstagram.exception.OnstagramException;
import com.onstagram.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("signup") //회원가입(ok)
    public DtoData join(@Valid @RequestBody MemberDetail memberDto, BindingResult result) {

        if (result.hasErrors()) {
            log.info("이곳은 빈칸 에러입니다.");
            throw new OnstagramException(HttpStatus.NO_CONTENT.value(), "입력안된 값이 있습니다.");
//            return HttpStatus.BAD_REQUEST; //에러코드(400)
        }

        boolean idCheck = memberService.IdCheck(memberDto.getEmail()); //아이디 중복체크(true : 중복 , false : 중복X)

        if (idCheck) {
            log.info("중복 아이디 존재");
            throw new OnstagramException(HttpStatus.CONFLICT.value(), "아이디 중복"); //409에러
//            return HttpStatus.CONFLICT; //에러코드(400)
        }

        MemberEntity memberEntity = memberDto.toEntity();

        try {
            memberService.join(memberEntity); //회원가입 시작
            log.info("회원가입 성공");
            return new DtoData(HttpStatus.OK.value(), true, MemberDto.builder()
                                                                        .memberEntity(memberEntity)
                                                                        .build());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("회원가입 실패");
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "가입 실패");
        }

    }

    @GetMapping("mypage/{userId}") //회원아이디로 해당 회원의 페이지 정보
    public DtoData mypage(@PathVariable("userId") Long userId, HttpServletRequest request) {
        log.info("마이페이지 들어옴");

        Long loginId = memberService.getUserId(request); //로그인한 유저의 아이디

        //해당 게시글 작성자의 회원 정보, 게시글 정보
        MypageDto mypageDto = memberService.profileInfo(userId);
        Long pageId = mypageDto.getMemberDto().getUserId(); //프로필 주인
        int check = loginId.equals(pageId) ? 0 : memberService.followCheck(userId,loginId);
//        if(loginId.equals(pageId)) {
//            log.info("로그인 회원 = 페이지 회원");
//            check = 0;
//        }
//        else {
//            check = memberService.followCheck(userId,loginId);
//        }
        mypageDto.followCheck(check);
        return new DtoData(HttpStatus.OK.value(), true, mypageDto);
    }

    @PostMapping("/login") //로그인-(ok)
    public ResponseEntity<String> login(@RequestBody SignInDto signinDto) {
        String token = memberService.signin(signinDto); //토큰 받아오기
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("setting/edit/{userId}")//회원정보 수정 페이지 @PathVariable("userId") Long userId
    public DtoData modifyForm(@PathVariable("userId") Long userId) {
        log.info(userId + "의 회원정보");
        MemberDto memberDto = memberService.findById(userId);
        if(memberDto == null) {
            return new DtoData(HttpStatus.BAD_REQUEST.value(), false, memberDto);
        }
        return new DtoData(HttpStatus.OK.value(), true, memberDto);
    }

    @PutMapping(value = "setting/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //회원정보 수정
    public DtoData modify(ModifyDto modifyDto, @RequestParam MultipartFile file, HttpServletRequest request) {
        request.getHeader("");
        log.info("회원수정에 들어왔습니다.");

        Long userId = memberService.getUserId(request); //토큰으로 회원 아이디 받아오기
        MemberDto memberDto = memberService.updateUser(userId, modifyDto, file);

        return new DtoData(HttpStatus.OK.value(), true, memberDto);
    }

}