package com.onstagram.Member.repository;

import com.onstagram.Member.entity.MemberEntity;

public interface MemberRepository {
    public void save(MemberEntity memberEntity);
}