package com.onstagram.follow.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowDto {

    private Long followId;
    private Long followUserId; //구독(팔로우)당한 사람
    private Long followingUserId; //구독(팔로우)한 사람
    private LocalDate followDate; //팔로우 하고 당한 날짜

}