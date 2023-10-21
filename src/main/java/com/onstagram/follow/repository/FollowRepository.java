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
    public int countFollowers(@Param("userId") Long userId);


    //팔로잉 개수
    @Query("SELECT COUNT(f) FROM FollowEntity f WHERE f.followingMemberEntity.userId = :userId")
    public int countFollowing(@Param("userId") Long userId);


    //팔로우 여부 확인(팔로우 관계) loginId가userId를 팔로우했는지
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FollowEntity f " +
            "WHERE f.followingMemberEntity.userId = :userId AND f.followMemberEntity.userId = :loginId")
    boolean followCheck(@Param("loginId") Long loginId, @Param("userId") Long userId);



}