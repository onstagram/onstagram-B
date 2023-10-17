package com.onstagram.post.repository;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {

//    public Optional<MemberEntity> findById(Long id);
//    public void createPost(PostEntity postEntity); //게시물 정보 등록
}