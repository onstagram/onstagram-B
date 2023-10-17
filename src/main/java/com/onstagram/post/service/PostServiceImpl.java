package com.onstagram.post.service;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public MemberDto findById(Long id) {
        MemberEntity memberEntity = postRepository.findById(id);
        return new ModelMapper().map(memberEntity, MemberDto.class); //memberEntity를 MemberDto로 매핑해서 dto를 리턴
    }

    @Override
    public PostEntity cratePost(PostEntity postEntity) {
        try {
            postRepository.createPost(postEntity);
            return postEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}