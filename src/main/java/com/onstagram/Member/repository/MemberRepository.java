package com.onstagram.Member.repository;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    public void save(MemberEntity memberEntity); // 회원가입
//    public Optional<MemberEntity> findOneByEmail(String email);
    public MemberEntity findOneByEmail(String email);
    public List<MemberEntity> findbyEmail(String email);
    public List<PostEntity> findbyId(Long id); //게시물 정보
    public Long likeCount(Long postId);
    public List<PostImgEntity> findByImgId(Long postId); //이미지 정보

    public Optional<MemberEntity> findByName(String email);
}