package com.onstagram.comment.controller;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.comment.domain.CommentDto;
import com.onstagram.comment.entity.CommentEntity;
import com.onstagram.comment.service.CommentService;
import com.onstagram.post.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
        //받은 이메일로 기존 회원 정보 받아오고
        //받아온 기존 회원 정보에 새로운 정보만 추가해서 저장
        //그러를 db에 새로 저장하고 return

        CommentEntity commentEntity = CommentEntity.builder()
                .commentId(commentDto.getCommentId())
                .memberEntity(MemberEntity.builder().userId(commentDto.getUserId()).build())
                .postEntity(PostEntity.builder().postId(commentDto.getPostId()).build())
                .content(commentDto.getContent())
                .commentDate(LocalDate.now())//수정 날짜
                .build();

        commentService.save(commentEntity); //db에 저장
        return new ResponseEntity<>(CommentDto.builder().commentEntity(commentEntity).build(),HttpStatus.OK);
    }

    //댓글 삭제
    @DeleteMapping("/delete/{commentId}")
    public HttpStatus commentDelete(@PathVariable("commentId") Long commentId) {
        log.info("댓글 삭제 시작");
        commentService.delete(commentId);
        return HttpStatus.OK;
    }




}