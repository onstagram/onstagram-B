package com.onstagram.post.repository;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;

public interface PostRepository {

    public MemberEntity findById(Long id);
    public void createPost(PostEntity postEntity); //게시물 정보 등록
}