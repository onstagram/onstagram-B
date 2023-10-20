package com.onstagram.follow.repository;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.follow.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<FollowEntity,Long> {


    //팔로우 개수
    @Query("SELECT COUNT(f) FROM FollowEntity f WHERE f.followMemberEntity.userId = :userId")
    public Long countFollowers(@Param("userId") Long userId);


    //팔로잉 개수
    @Query("SELECT COUNT(f) FROM FollowEntity f WHERE f.followingMemberEntity.userId = :userId")
    public Long countFollowing(@Param("userId") Long userId);




}