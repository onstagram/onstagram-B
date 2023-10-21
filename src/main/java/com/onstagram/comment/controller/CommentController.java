package com.onstagram.comment.controller;

import com.onstagram.DtoData;
import com.onstagram.Member.service.MemberService;
import com.onstagram.comment.domain.CommentDto;
import com.onstagram.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    @PostMapping("/add") //댓글 등록
    public DtoData commentAdd(@RequestBody CommentDto commentDto,  HttpServletRequest request) {
        log.info("댓글 등록 시작");
        Long CommentUserId = memberService.getUserId(request);
        commentDto.userId(CommentUserId);
        return new DtoData(HttpStatus.OK.value(), true, commentService.add(commentDto));
    }

    //댓글 삭제
    @DeleteMapping("/delete/{commentId}")
    public HttpStatus commentDelete(@PathVariable("commentId") Long commentId) {
        log.info("댓글 삭제 시작");
        commentService.delete(commentId);
        return HttpStatus.OK;
    }




}