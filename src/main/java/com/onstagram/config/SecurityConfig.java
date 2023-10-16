package com.onstagram.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig  {
    
    @Bean //비밀번호 암호화
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll() // 모든 페이지에 대한 권한을 허용
                .anyRequest().authenticated();
//        http.csrf().disable();
//        http.authorizeRequests()
//                    .antMatchers("/**").permitAll()
//                    .anyRequest().permitAll() //어떤 경로에서든지 매칭 성공
//                    .and()
//                .formLogin()
//                    .loginPage("/login") // 로그인 페이지 URL
//                    .loginProcessingUrl("/login") // 로그인 처리 URL
//                    .defaultSuccessUrl("/") // 로그인 성공 후 리디렉션할 URL
//                    .permitAll(); // 로그인 페이지에 대한 접근 권한을 모두에게 부여
        return http.build();
    }


}