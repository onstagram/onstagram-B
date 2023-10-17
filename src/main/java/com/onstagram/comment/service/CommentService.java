package com.onstagram.comment.service;

import com.onstagram.comment.entity.CommentEntity;

import java.util.Optional;

public interface CommentService {
    
    public Long save(CommentEntity commentEntity); //댓글 등록
//    public Optional<CommentEntity> findById(Long commentId); //해당 댓글 정보
    public void delete(Long commentId);
}
