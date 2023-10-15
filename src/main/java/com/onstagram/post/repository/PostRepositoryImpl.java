package com.onstagram.post.repository;//package com.onstagram.member.repository;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final EntityManager em;

    @Override
    public MemberEntity findById(Long id) { //회원아이디로 회원정보 리턴
        return em.createQuery("select m from MemberEntity  m where m.id = :id", MemberEntity.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override// 게시물 정보 등록
    public void createPost(PostEntity postEntity) {
        em.persist(postEntity); //게시물 등록
    }

    @Override // 게시물 이미지 파일 정보 업로드
    public void uploadImgs(PostImgEntity postImgEntity) {
        em.persist(postImgEntity);
    }
}