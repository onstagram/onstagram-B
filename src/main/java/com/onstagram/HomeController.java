package com.onstagram;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/main")
    public String home() {
        return "로그인 성공해서 메인페이지에 왔습니다.";
    }

}