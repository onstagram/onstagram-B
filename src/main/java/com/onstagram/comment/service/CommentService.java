package com.onstagram.comment.service;

import com.onstagram.comment.domain.CommentDto;
import com.onstagram.comment.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentService {


    public CommentDto add(CommentDto commentDto); //댓글 등록
    public List<CommentEntity> findAllById(Long postId); //게시물의 댓글 목록
    public void delete(Long commentId); //댓글 삭제
}
