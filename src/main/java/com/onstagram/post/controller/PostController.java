package com.onstagram.post.controller;

import com.onstagram.DtoData;
import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.service.MemberService;
import com.onstagram.comment.service.CommentService;
import com.onstagram.exception.OnstagramException;
import com.onstagram.file.FileService;
import com.onstagram.jwt.JwtTokenProvider;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.domain.PostModifyDto;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
//게시물 등록(ok)

//팔로우한 계정 게시물만 조회(ing)
//탐색탭 ->좋아요 많은 순서대로 게시물 조회

//게시물 상세정보 페이지(ok)
//게시물 상세(수정,댓글수정) 페이지(ok)

//게시물 수정(ing)
//게시물 삭제(ing)

public class PostController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;
    private final FileService fileService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/getpost") //게시물 등록 페이지(회원정보 리턴)
    public DtoData getpost(HttpServletRequest request) {
        log.info("게시물 등록 페이지 들어옴(계정 주인 정보 페이지에 전송)");
        Long userId = memberService.getUserId(request); //토큰으로 회원 아이디 받기
        MemberDto memberDto = memberService.findById(userId);
        return new DtoData(HttpStatus.OK.value(), true, memberDto);
    }


    @GetMapping("setting/edit/{postId}")//게시물 수정 페이지(회원정보, 게시물 정보)
    public DtoData modifyForm(@PathVariable("postId") Long postId) {
        return new DtoData(HttpStatus.OK.value(), true, postService.postInfo(postId));
    }

    //게시물 목록
    //전체 게시물에서 likeCount가 많은순으로 게시물 조회
    //게시물 목록(팔로우한 사람 게시물만 조회) --> 메인
    @GetMapping("/postList")
    public DtoData postList(HttpServletRequest request) {
        Long userId = memberService.getUserId(request);

        //내가 팔로우한 아이디 조회


        //팔로우한 아이디를 가지고 게시글 전부 불러오기
//        for(Long id : followingId) {
//            List<PostDto> postDtoList = postService.postList(id);




//        }
        /////////////////

        return null;
    }

//    @GetMapping("")

//    @GetMapping("postInfo/{postId}") //게시물 상세 페이지 -> 해당 게시물정보, 댓글정보, 회원정보
//    public ResponseEntity<PostInfoDto> postModifyForm(@PathVariable("postId") Long postId, HttpServletRequest request) {
//        log.info("게시물 상세페이지");
//        try {
//            String email = memberService.getEmail(request); // token을 통해서 User의 id를 뽑아오는 메서드
//            MemberDto memberDto = memberService.findByEmail(email); //회원 정보
//
//            if(memberDto == null) {
//                return new ResponseEntity("회원정보실패", HttpStatus.BAD_REQUEST);
//            }
//
//            log.info(memberDto.getUserId() + " || 아이디 : " + memberDto.getUserId());
//
//
//            PostEntity postEntity = postService.findById(postId);
//            if(postEntity == null) {
//                return new ResponseEntity("게시물실패", HttpStatus.BAD_REQUEST);
//            }
//            PostDto postDto = PostDto.builder().postEntity(postEntity).build();
//
//            log.info("게시물 아이디 : " + postDto.getPostId());
//            log.info("게시물 아이디 : " + postId);
//
//            List<CommentEntity> comments = commentService.findAllById(postId);
//            List<CommentDto> commentList = new ArrayList<>();
//
//            for(CommentEntity comment : comments) {
//                commentList.add(CommentDto.builder().commentEntity(comment).build());
//            }
//
//
//            log.info("댓글 개수 : " + commentList.size());
//
//            PostInfoDto postInfoDto = PostInfoDto.builder()
//                    .memberDto(memberDto)
//                    .postDto(postDto)
//                    .commentList(commentList)
//                    .build();
//
//            return new ResponseEntity<>(postInfoDto, HttpStatus.OK);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //이건 맞는지 모름
//        }
//
//    }

    //게시물 등록
    @PostMapping(value="/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String register(@RequestParam("file") MultipartFile file, @ModelAttribute PostDto postDto,
                           HttpServletRequest request) {

        String token = jwtTokenProvider.resolveToken(request);
        Long id = jwtTokenProvider.getUserIdFromToken(token);

//        log.info("토큰명 : " + token);
        log.info("회원아이디 :" + postDto.getPostId());
        log.info("게시물 등록 들어옴");

        //회원아이디 회원 엔터티에 저장
        MemberEntity memberEntity = MemberEntity.builder().userId(id).build();

        //게시물 사진 업로드 시작
        //이미지 파일인지 확인
        if (!file.getContentType().startsWith("image")) {
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "이미지 파일 아님");
//            return "이미지 파일이 아니다"; **
        }
        //S3에 파일 업로드
        String postImg = fileService.FileUpload(file,"post");

//        if(postImg == null) { **
//            return "이미지 업로드를 실패했습니다.";
//        }

        PostEntity postEntity = PostEntity.builder()
                .caption(postDto.getCaption()) //게시물 설명
                .memberEntity(memberEntity) //게시물 작성한 회원 아이디(엔터티로 넣음)
                .postImg(postImg) // 게시물을 등록함
                .likeCount(0L) // 좋아요 개수 초기값으 0으로 지정
                .build();

        PostEntity postDB = postService.AddPost(postEntity); //게시물 등록
//        PostDto postDto1 = PostDto.builder().postEntity(postDB).build(); //게시물 등록한 entity정보를 dto에 담아서 리턴

        if(postDB != null) { // 성공
            return "게시물 등록 성공";

        } else { //실패
            return "게시물 등록 실패";

        }

    }

    @PutMapping("setting/edit") //게시물 수정
    public DtoData postModify(@RequestBody PostModifyDto postModifyDto) {
        PostDto postDto = postService.postModify(postModifyDto);
        return new DtoData(HttpStatus.OK.value(), true, postDto);
    }

}