package com.onstagram.Member.service;

import com.onstagram.Member.domain.MemberDetail;
import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.domain.SignInDto;
import com.onstagram.Member.entity.MemberEntity;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {

    public String getEmail(HttpServletRequest request); //토큰 받아오기
    public Long join(MemberEntity memberEntity); //회원가입
    public boolean IdCheck(String email); //아이디(이메일) 중복 체크
    public String signin(SignInDto signInDto); //로그인(토큰값 반환)
    public MemberDto findByEmail(String email); //회원 정보
//    public void updateUser(MemberDto memberDto, MultipartFile newImg); //회원정보수정 --> S3후 하기

//    public List<PostDto> findById(Long id); //해당 계정 회원의 모든 게시물
//
//

}