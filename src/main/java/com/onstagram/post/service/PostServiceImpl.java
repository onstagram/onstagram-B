package com.onstagram.post.service;

import com.onstagram.Member.domain.ModifyDto;
import com.onstagram.exception.OnstagramException;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.domain.PostModifyDto;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostEntity findById(Long id) { //해당 회원의 정보
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if(postEntity.isPresent()) {
            return postEntity.orElse(null);
        } else {
            return null;
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
//            return null;
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

    @Override //회원의 전체 게시물
    public List<PostDto> postList(Long userId) {

        return null;

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

}