package com.onstagram.post.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostInfoDto {

    private PostDto postDto; //게시물
    private Long likeCount; //게시물 좋아요 개수
    //댓글 정보

}