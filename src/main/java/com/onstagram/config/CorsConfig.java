package com.onstagram.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 허용할 도메인을 여기에 추가
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드 설정
                .exposedHeaders("Authorization", "RefreshToken");
//                .allowCredentials(true) // 인증 정보 (쿠키 등)을 함께 보내도록 허용
//                .maxAge(3600); // CORS preflight 요청의 유효 기간 설정
    }

}