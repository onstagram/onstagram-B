package com.onstagram.Member.controller;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.domain.MypageDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.service.MemberService;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
//@RequestMapping("/")
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("mypage/{id}") //회원아이디로 해당 회원의 페이지 정보
    public ResponseEntity<MypageDto> mypage(@PathVariable("id") Long id) {
        log.info("마이페이지 들어옴");
        try {
            log.info("회원정보 먼저 가져오기");
            //회원정보
            MemberDto memberDto = postService.findById(id);

            log.info("회원정보는 이름 : " + memberDto.getUserName() + ", email : " + memberDto.getEmail());
            log.info("회원의 게시물 정보 찾기 시작");
            //게시물 정보(게시물테이블에서 회원아이디로 정보 찾고, 이미지테이블에서 게시물 아이돌 이미지 정보 넣고)
            List<PostDto> postDtoList = memberService.findById(id); //해당 회원 게시물 목록 리스트(게시물정보 - 게시물 이미지 리스트)

            log.info("찾은 회원의 게시물의 개수" + postDtoList.size());

            MypageDto mypageDto = MypageDto.builder()
                    .memberDto(memberDto)
                    .postDtoList(postDtoList)
                    .postCount((long) postDtoList.size())
                    .build();

            return new ResponseEntity<>(mypageDto,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); //실패(400)
        }

    }

    @PostMapping("signup") //회원가입
    public HttpStatus join(@Valid @RequestBody MemberDto memberDto, BindingResult result) {

        if (result.hasErrors()) {
            log.info("이곳은 빈칸 에러입니다.");
            return HttpStatus.NO_CONTENT; //빈칸에러
        }

        boolean idCheck = memberService.validateDuplicateMember(memberDto.getEmail()); //아이디 중복체크(true : 중복x , false : 중복)

        if (!idCheck) {
            log.info("중복 아이디 존재");
            return HttpStatus.CONFLICT; //서버 충돌 에러(중복 = 일종의 서버 충돌)
        }

        MemberEntity memberEntity = memberDto.toEntity();

        try {
            memberService.join(memberEntity); //회원가입 시작
        } catch (Exception e) {
            e.printStackTrace();
            log.info("회원가입 실패");
            return HttpStatus.BAD_REQUEST; // 잘못된 요청
        }
        log.info("회원가입 성공");
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

    @GetMapping("modifyForm/{email}")
    public MemberDto modifyForm(@PathVariable("email") String email) {
        return memberService.findByEmail(email);
    }


    @PutMapping(value = "modify/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //회원정보 수정
    public HttpStatus modify(@PathVariable String email, MemberDto memberDto, @RequestParam MultipartFile userImg) {

        try {
            memberService.updateUser(email, memberDto, userImg);
            return HttpStatus.OK;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return HttpStatus.BAD_REQUEST;
        }
    }

}