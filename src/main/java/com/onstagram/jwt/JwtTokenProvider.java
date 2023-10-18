package com.onstagram.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "onstagram"; // onstagram

    private long tokenValidTime = 60 * 60 * 1000L;   // 토큰 유효시간 60분

    private final UserDetailsService userDetailsService;

    // 객체 초기화, secret key를 Base64로 인코딩
    @PostConstruct //init() 메서드는 객체가 생성된 후 자동으로 호출되는 초기화 메서드입니다
    public void init() { //이 메서드의 주요 역할은 secretKey를 Base64로 인코딩하여 설정하는 것입니다.
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // 토큰 생성
    public String createToken(String email, Long userId) { // email:로그인 아이디값, id:userId
        Claims claims = Jwts.claims().setSubject(email);  // JWT payload에 저장되는 정보단위, String email = claims.getSubject();
        claims.put("email",email);
        claims.put("userId", userId);  // 정보는 key / value 쌍으로 저장
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)                                        // 정보 저장
                .setIssuedAt(now)                                         // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime))  // 토큰 유효시각 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)            // 암호화 알고리즘과 secret 값
                .compact();
    }

    // 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 email(로그인 아이디) 추출
    public String getEmail(String token) { //email 추출
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); //리턴값은 위에서 setSubject(email)해준 email값을 반환
    }

    // 토큰에서 회원 id 추출
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.get("userId").toString());
    }

    // 토큰 유효성, 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // Request의 Header에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("TOKEN");
    }

}
