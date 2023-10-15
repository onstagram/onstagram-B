package com.onstagram.like.controller;


import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.like.domain.LikeDto;
import com.onstagram.like.entity.LikeEntity;
import com.onstagram.like.service.LikeService;
import com.onstagram.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("post")
public class LikeController {

    private final LikeService likeService;

    //게시물 정보 페이지에서 좋아요 하트 클릭시 1 -> remove, 0 -> addLike
    @PostMapping("addLike") //좋아요 하기
    public ResponseEntity<LikeEntity> addLike(@RequestBody LikeDto likeDto) {

        log.info("좋아요 하기");

        LikeEntity likeEntity = LikeEntity.builder()
                .memberEntity(MemberEntity.builder().id(likeDto.getUserId()).build())
                .postEntity(PostEntity.builder().postId(likeDto.getPostId()).build())
                .build();
        try {
            likeService.addLike(likeEntity);
            return new ResponseEntity<>(likeEntity,HttpStatus.OK); //성공(200)
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST); //실패(400)
        }

    }

    @PostMapping("removeLike") //여기서도 LikeEntity 리턴해줘야 되는지 확인해야함
    public HttpStatus removeLike(@RequestBody LikeEntity likeEntity) {

        log.info("좋아요 취소 하기");

        try {
            likeService.removeLike(likeEntity.getLikeId());
            return HttpStatus.OK; //성공(200)
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST; //실패(400)
        }
    }





}