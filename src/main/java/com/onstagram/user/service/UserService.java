package com.onstagram.user.service;

import com.onstagram.user.entity.UserEntity;

import java.util.List;

public interface UserService {

    public Long join(UserEntity userEntity);

    public List<UserEntity> findUsers();
}