package com.onstagram.follow.service;

import com.onstagram.follow.entity.FollowEntity;
import com.onstagram.follow.repository.FollowRepository;
import com.onstagram.member.entity.MemberEntity;
import com.onstagram.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Override
    public FollowEntity createFollow(Long followerId, Long followingId) {
        MemberEntity follower = new MemberEntity();
        follower.setId(followerId);

        MemberEntity following = new MemberEntity();
        following.setId(followingId);

        FollowEntity follow = new FollowEntity();
        follow.setFollowUserId(follower);
        follow.setFollowingUserId(following);

        return followRepository.save(follow);
    }

    @Override
    public void deleteFollow(Long followId) {
        followRepository.deleteById(followId);
    }

    @Override
    public List<FollowEntity> getFollowers(Long userId) {
        MemberEntity user = new MemberEntity();
        user.setId(userId);

        return followRepository.findByFollowingUserId(user);
    }

    @Override
    public List<FollowEntity> getFollowing(Long userId) {
        MemberEntity user = new MemberEntity();
        user.setId(userId);

        return followRepository.findByFollowUserId(user);
    }
}
