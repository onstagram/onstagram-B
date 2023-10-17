package com.onstagram.like.repository;

import com.onstagram.like.entity.LikeEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepsitory extends JpaRepository<LikeEntity, Long> {
    public LikeEntity save(LikeEntity likeEntity);
    public void deleteById(Long likeId);
}
