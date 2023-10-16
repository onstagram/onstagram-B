package com.onstagram.follow.service;

import com.onstagram.follow.entity.FollowEntity;

import java.util.List;

public interface FollowService {

    FollowEntity createFollow(Long followerId, Long followingId);

    void deleteFollow(Long followId);

    List<FollowEntity> getFollowers(Long userId);

    List<FollowEntity> getFollowing(Long userId);
}
