package com.onstagram.Member.controller;

import com.onstagram.Member.domain.MemberDetail;
import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.domain.SignInDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.service.MemberService;
import com.onstagram.jwt.JwtService;
import com.onstagram.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Log4j2
//회원가입(ok)
//로그인은 ok(+토큰,시큐리티)
//로그아웃
//마이페이지(ing)
//회원정보 수정 페이지(ok)
//회원정보 수정(ok) -> 파일명만 어덕해 하기
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;

//    @GetMapping("mypage/{id}") //회원아이디로 해당 회원의 페이지 정보
//    public ResponseEntity<MypageDto> mypage(@PathVariable("id") Long id) {
//        log.info("마이페이지 들어옴");
//        try {
//            log.info("회원정보 먼저 가져오기");
//            //회원정보
//            MemberDto memberDto = postService.findById(id);
//
//            log.info("회원정보는 이름 : " + memberDto.getUserName() + ", email : " + memberDto.getEmail());
//            log.info("회원의 게시물 정보 찾기 시작");
//            //게시물 정보(게시물테이블에서 회원아이디로 정보 찾고, 이미지테이블에서 게시물 아이돌 이미지 정보 넣고)
//            List<PostDto> postDtoList = memberService.findById(id); //해당 회원 게시물 목록 리스트(게시물정보 - 게시물 이미지 리스트)
//
//            log.info("찾은 회원의 게시물의 개수" + postDtoList.size());
//
//            MypageDto mypageDto = MypageDto.builder()
//                    .memberDto(memberDto)
//                    .postDtoList(postDtoList)
//                    .postCount((long) postDtoList.size())
//                    .build();
//
//            return new ResponseEntity<>(mypageDto,HttpStatus.OK);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); //실패(400)
//        }
//
//    }

    @PostMapping("signup") //회원가입(ok)
    public HttpStatus join(@Valid @RequestBody MemberDetail memberDto, BindingResult result) {

        if (result.hasErrors()) {
            log.info("이곳은 빈칸 에러입니다.");
            return HttpStatus.BAD_REQUEST; //에러코드(400)
        }

        boolean idCheck = memberService.IdCheck(memberDto.getEmail()); //아이디 중복체크(true : 중복 , false : 중복X)

        if (idCheck) {
            log.info("중복 아이디 존재");
            return HttpStatus.BAD_REQUEST; //에러코드(400)
        }

        MemberEntity memberEntity = memberDto.toEntity();

        try {
            memberService.join(memberEntity); //회원가입 시작
            log.info("회원가입 성공");
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("회원가입 실패");
            return HttpStatus.BAD_REQUEST; // 잘못된 요청
        }

    }

    @PostMapping("/login") //로그인-(ok)
    public ResponseEntity<String> login(@RequestBody SignInDto signinDto) {
        log.info("로그인 시작");
        try {
            String token = memberService.signin(signinDto); //토큰 받아오기
            log.info("토큰값 : " + token);
            if(token != null) {
                HttpHeaders httpHeaderseaders = new HttpHeaders();
                httpHeaderseaders.set("TOKEN", token);
                return new ResponseEntity<>("success", httpHeaderseaders, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("fail", null,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }


    @GetMapping("/user/modifyForm")//회원정보 수정 페이지 @PathVariable("email") String email,
    public ResponseEntity<MemberDto> modifyForm(HttpServletRequest request) {
        log.info("회원정보 페이지");
        try {
            String email = memberService.getEmail(request); // token을 통해서 User의 id를 뽑아오는 메서드
            MemberDto memberDto = memberService.findByEmail(email);
            return new ResponseEntity<>(memberDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private final JwtService jwtService;
    @PutMapping(value = "/user/modify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //회원정보 수정
    public HttpStatus modify(MemberDto memberDto, @RequestParam MultipartFile file, HttpServletRequest request) {
        memberDto.setEmail(memberService.getEmail(request)); // token을 통해서 User의 id를 뽑아오는 메서드
        log.info("회원수정에 들어왔습니다.");
        return memberService.updateUser(memberDto, file) != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

}