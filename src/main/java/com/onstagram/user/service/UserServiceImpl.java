package com.onstagram.user.service;

import com.onstagram.user.entity.UserEntity;
import com.onstagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Long join(UserEntity userEntity) {

        String rawPassword = userEntity.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.builder()
                .password(encodePassword)
                .build();
//        userEntity.setPassword(encodePassword);

        userRepository.save(userEntity); //DB에 회원정보 저장
        return userEntity.getId();
    }

    @Override
    public List<UserEntity> findUsers() {
        return userRepository.findAll();
    }
}
