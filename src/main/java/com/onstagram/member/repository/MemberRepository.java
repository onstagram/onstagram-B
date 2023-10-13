package com.onstagram.member.repository;

import com.onstagram.member.entity.MemberEntity;

public interface MemberRepository {
    public void save(MemberEntity memberEntity);
}