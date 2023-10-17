package com.onstagram.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 토큰 받아오기
        log.info("헤더에서 토큰받아오기 실행****************************************");
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        log.info("토큰값은 ?????????  : " + token);
        // 토큰이 유효하다면
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰으로 부터 유저 정보를 받아
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext에 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Filter 실행
        chain.doFilter(request, response);
    }

}