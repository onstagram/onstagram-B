package com.onstagram.like.service;

import com.onstagram.like.entity.LikeEntity;

public interface LikeService {

    public Long addLike(LikeEntity likeEntity); //좋아요 하기
    public void removeLike(Long likeId); //좋아요 취소

}