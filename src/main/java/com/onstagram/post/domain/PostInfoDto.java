package com.onstagram.post.domain;


import com.onstagram.Member.domain.MemberDto;
import com.onstagram.comment.domain.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostInfoDto { //게시물 상세페이지 정보
    private MemberDto memberDto; //해당 회원의 정보
    private PostDto postDto; //게시물
    private CommentDto commentDto; //댓글 정보
}