package com.onstagram.post.service;

import com.onstagram.Member.domain.MemberDetail;
import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PostEntity cratePost(PostEntity postEntity) {
        try {
            postRepository.save(postEntity);
            return postEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}