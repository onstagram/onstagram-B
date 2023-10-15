package com.onstagram.like.service;


import com.onstagram.like.entity.LikeEntity;
import com.onstagram.like.repository.LikeRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepsitory likeRepsitory;

    @Override
    public Long addLike(LikeEntity likeEntity) { //좋아요 하기
        likeRepsitory.save(likeEntity);
        return likeEntity.getLikeId();
    }

    @Override
    public void removeLike(Long likeId) {
        likeRepsitory.deleteById(likeId);
    }
}