package com.onstagram.post.domain;

import com.onstagram.post.entity.PostEntity;
import lombok.*;

import java.util.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long postId; //게시물 아이디

    private Long userId; //회원테이블 참조(외래키)

    @NotEmpty(message = "게시물 설명은 작성")
    private String caption; //게시물 설명
    
    private String postImg; //게시물 사진

    private Long likeCount; //좋아요 개수

    private LocalDate postDate; //게시물 생성일

    @Builder
    public PostDto(PostEntity postEntity) {
        postId = postEntity.getPostId();
        userId = postEntity.getMemberEntity().getUserId();
        caption = postEntity.getCaption();
        postImg = postEntity.getPostImg();
        likeCount = postEntity.getLikeCount();
        postDate = postEntity.getPostDate();
    }

    public void modifyDto(PostModifyDto postModifyDto) {
        caption = postModifyDto.getCaption();
    }

}