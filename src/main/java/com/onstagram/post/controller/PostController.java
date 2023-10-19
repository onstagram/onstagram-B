package com.onstagram.post.controller;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.service.MemberService;
import com.onstagram.comment.domain.CommentDto;
import com.onstagram.comment.entity.CommentEntity;
import com.onstagram.comment.service.CommentService;
import com.onstagram.file.FileService;
import com.onstagram.jwt.JwtTokenProvider;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.domain.PostInfoDto;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.service.PostService;
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

    @GetMapping("/postForm") //게시물 등록 페이지(회원정보 리턴)
    public ResponseEntity<MemberDto> postForm(HttpServletRequest request) {
        log.info("게시물 등록 페이지 들어옴(계정 주인 정보 페이지에 전송)");
        try {
            String email = memberService.getEmail(request); // token을 통해서 User의 id를 뽑아오는 메서드
            MemberDto memberDto = memberService.findByEmail(email);
            return new ResponseEntity<>(memberDto,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //이건 맞는지 모름
        }
    }

    //게시물 목록
    //전체 게시물에서 likeCount가 많은순으로 게시물 조회
    //게시물 목록(팔로우한 사람 게시물만 조회) --> 메인
    @GetMapping("/postList")
    public ResponseEntity<List<PostDto>> postList() {


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

    @GetMapping("postInfo/{postId}") //게시물 상세 페이지 -> 해당 게시물정보, 댓글정보, 회원정보
    public ResponseEntity<PostInfoDto> postModifyForm(@PathVariable("postId") Long postId, HttpServletRequest request) {
        log.info("게시물 상세페이지");
        try {
            String email = memberService.getEmail(request); // token을 통해서 User의 id를 뽑아오는 메서드
            MemberDto memberDto = memberService.findByEmail(email); //회원 정보

            if(memberDto == null) {
                return new ResponseEntity("회원정보실패", HttpStatus.BAD_REQUEST);
            }

            log.info(memberDto.getUserId() + " || 아이디 : " + memberDto.getUserId());


            PostEntity postEntity = postService.findById(postId);
            if(postEntity == null) {
                return new ResponseEntity("게시물실패", HttpStatus.BAD_REQUEST);
            }
            PostDto postDto = PostDto.builder().postEntity(postEntity).build();

            log.info("게시물 아이디 : " + postDto.getPostId());
            log.info("게시물 아이디 : " + postId);

            List<CommentEntity> comments = commentService.findAllById(postId);
            List<CommentDto> commentList = new ArrayList<>();

            for(CommentEntity comment : comments) {
                commentList.add(CommentDto.builder().commentEntity(comment).build());
            }


            log.info("댓글 개수 : " + commentList.size());
            
            PostInfoDto postInfoDto = PostInfoDto.builder()
                    .memberDto(memberDto)
                    .postDto(postDto)
                    .commentList(commentList)
                    .build();
            
            return new ResponseEntity<>(postInfoDto, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //이건 맞는지 모름
        }

    }

    //게시물 등록
    @PostMapping(value="/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HttpStatus register(@RequestParam("file") MultipartFile file, @ModelAttribute PostDto postDto,
                               HttpServletRequest request) {

        String token = jwtTokenProvider.resolveToken(request);
        Long id = jwtTokenProvider.getUserIdFromToken(token);

//        log.info("토큰명 : " + token);
//        log.info("회원아이디 :" + id);

//        log.info("게시물 등록 들어옴");

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

    //게시물 수정

}