package com.onstagram.post.repository;

import com.onstagram.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {

    @Query("SELECT p FROM PostEntity p WHERE p.postId = :postId") //게시물 아이디로 게시물 정보 조회
    public List<PostEntity> postList(@Param("postId") Long postId);

//    "A"가 "B"를 구독(팔로우)하는 경우:
//    "A"의 아이디가 팔로잉(following) 목록에 추가됩니다.
//    "B"의 아이디가 팔로워(follower) 목록에 추가됩니다.
//    @Query("SELECT DISTINCT f.followMemberEntity.userId FROM FollowEntity f WHERE f.followingMemberEntity.userId = :userId")
//    List<Long> findDistinctFollowingIds(@Param("userId") Long userId); //팔로잉한 아이디에 로그인 회원의 아이디를 넣어서 팔로우한 아이디 개수 찾기

}