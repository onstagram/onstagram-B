package com.onstagram.post.repository;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.comment.entity.CommentEntity;
import com.onstagram.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {

    @Query("SELECT p FROM PostEntity p WHERE p.postId = :postId") //게시물 아이디로 게시물 정보 조회
    public List<PostEntity> postList(@Param("postId") Long postId);

    @Query("SELECT p FROM PostEntity p WHERE p.memberEntity.userId <> :userId") //본인제외 모든 게시물 조회
    public List<PostEntity> findUsersWithUserIdNotEqualToOne(@Param("userId") Long userId);

    @Query("SELECT p FROM PostEntity p WHERE p.memberEntity.userId <> :userId") //본인 모든 게시물 조회
    public List<PostEntity> findUserPost(@Param("userId") Long userId);

    // 게시글(Post)과 작성자(Member) 정보를 함께 조회하는 쿼리
    @Query("SELECT p FROM PostEntity p JOIN FETCH p.memberEntity m WHERE p.postId = :postId")
    public Optional<PostEntity> findPostWithUserByPostId(@Param("postId") Long postId);

    @Query("SELECT c FROM CommentEntity c WHERE c.postEntity.postId = :postId")
    public List<CommentEntity> findByAllPostId(@Param("postId") Long postId); //게시글의 댓글 목록

    @Query("SELECT m FROM MemberEntity m WHERE m.userId = :userId")
    public List<MemberEntity> findByUserId(@Param("userId") Long userId); //게시글의 댓글 목록

    @Modifying
    @Query("DELETE FROM LikeEntity l WHERE l.postEntity.postId = :postId")
    public int deleteByPostId(@Param("postId") Long postId); // 게시글에 대한 좋아요 데이터 삭제

    @Modifying
    @Query("DELETE FROM CommentEntity c WHERE c.postEntity.postId = :postId")
    public int CommentDelete(@Param("postId") Long postId); // 게시글에 대한 댓글 데이터 삭제

    @Query("SELECT COUNT(l) FROM LikeEntity l WHERE l.postEntity.postId <> :postId") //좋아요 테이블에 있는 게시물 개수
    public int findByLikeId(@Param("postId") Long postId);

    @Query("SELECT COUNT(c) FROM CommentEntity c WHERE c.postEntity.postId <> :postId") //좋아요 테이블에 있는 게시물 개수
    public int findByCommentId(@Param("postId") Long postId);




//    "A"가 "B"를 구독(팔로우)하는 경우:
//    "A"의 아이디가 팔로잉(following) 목록에 추가됩니다.
//    "B"의 아이디가 팔로워(follower) 목록에 추가됩니다.
//    @Query("SELECT DISTINCT f.followMemberEntity.userId FROM FollowEntity f WHERE f.followingMemberEntity.userId = :userId")
//    List<Long> findDistinctFollowingIds(@Param("userId") Long userId); //팔로잉한 아이디에 로그인 회원의 아이디를 넣어서 팔로우한 아이디 개수 찾기

}