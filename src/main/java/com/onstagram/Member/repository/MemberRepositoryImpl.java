package com.onstagram.Member.repository;

import com.onstagram.Member.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final EntityManager em;

    @Override //회원가입(회원정보 DB전송)
    public void save(MemberEntity memberEntity) {
        em.persist(memberEntity);
    }

    @Override //아이디 중복체크
    public MemberEntity findOneByEmail(String email) {
        try {
            return em.createQuery("select m from MemberEntity  m where m.email = :email", MemberEntity.class)
                    .setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

//    @Override
//    public List<MemberEntity> findbyEmail(String email) {
//        return em.createQuery("select m from MemberEntity m where m.email = :email", MemberEntity.class)
//                .setParameter("email", email).getResultList();
//    }
//
//    @Override //이미지를 제외한 회원의 게시물 정보 받아오기
//    public List<PostEntity> findbyId(Long id) {
//        return em.createQuery("select p from PostEntity  p where p.memberEntity.id = :id", PostEntity.class)
//                .setParameter("id", id).getResultList();
//    }
//
//    @Override //게시물 좋아요 개수
//    public Long likeCount(Long postId) {
//        return em.createQuery("select count(l) from LikeEntity l where l.postEntity.postId = :id",Long.class)
//                .setParameter("id",postId).getSingleResult();
//    }
//
//    @Override
//    public List<PostImgEntity> findByImgId(Long postId) {
//        return em.createQuery("select pi from PostImgEntity pi where pi.postEntity.postId = :id", PostImgEntity.class)
//                .setParameter("id", postId).getResultList();
//    }
//    @Override //아이디 중복 체크
//    public MemberEntity findByName(String email) {
//        MemberEntity memberEntity = em.createQuery("select m from MemberEntity m where m.email = :email", MemberEntity.class)
//                .setParameter("email", email)
//                .getSingleResult();
//            return memberEntity;
//    }

}