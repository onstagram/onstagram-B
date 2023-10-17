package com.onstagram.Member.service;

import com.onstagram.Member.domain.MemberDetail;
import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.domain.SignInDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.repository.MemberRepository;
import com.onstagram.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; //비밀 번호 암호화
    private final JwtTokenProvider jwtTokenProvider; //토큰 생성하기 위해

    //토큰 받아오기
    @Override
    public String getEmail(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request); //토큰값 가져오기
        log.info("토큰값 : " + token);
        if (token != null && jwtTokenProvider.validateToken(token)) { //토큰이 있고 유효기간이 남았으면
            log.info("토큰 유효 가능");
            String email = jwtTokenProvider.getEmail(token);
            return email;
        } else {
            log.info("토큰 유효 불가능");
            return null;
//            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Override //회원가입
    public Long join(MemberEntity memberEntity) {

        //비밀번호 암호화
        String rawPassword = memberEntity.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);

        memberEntity.setPassword(encodePassword);
        memberEntity.setUserImg("https://onstagram.s3.ap-northeast-2.amazonaws.com/Member/default.png");

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
            return null; //아이디가 존재x
        }
        log.info("아이디 존재");
        MemberEntity member = members.get(0);
        log.info("로그인 비밀번호 : " + signInDto.getPassword());
        log.info("기존 회원 비밀번호와 매칭 : " + bCryptPasswordEncoder.matches(signInDto.getPassword(), member.getPassword()));
        if (!bCryptPasswordEncoder.matches(signInDto.getPassword(), member.getPassword())) {
            log.info("비밀번호 불일치");
            return null; //비밀번호 불일치
        }

        // 로그인에 성공하면 email, userId 토큰 생성 후 반환
        return jwtTokenProvider.createToken(member.getEmail(),member.getUserId());
    }

    @Override //해당 회원 회원정보
    public MemberDto findByEmail(String email) {
        List<MemberEntity> members = memberRepository.findOneByEmail(email);
        return members.isEmpty() ? null : MemberDto.builder().memberEntity(members.get(0)).build();
//        if (memberEntity == null) {
//            throw new NullPointerException();
//        }
//        MemberDto memberDto = memberRepository.findOneByEmail(email).map(
//                (MemberEntity memberEntity) -> MemberDto.builder().memberEntity(memberEntity).build()
//        ).orElseThrow(
//                () -> new NullPointerException()
//        );
    }

//    @Override //회원정보 수정
//    public void updateUser(MemberDto memberDto, MultipartFile userImg) {
//
//        MemberEntity memberEntity = memberRepository.findOneByEmail(memberDto.getEmail());//수정전 회원정보 가져오기
//        String oldImg = memberEntity.getUserImg(); //수정전 이미지 파일명
//
//        String rawPassword = memberDto.getPassword();
//        String encodePassword = bCryptPasswordEncoder.encode(rawPassword); //비밀번호 암호화
//
//        memberEntity.setPassword(encodePassword); //새로운 회원 비밀번호 저장
//        memberEntity.setIntroduction(memberDto.getIntroduction()); //새로운 자기소개 저장
//
//        String uploadPath = "C:/image/profile"; // 이미지를 저장할 디렉토리 경로 설정 --> 추후 S3로 변경
//
//        String originalName = userImg.getOriginalFilename(); //선택한 파일명
//        String saveName = uploadPath + File.separator + originalName;
//        System.out.println("saveName : " + saveName);
//
//        if(originalName.equals("default")) { //이미지 삭제해서 기본이미지 설정
//            memberEntity.setUserImg("defalut.jpg");
//        } else {
//            if(!originalName.equals(oldImg)) {
//
//            }
//        }
//
//
//            if(!saveName.equals(oldImg)) {
//                //기존 old파일 삭제
//                File oldFile = new File(oldImg);
//                oldFile.delete(); //기존 이미지 삭제
//
//                //새로운 이미지 파일 업로드
//                Path savePath = Paths.get(saveName);
//
//                try {
//                    userImg.transferTo(savePath);
//                    memberEntity.setUserImg(saveName);
//                } catch (Exception e) {
//                    e.getMessage();
//                }
//            }
//
//        } else { //이미지가 없을 경우
//            memberEntity.setUserImg(uploadPath + "/default.jpg");
//
//        }
//        memberRepository.save(memberEntity);
//    }

//    @Override//회원 게시물 정보 찾기
//    public List<PostDto> findById(Long id) {
//
//        List<PostDto> postDtoList = new ArrayList<>(); //회원게시물 리스트 생성
//
//        List<PostEntity> list = memberRepository.findbyId(id);
//
//        System.out.println("총 회원 게시물의 개수 : " + list.size());
//        System.out.println("list : " + list);
//
//        for(PostEntity postEntity : list) {
//            Long postId = postEntity.getPostId(); //게시물 아이디
//
//            List<PostImgEntity> imgDtoList = memberRepository.findByImgId(postId); //해당 게시물이미지 정보 리스트
//
//            List<PostImgDto> postImgDtoList = new ArrayList<>();
//
//            for(PostImgEntity postImgEntity : imgDtoList) { // Entity -> Dto로 변환
//                PostImgDto postImgDto = PostImgDto.builder()
//                        .imgId(postImgEntity.getImgId())
//                        .imgName(postImgEntity.getImgName())
//                        .postId(postId)
//                        .build();
//                postImgDtoList.add(postImgDto);
//            }
//
//            //좋아요 수
//            Long likeCount = memberRepository.likeCount(postId);
//
//            PostDto postDto = PostDto.builder()
//                    .postId(postEntity.getPostId())
//                    .caption(postEntity.getCaption())
//                    .userId(id)
//                    .imgList(postImgDtoList)
//                    .likeCount(likeCount)
//                    .postDate(postEntity.getPostDate())
//                    .build();
//
//            postDtoList.add(postDto);
//
//        }
//        return postDtoList;
//    }
//

//
//

}