package com.onstagram.post.controller;

import com.onstagram.DtoData;
import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.repository.MemberRepository;
import com.onstagram.Member.service.MemberService;
import com.onstagram.PostDataList;
import com.onstagram.comment.domain.CommentDto;
import com.onstagram.exception.OnstagramException;
import com.onstagram.file.FileService;
import com.onstagram.jwt.JwtTokenProvider;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.domain.PostInfoDto;
import com.onstagram.post.domain.PostModifyDto;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final MemberService memberService;
    private final PostService postService;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/getpost") //게시물 등록 페이지(회원정보 리턴)
    public DtoData getpost(HttpServletRequest request) {
        log.info("게시물 등록 페이지 들어옴(계정 주인 정보 페이지에 전송)");
        Long userId = memberService.getUserId(request); //토큰으로 회원 아이디 받기
        MemberDto memberDto = memberService.findById(userId);
        return new DtoData(HttpStatus.OK.value(), true, memberDto);
    }


    @GetMapping("/edit/{postId}")//게시물 수정 페이지(회원정보, 게시물 정보)
    public DtoData modifyForm(@PathVariable("postId") Long postId) {
        return new DtoData(HttpStatus.OK.value(), true, postService.postInfo(postId));
    }

    //게시물 목록
    //전체 게시물에서 likeCount가 많은순으로 게시물 조회
    //게시물 목록(팔로우한 사람 게시물만 조회) --> 메인
    @GetMapping("/postList") //일단 전체 게시물 조회(본인 제외)
    public PostDataList postList(HttpServletRequest request) {
        Long userId = memberService.getUserId(request);
        List<PostDto> postDtoList = postService.postList(userId);
        return new PostDataList(HttpStatus.OK.value(), true,postDtoList);
    }

    @GetMapping("postInfo/{postId}") //게시물 상세 페이지 -> 해당 게시물정보, 댓글정보, 회원정보
    public DtoData postDetailInfo(@PathVariable("postId") Long postId) {
        log.info("게시물 상세페이지");

        //게시물 정보 + 작성자 정보
        PostEntity postEntity = postService.postAndUserInfo(postId);
        if(postEntity == null) {
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "값이 없다");
        }

        PostDto postDto = PostDto.builder().postEntity(postEntity).build(); //게시글 정보
        MemberDto memberDto = MemberDto.builder().memberEntity(postEntity.getMemberEntity()).build(); //회원정보
        //댓글 정보
        List<CommentDto> commentDtoList = postService.commemtList(postId); //게시물의 댓글 목록

        PostInfoDto postInfoDto = new PostInfoDto(memberDto, postDto, commentDtoList);

        return new DtoData(HttpStatus.OK.value(),true,postInfoDto);

    }

    //게시물 등록
    @PostMapping(value="/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DtoData register(@RequestParam("file") MultipartFile file, @ModelAttribute PostDto postDto,
                           HttpServletRequest request) {

        String token = jwtTokenProvider.resolveToken(request);
        Long id = jwtTokenProvider.getUserIdFromToken(token);

        log.info("회원아이디 :" + postDto.getPostId());
        log.info("게시물 등록 들어옴");

        postService.findById(id);

        //회원아이디 회원 엔터티에 저장
//        MemberEntity memberEntity = MemberEntity.builder().userId(id).build();
        MemberEntity memberEntity = memberRepository.findById(id).get(0);

        //게시물 사진 업로드 시작
        //이미지 파일인지 확인
        if (!file.getContentType().startsWith("image")) {
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "이미지 파일 아님");
        }
        //S3에 파일 업로드
        String postImg = fileService.FileUpload(file,"post");

        PostEntity postEntity = PostEntity.builder()
                .caption(postDto.getCaption()) //게시물 설명
                .memberEntity(memberEntity) //게시물 작성한 회원 아이디(엔터티로 넣음)
                .postImg(postImg) // 게시물을 등록함
                .likeCount(0L) // 좋아요 개수 초기값으 0으로 지정
                .build();

        PostEntity postDB = postService.AddPost(postEntity); //게시물 등록

        PostDto.builder().postEntity(postDB).build();

        if(postDB != null) { // 성공
            return new DtoData(HttpStatus.OK.value(),true,PostDto.builder().postEntity(postDB).build());

        } else { //실패
            return new DtoData(HttpStatus.BAD_REQUEST.value(),false,null);

        }

    }

    @PutMapping("/edit") //게시물 수정
    public DtoData postModify(@RequestBody PostModifyDto postModifyDto) {
        PostDto postDto = postService.postModify(postModifyDto);
        return new DtoData(HttpStatus.OK.value(), true, postDto);
    }

    @DeleteMapping("/delete") //게시물 삭제
    public DtoData postDelete(@RequestParam Long postId) {
        log.info("postId : " + postId);
        postService.postDelete(postId);
        return new DtoData(HttpStatus.OK.value(),true,null);
    }

}