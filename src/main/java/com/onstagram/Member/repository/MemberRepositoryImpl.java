package com.onstagram.Member.repository;

import com.onstagram.Member.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final EntityManager em;

    @Override
    public void save(MemberEntity memberEntity) {
        em.persist(memberEntity);
    }

    @Override //로그인시 로그인한 회원의 정보 가져오기
    public MemberEntity findOneByEmail(String email) {
        return em.createQuery("select m from MemberEntity  m where m.email = :email", MemberEntity.class)
                .setParameter("email", email).getSingleResult();
    }

    @Override
    public List<MemberEntity> findbyEmail(String email) {
        return em.createQuery("select m from MemberEntity  m where m.email = :email", MemberEntity.class)
                .setParameter("email", email).getResultList();
    }

}