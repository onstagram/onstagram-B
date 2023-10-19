package com.onstagram.config;

import com.onstagram.jwt.JwtAuthenticationFilter;
import com.onstagram.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    @Bean //비밀번호 암호화
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable()
                .and()

                .cors().configurationSource(corsConfigurationSource())
                .and()

                // 세션 사용안함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // URL 관리
                .authorizeRequests()
                .antMatchers("/**").permitAll()
//                .anyRequest().permitAll()
//                .antMatchers("/signup", "/signin", "/").permitAll()
//                .anyRequest().hasRole("USER") //위에 URL외에는 USER라는 권한만 허용
//                .anyRequest().authenticated() //authenticated() : 로그인 인증만 해야됨
                .and()

//                .formLogin() // ->  API 서버와 같이 로그인 페이지가 필요하지 않거나, 다른 로그인 메커니즘을 사용해야 하는 경우에는 .formLogin()을 사용하지 않을 수 있습니다.
//                    .loginPage("/login") // 로그인 페이지 지정
//                    .permitAll() // 로그인 페이지는 모두 허용
//                    .defaultSuccessUrl("/main") // 로그인 성공 후 이동할 페이지
//                    .and()

//                .logout()
//                    .permitAll() // 로그아웃은 모두 허용
//                .and()

                // JwtAuthenticationFilter를 먼저 적용
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }


}