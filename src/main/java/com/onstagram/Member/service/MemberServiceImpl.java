package com.onstagram.Member.service;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override //회원가입
    public Long join(MemberEntity memberEntity) {

        //비밀번호 암호화
        String rawPassword = memberEntity.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);

        memberEntity.setPassword(encodePassword);
        memberEntity.setUserImg("C:/image/profile/default.jpg");

        memberRepository.save(memberEntity); //DB에 회원정보 저장
        return memberEntity.getUserId();
    }

    @Override //아이디 중복체크
    public boolean IdCheck(String email) {
        return memberRepository.findOneByEmail(email)!=null; //존재하면 true, 존재 x false
    }

    @Override //로그인할때 비밀번호 체크(복호화)
    public MemberDto checkPassword(MemberDto memberDto) {

        MemberEntity memberEntity = memberRepository.findOneByEmail(memberDto.getEmail());
        //true : 비밀번호 일치(로그인 성공), false: 비밀번호 불일치
        boolean result = bCryptPasswordEncoder.matches(memberDto.getPassword(), memberEntity.getPassword());

        return result ? MemberDto.builder().memberEntity(memberEntity).build() : null;
    }

    @Override //해당 회원 회원정보
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