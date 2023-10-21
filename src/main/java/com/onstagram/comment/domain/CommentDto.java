package com.onstagram.comment.domain;

import com.onstagram.comment.entity.CommentEntity;
import lombok.*;

import java.time.LocalDate;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long commentId; //댓글 아이디
    private Long userId; //회원 아이디
    private Long postId; //게시물 아이디
    private String content; //댓글 내용
    private LocalDate commentDate; //댓글 생성일
    private String userImg; //회원 이미지

    @Builder
    public CommentDto(CommentEntity commentEntity) {
        commentId = commentEntity.getCommentId();
        userId = commentEntity.getMemberEntity().getUserId();
        postId = commentEntity.getPostEntity().getPostId();
        content = commentEntity.getContent();
        commentDate = commentEntity.getCommentDate();
    }

    public void userImg(String img) {
        userImg = img;
    }

    public void userId(Long CommentUserId) {
        userId = CommentUserId;
    }

}