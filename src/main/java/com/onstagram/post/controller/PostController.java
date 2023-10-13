package com.onstagram.post.controller;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.domain.PostInfoDto;
import com.onstagram.post.domain.PostStatus;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;
import com.onstagram.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/post")
public class PostController {

    private String uploadPath = "C:/image/post"; //파일이 저장되는 경로

    private final PostService postService;

    @PostMapping(value="/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostInfoDto> register(@RequestParam("uploadImgs") MultipartFile[] uploadImgs, @ModelAttribute PostDto postDto) {

        log.info("게시물 등록 들어옴");

        //회원아이디 회원 엔터티에 저장
        MemberEntity memberEntity = MemberEntity.builder()
                .id(postDto.getUserId())
                .build();

        PostEntity postEntity = PostEntity.builder()
                .caption(postDto.getCaption()) //게시물 설명
                .memberEntity(memberEntity) //게시물 작성한 회원 아이디(엔터티로 넣음)
                .build();

        postService.cratePost(postEntity); //게시물 등록

        List<PostImgEntity> list = new ArrayList<>();

        //게시물 사진 업로드 시작
        for (MultipartFile uploadImg : uploadImgs) {
            //이미지 파일만 업로드 가능
            if (!uploadImg.getContentType().startsWith("image")) {
                log.warn("이미지 파일이 아닙니다.");
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); //415 에러코드 미디어 타입 에러
            }

            String originalName = uploadImg.getOriginalFilename(); //선택한 파일명

            String saveName = uploadPath + File.separator + originalName;
            Path savePath = Paths.get(saveName);

            try {
                uploadImg.transferTo(savePath); //경로에 이미지 파일 저장
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                //500 에러코드 서버 측에서 예기치 않은 오류가 발생했거나 파일 저장과 관련된 문제가 있을 때 반환
            }

            PostImgEntity postImgEntity = PostImgEntity.builder()
                    .uploadImgs(originalName)
                    .postEntity(postEntity)
                    .build();

            try {
                postService.uploadImgs(postImgEntity); //이미지 테이블에 저장
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                //500 에러코드 서버 측에서 예기치 않은 오류가 발생했거나 파일 저장과 관련된 문제가 있을 때 반환
            }

            list.add(postImgEntity);

        }

        //최종적으로 해당 게시물 + 해당 게시물 이미지 정보
        PostInfoDto postInfoDto = PostInfoDto.builder()
                .postEntity(postEntity)
                .postImgEntityList(list)
                .build();

        return new ResponseEntity<>(postInfoDto, HttpStatus.OK); //200 성공

    }
    
}