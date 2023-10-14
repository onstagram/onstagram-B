package com.onstagram.member.repository;

import com.onstagram.member.entity.MemberEntity;
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

    @Override
    public List<MemberEntity> findAll() {
        return em.createQuery("select m from MemberEntity m", MemberEntity.class).getResultList();
    }

    @Override
    public List<MemberEntity> findbyEmail(String email) {
        return em.createQuery("select m from MemberEntity  m where m.email = :email", MemberEntity.class)
                .setParameter("email", email).getResultList();
    }

    @Override
    public List<MemberEntity> findByName(String name) {
        return em.createQuery("select m from MemberEntity  m where m.userName = :name", MemberEntity.class)
                .setParameter("name", name).getResultList();
    }


}