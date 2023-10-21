package com.onstagram.comment.repository;

import com.onstagram.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT c FROM CommentEntity c WHERE c.postEntity.postId = :postId")
    List<CommentEntity> findAllByPostId(@Param("postId") Long postId);//게시글의 댓글 목록
//
//    public void deleteByPostId(Long postId);//게시글 삭제

}