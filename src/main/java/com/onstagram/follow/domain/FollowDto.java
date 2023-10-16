package com.onstagram.follow.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
//@AllArgsConstructor
public class FollowDto {

    private Long followUserId; //좋아요한 회원 아이디(MEMBER 테이블 참조)
    private Long followingUserId; //좋아요한 게시물 아이디(POST 테이블 참조)
    private LocalDate likeDate; //좋아요한 날짜
 }
