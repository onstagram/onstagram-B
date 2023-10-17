package com.onstagram.post.controller;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.file.FileService;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.service.PostService;
import com.onstagram.status;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
//게시물 등록(ok)
//게시물 상세정보 페이지(ing)
//게시물 수정 페이지(ing)
//게시물 수정(ing)
//게시물 삭제(ing)
//팔로우한 계정 게시물만 조회(ing)
//탐색탭 ->좋아요 많은 순서대로 게시물 조회z
public class PostController {

    private final PostService postService;
    private final FileService fileService;

    @GetMapping("/postForm/{postId}") //게시물 등록 페이지(회원정보 리턴)
    public MemberDto postForm(@PathVariable("postId") Long postId) {
        log.info("게시물 등록 페이지 들어옴(계정 주인 정보 페이지에 전송)");
        return postService.findById(postId);
    }

//    @GetMapping("postModifyForm/{postId}") //게시물 수정 페이지
//    public PostStatus postModifyForm(@PathVariable("postId") Long postId) {
//
//        return null;
//    }
    
    //게시물 목록(로그인한 사람 게시물만 조회)
    @GetMapping("postList/{userId}")
    public ResponseEntity<List<PostDto>> postList(@PathVariable("userId") Long userId) {









        //try-catch문으로 하기

        //먼저 리턴할 List<PostDto>를 만든다.
        List<PostDto> postDtoList = new ArrayList<>();
        //findById를 통해서 내가 팔로우(구독)한 사람 계정을 먼저 전부 조회
        //List<Long> IdList = findById
        //for(Long id :IdList) {
        //여기서 해당 아이디를 통해서 해당 아이디의 PostDto를 받아오고
        //postDtoList.add(PostDto)를 한다.
        //}
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    //게시물 등록
    @PostMapping(value="/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HttpStatus register(@RequestParam("file") MultipartFile file, @ModelAttribute PostDto postDto) {

        log.info("게시물 등록 들어옴");

        //회원아이디 회원 엔터티에 저장
        MemberEntity memberEntity = MemberEntity.builder().userId(postDto.getUserId()).build();

        //게시물 사진 업로드 시작
        //이미지 파일인지 확인
        if (!file.getContentType().startsWith("image")) {
            return HttpStatus.BAD_REQUEST;
        }
        //S3에 파일 업로드
        String postImg = fileService.FileUpload(file,"post");

        PostEntity postEntity = PostEntity.builder()
                .caption(postDto.getCaption()) //게시물 설명
                .memberEntity(memberEntity) //게시물 작성한 회원 아이디(엔터티로 넣음)
                .postImg(postImg) // 게시물을 등록함
                .likeCount(0L) // 좋아요 개수 초기값으 0으로 지정
                .build();

        PostEntity postDB = postService.cratePost(postEntity); //게시물 등록
//        PostDto postDto1 = PostDto.builder().postEntity(postDB).build(); //게시물 등록한 entity정보를 dto에 담아서 리턴

        if(postDB != null) { // 성공
            return HttpStatus.OK;

        } else { //실패
            return HttpStatus.BAD_REQUEST;

        }

    }







}