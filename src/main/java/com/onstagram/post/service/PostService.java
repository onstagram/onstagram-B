package com.onstagram.post.service;

import com.onstagram.post.domain.PostDto;
import com.onstagram.post.domain.PostInfoDto;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    public Long cratePost(PostEntity postEntity); //게시물 정보 등록
    public Long uploadImgs(PostImgEntity postImgEntity); // 게시물 이미지 파일 정보 업로드

}