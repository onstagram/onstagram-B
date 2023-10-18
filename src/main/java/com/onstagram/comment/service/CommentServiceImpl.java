package com.onstagram.comment.service;


import com.onstagram.comment.domain.CommentDto;
import com.onstagram.comment.entity.CommentEntity;
import com.onstagram.comment.repository.CommentRepository;
import com.onstagram.post.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override //게시물의 댓글 목록
    public List<CommentEntity> findAllById(Long postId) {
        System.out.println("게시물 아이디 ::::::::::::::: " + postId);
        return commentRepository.findAllByPostId(postId);
    }

    @Override //댓글 등록
    public Long save(CommentEntity commentEntity) {
        try {
            commentRepository.save(commentEntity);
            return commentEntity.getCommentId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    @Override
    public void delete(Long commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override //해당 댓글 정보
//    public Optional<CommentEntity> findById(Long commentId) {
//        try {
//            CommentEntity commentEntity = commentRepository.getById(commentId);
//            return Optional.of(commentRepository.getById(commentId)); //commentId값에 대한 데이터가 없으면 빈객체를 반환
//        } catch (Exception e) {
////            e.printStackTrace();
//            return Optional.empty(); //빈객체 반환
//        }
//    }

}