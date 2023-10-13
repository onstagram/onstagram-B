package com.onstagram.user.repository;

import com.onstagram.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager em;

    @Override
    public void save(UserEntity userEntity) {
        em.persist(userEntity);
    }

    @Override
    public List<UserEntity> findAll() {
        return em.createQuery("select u from UserEntity u", UserEntity.class).getResultList();

    }

}