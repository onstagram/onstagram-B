package com.onstagram.post.domain;

import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostStatus {

    private int Status; //상태 코드
    private String message; //상태 메시지
    private PostEntity postEntity; //게시물 정보
    private List<PostImgEntity> postImgEntityList; //게시물 이미지 정보
}
