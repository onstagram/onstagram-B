package com.onstagram.member.service;

import com.onstagram.member.entity.MemberEntity;
import com.onstagram.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Long join(MemberEntity memberEntity) {
        memberRepository.save(memberEntity);
        return memberEntity.getId();
    }

}
