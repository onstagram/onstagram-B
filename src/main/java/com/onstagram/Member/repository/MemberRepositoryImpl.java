package com.onstagram.Member.repository;

import com.onstagram.Member.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final EntityManager em;

    @Override
    public void save(MemberEntity memberEntity) {
        em.persist(memberEntity);
    }

}