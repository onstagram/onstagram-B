package com.onstagram.Member.service;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.repository.MemberRepository;
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
    public boolean validateDuplicateMember(String email) {
        List<MemberEntity> findMembers = memberRepository.findbyEmail(email);
        if (!findMembers.isEmpty()) { // 중복 & 아이디 존재
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPassword(MemberEntity memberEntity) {
        MemberEntity memberEntity1 = memberRepository.findOneByEmail(memberEntity.getEmail());
        //true : 비밀번호 일치(로그인 성공), false: 비밀번호 불일치
        return bCryptPasswordEncoder.matches(memberEntity.getPassword(), memberEntity1.getPassword());
    }

}
