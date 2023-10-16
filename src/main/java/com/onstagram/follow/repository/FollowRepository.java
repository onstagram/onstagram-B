package com.onstagram.follow.repository;

import com.onstagram.follow.entity.FollowEntity;
import com.onstagram.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity,Long> {

    List<FollowEntity> findByFollowingUserId(MemberEntity user);
    List<FollowEntity> findByFollowUserId(MemberEntity user);
    }

