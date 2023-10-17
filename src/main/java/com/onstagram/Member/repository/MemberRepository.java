package com.onstagram.Member.repository;

import com.onstagram.Member.entity.MemberEntity;

import java.util.List;

public interface MemberRepository {
    public void save(MemberEntity memberEntity); // 회원가입 - ok

    public List<MemberEntity> findOneByEmail(String email); //아이디 중복체크 및 로그인




//    public MemberEntity findOneByEmail(String email); //아디디 중복체크
//    public MemberEntity findByName(String email); //아이디 중복 체크 - ok

//    public Optional<MemberEntity> findOneByEmail(String email);
//    public MemberEntity findOneByEmail(String email);
//    public List<MemberEntity> findbyEmail(String email);
//    public List<PostEntity> findbyId(Long id); //게시물 정보
//    public Long likeCount(Long postId);
//    public List<PostImgEntity> findByImgId(Long postId); //이미지 정보


}