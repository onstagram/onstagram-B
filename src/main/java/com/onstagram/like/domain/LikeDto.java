package com.onstagram.like.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
//@AllArgsConstructor
public class LikeDto {

    private Long userId; //좋아요한 회원 아이디(MEMBER 테이블 참조)
    private Long postId; //좋아요한 게시물 아이디(POST 테이블 참조)
    private LocalDate likeDate; //좋아요한 날짜

}
