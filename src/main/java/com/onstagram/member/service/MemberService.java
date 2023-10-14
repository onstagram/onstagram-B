package com.onstagram.member.service;

import com.onstagram.member.domain.MemberDto;
import com.onstagram.member.entity.MemberEntity;

import java.util.List;

public interface MemberService {

    public Long join(MemberEntity memberEntity);

    public List<MemberEntity> findUsers();

    public boolean validateDuplicateMember(String email);


}