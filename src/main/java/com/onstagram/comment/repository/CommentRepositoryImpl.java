package com.onstagram.comment.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final EntityManager em;


}
