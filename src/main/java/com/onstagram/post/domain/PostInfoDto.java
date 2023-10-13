package com.onstagram.post.domain;

import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostInfoDto {
    private PostEntity postEntity; //게시물 정보
    private List<PostImgEntity> postImgEntityList; //게시물 이미지 정보
}
