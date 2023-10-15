package com.onstagram.member.service;

import com.onstagram.member.domain.ModifyDto;
import com.onstagram.member.domain.SigninDto;
import com.onstagram.member.entity.MemberEntity;
import com.onstagram.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Long join(MemberEntity memberEntity) {

        String rawPassword = memberEntity.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);

        memberEntity.setPassword(encodePassword);

        memberRepository.save(memberEntity); //DB에 회원정보 저장
        return memberEntity.getId();
    }


    @Override
    public List<MemberEntity> findUsers() {
        return memberRepository.findAll();
    }

    public boolean validateDuplicateMember(String email) {
        List<MemberEntity> findMembers = memberRepository.findbyEmail(email);
        if (!findMembers.isEmpty()) { // 중복일때
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPassword(SigninDto signinDto) {
        List<MemberEntity> memberEntities = memberRepository.findbyEmail(signinDto.getEmail());

        if (!memberEntities.isEmpty()) {
            return bCryptPasswordEncoder.matches(signinDto.getPassword(), memberEntities.get(0).getPassword());
        } else return false;

    }

    @Override
    public List<MemberEntity> findByName(String name) {
        return memberRepository.findByName(name);
    }

    @Override
    public List<MemberEntity> findByNameWithLike(String name) {
        return memberRepository.findByNameWithLike(name);
    }

    @Override
    public void updateUser(String email, @RequestBody ModifyDto modifyDto, @RequestParam("uploadProfileImg") MultipartFile uploadProfileImg) {
        MemberEntity memberEntity = memberRepository.findbyEmail(email).get(0);

        String rawPassword = modifyDto.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);

        memberEntity.setPassword(encodePassword);
        memberEntity.setIntroduction(modifyDto.getIntroduction());

        if (uploadProfileImg != null && !uploadProfileImg.isEmpty()) {

            String uploadPath = "C:/image/profile"; // 이미지를 저장할 디렉토리 경로 설정
            String originalName = uploadProfileImg.getOriginalFilename(); //선택한 파일명

            String saveName = uploadPath + File.separator + originalName;
            Path savePath = Paths.get(saveName);

            try {
                uploadProfileImg.transferTo(savePath);
            } catch (Exception e) {
                e.getMessage();
            }

            memberEntity.setUserImg(originalName);
        }else{
            memberEntity.setUserImg("default.jpg");
        }


        memberRepository.save(memberEntity);
    }


}
