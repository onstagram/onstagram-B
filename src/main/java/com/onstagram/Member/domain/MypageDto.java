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
}