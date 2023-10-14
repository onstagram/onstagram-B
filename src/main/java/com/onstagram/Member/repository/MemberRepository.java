package com.onstagram.Member.repository;

import com.onstagram.Member.entity.MemberEntity;

import java.util.List;

public interface MemberRepository {
    public void save(MemberEntity memberEntity); // 회원가입

    public MemberEntity findOneByEmail(String email);
    public List<MemberEntity> findbyEmail(String email);
}