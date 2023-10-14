package com.onstagram.member.service;

import com.onstagram.member.entity.MemberEntity;
import com.onstagram.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Long join(MemberEntity memberEntity) {

        String rawPassword = memberEntity.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);

        memberEntity.setPassword(encodePassword);

        memberRepository.save(memberEntity); //DB에 회원정보 저장
        return memberEntity.getId();
    }


    @Override
    public List<MemberEntity> findUsers() {
        return memberRepository.findAll();
    }

    public boolean validateDuplicateMember(String email) {
        List<MemberEntity> findMembers = memberRepository.findbyEmail(email);
        if (!findMembers.isEmpty()) { // 중복일때
            return false;
        }
        return true;
    }



}
