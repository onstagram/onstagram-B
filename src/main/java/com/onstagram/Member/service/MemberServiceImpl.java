package com.onstagram.Member.service;

import com.onstagram.Member.domain.*;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.repository.MemberRepository;
import com.onstagram.exception.OnstagramException;
import com.onstagram.file.FileService;
import com.onstagram.follow.repository.FollowRepository;
import com.onstagram.jwt.JwtTokenProvider;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.entity.PostEntity;
import com.onstagram.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; //비밀 번호 암호화
    private final JwtTokenProvider jwtTokenProvider;
    //토큰 생성하기 위해
    private final FileService fileService;//파일 업로드 or 삭제하기 위해

//    @org.springframework.beans.factory.annotation.Value("${spring.member.path}")
    private String path = "member"; //회원 사진 저장 경로
//
//    @org.springframework.beans.factory.annotation.Value("${spring.member.fileName}")
    private String defaultFileName = "default.png"; //회원 기본 이미지 파일명
    
    @Override
    public Long getUserId(HttpServletRequest request) { //토큰 받아와서 아이디 값 리턴

        String token = jwtTokenProvider.resolveToken(request); //토큰값 가져오기

        log.info("토큰값 : " + token);

        if (token != null && jwtTokenProvider.validateToken(token)) { //토큰이 있고 유효기간이 남았으면
            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            return userId;
        }
        else if(token == null) {
            throw new OnstagramException(HttpStatus.REQUEST_TIMEOUT.value(), "토큰 값이 없다.");
        }
        else {
            throw new OnstagramException(HttpStatus.REQUEST_TIMEOUT.value(), "토큰 유효기간 만료");
        }
    }

    @Override //회원가입
    public Long join(MemberEntity memberEntity) {

        //비밀번호 암호화
        String rawPassword = memberEntity.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);

        memberEntity.setPassword(encodePassword);
        memberEntity.setUserImg("https://onstagram2.s3.ap-northeast-2.amazonaws.com/member/default.png");

        memberRepository.save(memberEntity); //DB에 회원정보 저장
        return memberEntity.getUserId();
    }

    @Override //아이디 중복체크
    public boolean IdCheck(String email) {
        return !memberRepository.findOneByEmail(email).isEmpty(); //존재하면 true, 존재 x false
    }

    @Override //로그인 처리후 토큰 반환
    public String signin(SignInDto signInDto) {
        log.info("로그인 시작 서비스 들어옴");
        List<MemberEntity> members = memberRepository.findOneByEmail(signInDto.getEmail());
        if (members.isEmpty()) {
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "아이디가 없다.");
//            return null; //아이디가 존재x
        }
        log.info("아이디 존재");
        MemberEntity member = members.get(0);
        log.info("로그인 비밀번호 : " + signInDto.getPassword());
        log.info("기존 회원 비밀번호와 매칭 : " + bCryptPasswordEncoder.matches(signInDto.getPassword(), member.getPassword()));
        if (!bCryptPasswordEncoder.matches(signInDto.getPassword(), member.getPassword())) {
            log.info("비밀번호 불일치");
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "비밀번호 불일치");
//            return null; //비밀번호 불일치
        }
        String token = jwtTokenProvider.createToken(member.getEmail(),member.getUserId());

        if(token == null) {
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "토큰생성 실패");
        }

        // 로그인에 성공하면 email, userId 토큰 생성 후 반환
        return token;
    }

    @Override //해당 회원 회원정보
    public MemberDto findById(Long userId) {
        List<MemberEntity> members;
        try {
            members = memberRepository.findById(userId);
            return MemberDto.builder().memberEntity(members.get(0)).build();
        }catch (Exception e) {
            //return null;
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "회원정보찾기 실패");
        }
    }

    @Override //회원정보 수정
    public MemberDto updateUser(Long userId, ModifyDto modifyDto, MultipartFile file) {
        //이미지,
        log.info("회원정보 수정 서비스 들어옴");
        log.info("자기소개 : " + modifyDto.getIntroduction());

        MemberEntity memberEntity = memberRepository.findById(userId).get(0); //기존 회원 정보 받아오기

        //수정할 비밀번호 암호화
        String rawPassword = modifyDto.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword); //수정한 비밀번호
        String userImg; //수정할 이미지

        if (!file.isEmpty()) { //수정할 파일이 있으면

            String[] str = memberEntity.getUserImg().split("/");
            String oldImg = str[str.length-1];

            if(!oldImg.equals(defaultFileName)) { //기존(수정전)이미지가 default.png인 기본이미지이면 삭제
                fileService.DeleteFile(path + "/" + oldImg); //기존에 이미지 삭제
            }
            userImg = fileService.FileUpload(file, path); //path(member)경로에 파일 업로드 후 url 받기

        } else {
            userImg = memberEntity.getUserImg();//수정할 파일이 없으면 기존 파일 url 그대로 사용.
        }

        String intro = modifyDto.getIntroduction().isEmpty() ? "" : modifyDto.getIntroduction();
        //최종 수정한 이미지명, 비밀번호, 자기소개 값을 dto에 넣기
        modifyDto = new ModifyDto(encodePassword, userImg, modifyDto.getIntroduction());
        //기존에 회원정보인 memberEntity 값의 수정할 값인 NewModifyDto 값만 변경해주기
        memberEntity.modifyEntity(modifyDto);

        try {
            memberRepository.save(memberEntity);
            return MemberDto.builder().memberEntity(memberEntity).build();

        } catch (Exception e) {
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "회원정보수정 실패");
        }

    }

    @Override
    public MypageDto profileInfo(Long userId, Long loginId) { //해당 회원의 프로필

        try {
            //해당 회원의 정보
            MemberDto memberDto = MemberDto.builder()
                    .memberEntity(memberRepository.findById(userId).get(0)).build();

            List<PostDto> postDtoListlist = new ArrayList<>();
            //해당 회원의 게시물 목록
            for(PostEntity postEntity : postRepository.findUserPost(userId)) {
                postDtoListlist.add(PostDto.builder()
                        .postEntity(postEntity)
                        .build());
            }

            //해당 회원의 팔로우 정보
            int followcount = followRepository.countFollowers(userId); //팔로우 개수
            int followingcount = followRepository.countFollowing(userId); //팔로잉 개수

            Long pageId = memberDto.getUserId(); //프로필 주인
            int check = loginId.equals(pageId) ? 0 : followCheck(userId,loginId);

            FollowInfo followInfo = new FollowInfo(followcount, followingcount , check);

            return new MypageDto(memberDto,postDtoListlist,followInfo);

        } catch (Exception e) {
            e.printStackTrace();
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "실패");
        }


    }

    @Override
    public int followCheck(Long userId, Long loginId) {
        boolean check = followRepository.followCheck(userId,loginId);
        return check ? 1 : 2; //팔로우했으면 1, 안했으면 2
    }


}