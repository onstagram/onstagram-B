package com.onstagram.post.service;

import com.onstagram.Member.domain.MemberDetail;
import com.onstagram.Member.domain.MemberDto;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.entity.PostEntity;

import java.util.Optional;

public interface PostService {
    
    public PostEntity findById(Long id); //게시물등록페이지에서 사용할 로그인한 회원 정보
    public PostEntity cratePost(PostEntity postEntity); //게시물 정보 등록


}