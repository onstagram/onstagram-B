package com.onstagram.user.controller;

import com.onstagram.user.domain.JoinStatus;
import com.onstagram.user.domain.UserDto;
import com.onstagram.user.entity.UserEntity;
import com.onstagram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("join")
    public HttpStatus join(@Valid @RequestBody UserDto userDto) {

        log.info("회원가입 시작");

        UserEntity userEntity = userDto.toEntity();

        log.info("회원정보들 ************************");
        log.info(userEntity.getUserName());

        try {
            userService.join(userEntity); //회원가입 시작
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }

        return HttpStatus.OK;
    }

    @GetMapping("users")
    public ResponseEntity<List<UserEntity>> usersList() {
        List<UserEntity> users = userService.findUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }
}