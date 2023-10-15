package com.onstagram.post.service;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;
import com.onstagram.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public MemberEntity findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Long cratePost(PostEntity postEntity) {
        postRepository.createPost(postEntity);
        return postEntity.getPostId();
    }

    @Override// 게시물 이미지 파일 정보 업로드
    public Long uploadImgs(PostImgEntity postImgEntity) {
        postRepository.uploadImgs(postImgEntity);
        return postImgEntity.getImgId();
    }
}