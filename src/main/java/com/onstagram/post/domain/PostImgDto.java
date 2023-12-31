package com.onstagram.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostImgDto {

    private Long imgId;//이미지 아이디
    private String imgName; //사진
    private Long postId; //게시물 참조(외래키)

}