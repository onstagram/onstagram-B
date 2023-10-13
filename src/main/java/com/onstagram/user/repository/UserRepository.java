package com.onstagram.user.repository;

import com.onstagram.user.entity.UserEntity;

import java.util.List;

public interface UserRepository {
    void save(UserEntity userEntity);

    List<UserEntity> findAll();

}