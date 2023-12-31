package com.onstagram.Member.service;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.repository.MemberRepository;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.domain.PostImgDto;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.entity.PostImgEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override //회원가입
    public Long join(MemberEntity memberEntity) {

        String rawPassword = memberEntity.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);

        memberEntity.setPassword(encodePassword);

        memberRepository.save(memberEntity); //DB에 회원정보 저장
        return memberEntity.getUserId();
    }

    @Override //아이디 중복체크
    public boolean validateDuplicateMember(String email) {
//        MemberEntity memberEntity = memberRepository.findByName(email).get();
//        if (memberEntity != null) { // 중복 & 아이디 존재
//            return false;
//        }
//        return true;
//

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByName(email);
        if (optionalMemberEntity.isPresent()) { // 중복 & 아이디 존재
            return false;
        }
        return true;

    }

    @Override //로그인할때 비밀번호 체크(복호화)
    public boolean checkPassword(MemberEntity memberEntity) {
        List<MemberEntity> memberEntity1 = memberRepository.findbyEmail(memberEntity.getEmail());
        //true : 비밀번호 일치(로그인 성공), false: 비밀번호 불일치
        return bCryptPasswordEncoder.matches(memberEntity.getPassword(), memberEntity1.get(0).getPassword());
    }

    @Override//회원 게시물 정보 찾기
    public List<PostDto> findById(Long id) {

        List<PostDto> postDtoList = new ArrayList<>(); //회원게시물 리스트 생성

        List<PostEntity> list = memberRepository.findbyId(id);

        System.out.println("총 회원 게시물의 개수 : " + list.size());
        System.out.println("list : " + list);

        for(PostEntity postEntity : list) {
            Long postId = postEntity.getPostId(); //게시물 아이디

            List<PostImgEntity> imgDtoList = memberRepository.findByImgId(postId); //해당 게시물이미지 정보 리스트

            List<PostImgDto> postImgDtoList = new ArrayList<>();

            for(PostImgEntity postImgEntity : imgDtoList) { // Entity -> Dto로 변환
                PostImgDto postImgDto = PostImgDto.builder()
                        .imgId(postImgEntity.getImgId())
                        .imgName(postImgEntity.getImgName())
                        .postId(postId)
                        .build();
                postImgDtoList.add(postImgDto);
            }

            //좋아요 수
            Long likeCount = memberRepository.likeCount(postId);

            PostDto postDto = PostDto.builder()
                    .postId(postEntity.getPostId())
                    .caption(postEntity.getCaption())
                    .userId(id)
                    .imgList(postImgDtoList)
                    .likeCount(likeCount)
                    .postDate(postEntity.getPostDate())
                    .build();

            postDtoList.add(postDto);

        }
        return postDtoList;
    }

    @Override
    public MemberDto findByEmail(String email) {
        MemberEntity memberEntity = memberRepository.findOneByEmail(email);
//        if (memberEntity == null) {
//            throw new NullPointerException();
//        }
//        MemberDto memberDto = memberRepository.findOneByEmail(email).map(
//                (MemberEntity memberEntity) -> MemberDto.builder().memberEntity(memberEntity).build()
//        ).orElseThrow(
//                () -> new NullPointerException()
//        );
        return MemberDto.builder().memberEntity(memberEntity).build();
    }

    @Override //회원정보 수정
    public void updateUser(String email, MemberDto memberDto, MultipartFile userImg) {

        MemberEntity memberEntity = memberRepository.findOneByEmail(email);//수정전 회원정보 가져오기
        String oldImg = memberEntity.getUserImg(); //수정전 이미지 파일명
        
        String rawPassword = memberDto.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword); //비밀번호 암호화

        memberEntity.setPassword(encodePassword); //새로운 회원 비밀번호 저장
        memberEntity.setIntroduction(memberDto.getIntroduction()); //새로운 자기소개 저장

        String uploadPath = "C:/image/profile"; // 이미지를 저장할 디렉토리 경로 설정

        if (userImg != null) { //이미지 교체 시작

            String originalName = userImg.getOriginalFilename(); //선택한 파일명
            String saveName = uploadPath + File.separator + originalName;

            if(!saveName.equals(oldImg)) {
                //기존 old파일 삭제
                File oldFile = new File(oldImg);
                oldFile.delete(); //기존 이미지 삭제

                //새로운 이미지 파일 업로드
                Path savePath = Paths.get(saveName);

                try {
                    userImg.transferTo(savePath);
                    memberEntity.setUserImg(saveName);
                } catch (Exception e) {
                    e.getMessage();
                }
            }

        } else { //이미지가 없을 경우
            memberEntity.setUserImg(uploadPath + "/default.jpg");

        }
        memberRepository.save(memberEntity);
    }

}