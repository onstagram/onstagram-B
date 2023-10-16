package com.onstagram.post.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;//버킷이름

    private final PostService postService;

    @GetMapping("/postForm/{postId}") //게시물 등록 페이지(회원정보 리턴)
    public MemberDto postForm(@PathVariable("postId") Long postId) {
        log.info("게시물 등록 페이지 들어옴(계정 주인 정보 페이지에 전송)");
        return postService.findById(postId);
    }

    @GetMapping("postModifyForm/{postId}") //게시물 수정 페이지
    public HttpStatus postModifyForm(@PathVariable("postId") Long postId) {

        return HttpStatus.OK;
    }
    
    //게시물 목록(로그인한 사람 게시물만 조회)
    @GetMapping("postList")
    public ResponseEntity<List<PostDto>> postList(@PathVariable("id") Long id) {

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
            return HttpStatus.INTERNAL_SERVER_ERROR; //415 에러코드 미디어 타입 에러
        }
        //S3에 파일 업로드

        try {
            String fileName = file.getOriginalFilename(); // 선택한 파일명을 가져옴
            String fileUrl = "https://" + bucket + fileName; // S3에 업로드된 파일의 URL 생성

            ObjectMetadata metadata = new ObjectMetadata(); // S3 객체 메타데이터 생성
            metadata.setContentType(file.getContentType()); // 파일의 컨텐츠 타입 설정
            metadata.setContentLength(file.getSize()); // 파일 크기 설정

            // Amazon S3에 파일 업로드. "post/" 폴더에 저장.
            amazonS3Client.putObject(bucket, "post/"+fileName, file.getInputStream(), metadata);

            PostEntity postEntity = PostEntity.builder()
                    .caption(postDto.getCaption()) //게시물 설명
                    .memberEntity(memberEntity) //게시물 작성한 회원 아이디(엔터티로 넣음)
                    .postImg(fileUrl) // 게시물을 등록함
                    .build();

            postService.cratePost(postEntity); //게시물 등록

            return HttpStatus.OK; //성공(200)

        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }

    }


}