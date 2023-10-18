package com.onstagram.jwt;


import com.onstagram.Member.domain.MemberDetail;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class JwtService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("시큐리티 로그인 아이디 값 : " + username);
        List<MemberEntity> members = memberRepository.findOneByEmail(username); //username:로그인 아이디값 -> email
        log.info("아이디가 존재하는지 : " + members.size());
        if (!members.isEmpty()) {
            log.info("로그인한 회원의 정보가 있다");
            return MemberDetail.builder().memberEntity(members.get(0)).build();
        } else {
            throw new UsernameNotFoundException("사용자를 찾을 수 업습니다.");
        }
    }

}
