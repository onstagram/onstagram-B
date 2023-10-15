package com.onstagram.Member.service;

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

import java.util.ArrayList;
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
    public boolean validateDuplicateMember(String email) {
        List<MemberEntity> findMembers = memberRepository.findbyEmail(email);
        if (!findMembers.isEmpty()) { // 중복 & 아이디 존재
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPassword(MemberEntity memberEntity) {
        MemberEntity memberEntity1 = memberRepository.findOneByEmail(memberEntity.getEmail());
        //true : 비밀번호 일치(로그인 성공), false: 비밀번호 불일치
        return bCryptPasswordEncoder.matches(memberEntity.getPassword(), memberEntity1.getPassword());
    }

    @Override//회원 게시물 정보 찾기
    public List<PostDto> findById(Long id) {

        List<PostDto> postDtoList = new ArrayList<>();

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

}
