package com.onstagram.post.repository;//package com.onstagram.member.repository;

import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final EntityManager em;

    @Override// 게시물 정보 등록
    public void createPost(PostEntity postEntity) {
        em.persist(postEntity); //게시물 등록
    }

    @Override // 게시물 이미지 파일 정보 업로드
    public void uploadImgs(PostImgEntity postImgEntity) {
        em.persist(postImgEntity);
    }
}