package com.onstagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .antMatchers("/").authenticated()  //인증이 필요한 페이지 ex) "/user/**"
                .anyRequest().permitAll()// 윗줄 제외 모든 요청은 허용
                .and()
                .formLogin()
                .loginPage("/user/login") // url로!! get으로 //사용자가 따로 만든 로그인 페이지를 사용하려고 할때 설정합니다.
                .loginProcessingUrl("/user/login") // post -> 스프링 시큐리티가 로그인 프로세스 진행ㅌ
                .defaultSuccessUrl("/");  // 정상적으로 인증성공 했을 경우 이동하는 페이지
        return http.build();

    }


}
