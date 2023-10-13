package com.onstagram.post.service;

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
    public Long cratePost(PostEntity postEntity) {
        postRepository.createPost(postEntity);
        return postEntity.getId();
    }

    @Override// 게시물 이미지 파일 정보 업로드
    public Long uploadImgs(PostImgEntity postImgEntity) {
        postRepository.uploadImgs(postImgEntity);
        return postImgEntity.getId();
    }
}
