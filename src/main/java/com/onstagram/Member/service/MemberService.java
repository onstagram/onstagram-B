package com.onstagram.Member.service;

import com.onstagram.Member.entity.MemberEntity;

public interface MemberService {

    public Long join(MemberEntity memberEntity); //회원가입
    public boolean validateDuplicateMember(String email); //아이디(이메일) 중복 체크

    boolean checkPassword(MemberEntity memberEntity);

}