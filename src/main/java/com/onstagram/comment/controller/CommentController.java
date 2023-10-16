package com.onstagram.comment.controller;

import com.onstagram.comment.domain.CommentDto;
import com.onstagram.comment.entity.CommentEntity;
import com.onstagram.comment.service.CommentService;
import com.onstagram.member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDto> createReply(@RequestBody CommentDto commentDto) {

        CommentEntity commentEntity = CommentEntity.builder()
                .memberEntity(MemberEntity.builder().id(commentDto.getUserId()).build())
                .postEntity(PostEntity.builder().id(commentDto.getPostId()).build())
                .content(commentDto.getContent())
                .build();




    return null;
    }


}
