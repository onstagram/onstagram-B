package com.onstagram.Member.domain;


import com.onstagram.post.domain.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MypageDto { //마이페이지 정보(회원정보 & 팔로우 정보 & 게시물 정보)
    
    private MemberDto memberDto; //회원정보
    private List<PostDto> postDtoList; //게시물 정보
    private Long postCount; //게시물 개수
    //팔로우 정보
}