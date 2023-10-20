package com.onstagram.Member.service;

import com.onstagram.Member.domain.*;
import com.onstagram.Member.entity.MemberEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {

    public Long getUserId(HttpServletRequest request); //토큰 받아오기
    public Long join(MemberEntity memberEntity); //회원가입
    public boolean IdCheck(String email); //아이디(이메일) 중복 체크
    public String signin(SignInDto signInDto); //로그인(토큰값 반환)
    public MemberDto findById(Long userId); //회원 정보
    public MemberDto updateUser(Long userId, ModifyDto modifyDto, MultipartFile file); //회원정보수정

    public MypageDto profileInfo(Long userId); //해당 프로필의 정보

//    public List<PostDto> findById(Long id); //해당 계정 회원의 모든 게시물
//
//

}