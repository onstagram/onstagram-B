package com.onstagram.post.service;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;
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
        return MemberDto.builder()
                .userId(memberEntity.getId())
                .email(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .userPhone(memberEntity.getUserPhone())
                .userImg(memberEntity.getUserImg())
                .introduction(memberEntity.getIntroduction())
                .userDate(memberEntity.getUserDate())
                .userName(memberEntity.getUserName())
                .build();
//        return new ModelMapper().map(memberEntity, MemberDto.class); //memberEntity를 MemberDto로 매핑해서 dto를 리턴
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