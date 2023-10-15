package com.onstagram.member.service;

import com.onstagram.member.domain.ModifyDto;
import com.onstagram.member.domain.SigninDto;
import com.onstagram.member.entity.MemberEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberService {

    public Long join(MemberEntity memberEntity);

    public List<MemberEntity> findUsers();

    public boolean validateDuplicateMember(String email);

    boolean checkPassword(SigninDto signinDto);

    public List<MemberEntity> findByName(String name); //이름으로검색

    public List<MemberEntity> findByNameWithLike(String name); //이름으로검색 (like)

    void updateUser(String email, ModifyDto modifyDto, MultipartFile uploadProfileImg);
}
