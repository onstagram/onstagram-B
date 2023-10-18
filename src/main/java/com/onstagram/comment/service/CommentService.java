package com.onstagram.comment.service;

import com.onstagram.comment.domain.CommentDto;
import com.onstagram.comment.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    public List<CommentEntity> findAllById(Long postId); //게시물의 댓글 목록
    public Long save(CommentEntity commentEntity); //댓글 등록
//    public Optional<CommentEntity> findById(Long commentId); //해당 댓글 정보
    public void delete(Long commentId);
}
