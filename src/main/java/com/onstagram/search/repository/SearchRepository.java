package com.onstagram.search.repository;

import com.onstagram.Member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SearchRepository extends JpaRepository<MemberEntity, Long> {

    // "content" 필드에 "이라는" 글자를 포함하는 데이터를 조회하는 메서드
    @Query("SELECT m FROM MemberEntity m WHERE m.userName LIKE %:userName%")//검색결과를 포함하는 모든 계정값
    List<MemberEntity> findByMembers(@Param("userName") String userName);



}