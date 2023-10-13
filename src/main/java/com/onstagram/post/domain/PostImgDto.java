package com.onstagram.post.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostImgDto {

    private String uploadImgs; //사진
    private Long postId; //게시물 참조(외래키)

}