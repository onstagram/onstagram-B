package com.onstagram.comment.service;


import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.repository.MemberRepository;
import com.onstagram.comment.domain.CommentDto;
import com.onstagram.comment.entity.CommentEntity;
import com.onstagram.comment.repository.CommentRepository;
import com.onstagram.exception.OnstagramException;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDto add(CommentDto commentDto) { //댓글 등록

        try {
            //회원정보 Entity
            MemberEntity memberEntity = memberRepository.findById(commentDto.getUserId()).get(0);

            //게시글 Entity
            PostEntity postEntity = postRepository.getReferenceById(commentDto.getPostId());

            CommentEntity commentEntity = CommentEntity.builder()
                    .memberEntity(memberEntity)
                    .postEntity(postEntity)
                    .content(commentDto.getContent())
                    .build();

            commentRepository.save(commentEntity); //댓글 등록
            return CommentDto.builder().commentEntity(commentEntity).build();

        } catch (Exception e) {
          e.printStackTrace();
          throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "댓글 등록 실패");
        }

    }

    @Override //게시물의 댓글 목록
    public List<CommentEntity> findAllById(Long postId) {
        System.out.println("게시물 아이디 ::::::::::::::: " + postId);
        return commentRepository.findAllByPostId(postId);
    }

    @Override //댓글 삭제
    public void delete(Long commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}