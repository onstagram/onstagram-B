package com.onstagram.post.repository;

import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;

public interface PostRepository {
    public void createPost(PostEntity postEntity); //게시물 정보 등록
    public void uploadImgs(PostImgEntity postImgEntity); // 게시물 이미지 파일 정보 업로드
}