package com.onstagram.post.service;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;

public interface PostService {
    
    public MemberEntity findById(Long id); //게시물등록페이지에서 사용할 로그인한 회원 정보
    public Long cratePost(PostEntity postEntity); //게시물 정보 등록
    public Long uploadImgs(PostImgEntity postImgEntity); // 게시물 이미지 파일 정보 업로드

}