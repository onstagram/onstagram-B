package com.onstagram.comment.controller;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.comment.domain.CommentDto;
import com.onstagram.comment.entity.CommentEntity;
import com.onstagram.comment.service.CommentService;
import com.onstagram.post.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add") //댓글 등록
    public HttpStatus commentAdd(@RequestBody CommentDto commentDto) {
        log.info("댓글 등록 시작");

        CommentEntity commentEntity = CommentEntity.builder()
                .memberEntity(MemberEntity.builder().userId(commentDto.getUserId()).build())
                .postEntity(PostEntity.builder().postId(commentDto.getPostId()).build())
                .content(commentDto.getContent())
                .build();

        Long result = commentService.save(commentEntity);
        return result > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    //댓글 수정
    @PutMapping("/modify")
    public ResponseEntity<CommentDto> commentModify(@RequestBody CommentDto commentDto) {

        log.info("댓글 수정 시작");

//        Optional<CommentEntity> commentOptional = commentService.findById(commentDto.getCommentId());
//        CommentEntity commentEntity = commentOptional.orElse(null); // 빈객체일 경우 null
//        commentEntity.setContent(commentDto.getContent());
//        commentEntity.setCommentDate(LocalDate.now());

        CommentEntity commentEntity = CommentEntity.builder()
                .commentId(commentDto.getCommentId())
                .memberEntity(MemberEntity.builder().userId(commentDto.getUserId()).build())
                .postEntity(PostEntity.builder().postId(commentDto.getPostId()).build())
                .content(commentDto.getContent())
                .commentDate(LocalDate.now())//수정 날짜
                .build();

        Long result = commentService.save(commentEntity);
        return new ResponseEntity<>(CommentDto.builder().commentEntity(commentEntity).build(),HttpStatus.OK);
    }

    //댓글 삭제





}