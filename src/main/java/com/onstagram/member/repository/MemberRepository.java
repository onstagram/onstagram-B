package com.onstagram.member.repository;

import com.onstagram.member.entity.MemberEntity;

import java.util.List;

public interface MemberRepository {
    void save(MemberEntity memberEntity);

    List<MemberEntity> findAll();

    List<MemberEntity> findbyEmail(String email);

    List<MemberEntity> findByName(String name);


}