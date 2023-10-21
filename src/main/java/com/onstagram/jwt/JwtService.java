package com.onstagram.jwt;


import com.onstagram.Member.domain.MemberDetail;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.repository.MemberRepository;
import com.onstagram.exception.OnstagramException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class JwtService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; //비밀 번호 암호화

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("아이디 값 : " + username);

        List<MemberEntity> members = memberRepository.findOneByEmail(username); //username:로그인 아이디값 -> email

        log.info("로그인 회원의 정보 크기 : " + members.size());

        if (!members.isEmpty()) {
            log.info("로그인한 아이디의 회원의 정보가 있다");
            // 여기서 비밀번호 확인

//            if (bCryptPasswordEncoder.matches("사용자가 입력한 비밀번호", members.get(0).getPassword())) {
//                return MemberDetail.builder().memberEntity(members.get(0)).build();
//            } else {
//                throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다.");
//            }

            return MemberDetail.builder().memberEntity(members.get(0)).build();
        } else {
            log.info("회원정보 x");
            var notFound = HttpStatus.NOT_FOUND;
            throw new UsernameNotFoundException("사용자를 찾을 수 없다.");
        }
    }

}
