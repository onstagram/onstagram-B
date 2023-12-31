package com.onstagram.Member.service;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.domain.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberService {

    public Long join(MemberEntity memberEntity); //회원가입
    public boolean validateDuplicateMember(String email); //아이디(이메일) 중복 체크
    public boolean checkPassword(MemberEntity memberEntity);
    public List<PostDto> findById(Long id); //해당 계정 회원의 모든 게시물

    public MemberDto findByEmail(String email); //회원 정보

    public void updateUser(String email, MemberDto memberDto, MultipartFile newImg); //회원정보수정

}