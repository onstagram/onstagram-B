package com.onstagram.Member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowInfo {

    //팔로우 정보
    private int follow; //팔로우 개수(팔로우 테이블에서 follow  = user_id)
    private int following; //팔로잉 개수(folowing = user_id)
    private int check; //0 : 로그인==이동페이지 1:구독버튼(팔로우), 2: 구독취소버튼(팔로잉)
    //1 -> 구독버튼 : 팔로우실행
    //2 -> 구독취소버튼 : 팔로우 삭제

}