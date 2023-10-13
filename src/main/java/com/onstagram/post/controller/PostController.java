package com.onstagram.post.controller;

import com.onstagram.member.entity.MemberEntity;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.domain.PostStatus;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;
import com.onstagram.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

//    @GetMapping("/registerForm") //게시물 등록 화면
//    public String registerForm() {
//        return "/post/registerForm";
//    }

    @PostMapping("/register")
    public  PostStatus register(@RequestBody MultipartFile[] uploadImgs, @RequestBody PostDto postDto) {

        System.out.println("게시물 등록 들어옴");

        log.info("게시물 설명 -> " + postDto.getCaption());
        log.info("게시물 작성자 -> " + postDto.getUserId());

        //회원아이디 회원 엔터티에 저장
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(postDto.getUserId());

        PostEntity postEntity = new PostEntity();

        postEntity.setCaption(postDto.getCaption()); //게시물 설명
        postEntity.setMemberEntity(memberEntity); //게시물 작성한 회원 아이디(엔터티로 넣음)

        postService.cratePost(postEntity); //게시물 등록

        List<PostImgEntity> list = new ArrayList<>();

        //게시물 사진 업로드 시작
        for (MultipartFile uploadImg : uploadImgs) {
            //이미지 파일만 업로드 가능
            if (!uploadImg.getContentType().startsWith("image")) {
                log.warn("이미지 파일이 아닙니다.");
                return new PostStatus(401, "이미지 파일이 아님", null, null);
            }

            String originalName = uploadImg.getOriginalFilename(); //선택한 파일명

            String saveName = uploadPath + File.separator + originalName;
            Path savePath = Paths.get(saveName);

            try {
                uploadImg.transferTo(savePath); //경로에 이미지 파일 저장
            } catch (IOException e) {
                e.printStackTrace();
                return new PostStatus(402, "이미지 파일 저장이 안됨", null, null);
            }

            PostImgEntity postImgEntity = new PostImgEntity();
            postImgEntity.setUploadImgs(originalName);
            postImgEntity.setPostEntity(postEntity);

            try {
                postService.uploadImgs(postImgEntity); //이미지 테이블에 저장
            } catch (Exception e) {
                e.printStackTrace();
                return new PostStatus(400, "정보 전송 실패", null, null);
            }

            list.add(postImgEntity);

        }

        return new PostStatus(200, "성공", postEntity, list);

    }


}