package com.onstagram.Member.domain;


import com.onstagram.post.domain.PostDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MypageDto { //마이페이지 정보(회원정보 & 팔로우 정보 & 게시물 정보)
    
    private MemberDto memberDto; //회원정보
    private List<PostDto> postDtoList; //게시물 정보
    //팔로우 정보
    private Long follow; //팔로우 개수(팔로우 테이블에서 follow  = user_id)
    private Long following; //팔로잉 개수(folowing = user_id)
    private int check; //0 : 로그인==이동페이지 1:구독버튼(팔로우), 2: 구독취소버튼(팔로잉)
    //1 -> 구독버튼 : 팔로우실행
    //2 -> 구독취소버튼 : 팔로우 삭제

    public void followCheck(int followcheck) {
        check = followcheck;
    }
}