package com.onstagram.post.service;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.comment.domain.CommentDto;
import com.onstagram.comment.entity.CommentEntity;
import com.onstagram.comment.repository.CommentRepository;
import com.onstagram.exception.OnstagramException;
import com.onstagram.file.FileService;
import com.onstagram.like.repository.LikeRepsitory;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.domain.PostModifyDto;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FileService fileService;
    private String path = "post";

    @Override
    public PostEntity findById(Long id) { //해당 회원의 정보
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if(postEntity.isPresent()) {
            return postEntity.orElse(null);
        } else {
            throw new OnstagramException(HttpStatus.NOT_FOUND.value(), "회원정보를 받아올 수 없습니다.");
        }
    }

    @Override
    public PostEntity AddPost(PostEntity postEntity) {
        try {
            postRepository.save(postEntity);
            return postEntity;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new OnstagramException(HttpStatus.BAD_REQUEST.value(), "게시물 정보 DB전송 실패");
        }
    }

    @Override //게시물 정보
    public PostDto postInfo(Long postId) {
        try {
            return PostDto.builder().postEntity(postRepository.postList(postId).get(0)).build();
        }catch (Exception e) {
            e.printStackTrace();
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "게시물 정보 조회 실패");
        }
    }

    @Override //회원의 전체 게시물(본인 제외)
    public List<PostDto> postList(Long userId) {
        List<PostDto> postDtoList = new ArrayList<>();
        try {
            List<PostEntity> postEntityList = postRepository.findUsersWithUserIdNotEqualToOne(userId);
            for(PostEntity postEntity : postEntityList) { //Entity를 하나씩 꺼내서 -> Dto 매핑(변환)
                postDtoList.add(PostDto.builder().postEntity(postEntity).build()); //list에 넣기
            }
            return postDtoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(),"게시물 조회 실패");
        }
    }

    @Override
    public PostDto postModify(PostModifyDto postModifyDto) { //회원의 게시물 정보 수정
        try {
            //기존 게시물의 정보 불러오기
            PostEntity postEntity = postRepository.getReferenceById(postModifyDto.getPostId());
            postEntity.modifyEntity(postModifyDto); //수정할 정보 + 수정 날짜만 변경하기
            postRepository.save(postEntity); //수정 정보 업로드 성공
            return PostDto.builder().postEntity(postEntity).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "게시물 정보 수정 실패");
        }
    }

    @Override
    public void postDelete(Long postId) {
        try {
            // 게시글에 대한 좋아요 데이터 삭제
            if(postRepository.findByLikeId(postId) > 0) {
                log.info("좋아요 삭제");
                postRepository.deleteByPostId(postId);
            }
            // 게시글에 대한 댓글 데이터 삭제
            if(postRepository.findByCommentId(postId) > 0) {
                log.info("댓글 삭제");
                postRepository.CommentDelete(postId);
            }
            // 게시글 삭제
            postRepository.deleteById(postId);
            String[] imgArr = postRepository.postList(postId).get(0).getPostImg().split("/");
            String img = imgArr[imgArr.length-1]; //파일명.확장자

            //파일 삭제
            fileService.DeleteFile(path + "/" + img);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(),"삭제 실패");
        }
    }

    @Override
    public PostEntity postAndUserInfo(Long postId) {
        try {
            Optional<PostEntity> postEntity = postRepository.findPostWithUserByPostId(postId);
            if(postEntity.isPresent()) {
                return postEntity.orElse(null);
            }else {
                throw new OnstagramException(HttpStatus.BAD_REQUEST.value(),"값이 비어있다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "게시물&작성자 조회 실패");
        }
    }

    @Override
    public List<CommentDto> commemtList(Long postId) {
        
        List<CommentDto> list = new ArrayList<>();
        
        try {
            List<CommentEntity> commentEntityList = postRepository.findByAllPostId(postId);
            
            for(CommentEntity comment : commentEntityList) {
                MemberEntity memberEntity = postRepository.findByUserId(
                                                            comment.getMemberEntity().getUserId()).get(0);

                String userImg = memberEntity.getUserImg(); //댓글단 회원의 이미지
                log.info("회원 이미지 리읔 : " + userImg);
                CommentDto commentDto = CommentDto.builder().commentEntity(comment).build();
                commentDto.userImg(userImg);

                list.add(commentDto);
            }
            
            return list; //댓글 정보를 전송

        } catch (Exception e) {
            e.printStackTrace();
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "실패");
        }
    }

}