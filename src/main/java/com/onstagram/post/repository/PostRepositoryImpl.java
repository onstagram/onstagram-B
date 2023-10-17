//package com.onstagram.post.repository;//package com.onstagram.member.repository;
//
//import com.onstagram.Member.entity.MemberEntity;
//import com.onstagram.post.entity.PostEntity;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import java.util.Optional;
//
//@Repository
//@RequiredArgsConstructor
//public class PostRepositoryImpl implements PostRepository {
//
//    private final EntityManager em;
//
//    @Override
//    public Optional<MemberEntity> findById(Long id) { //회원아이디로 회원정보 리턴
//        try {
//            MemberEntity memberEntity = em.createQuery("select m from MemberEntity m where m.id = :id", MemberEntity.class)
//                    .setParameter("id", id)
//                    .getSingleResult();
//            return Optional.ofNullable(memberEntity);
//        } catch (Exception e) {
//            // 해당 ID에 대한 결과가 없을 경우
//            return Optional.empty();
//        }
//
//
////        return em.createQuery("select m from MemberEntity  m where m.id = :id", MemberEntity.class)
////                .setParameter("id", id).getSingleResult();
//    }
//
//    @Override// 게시물 정보 등록
//    public void createPost(PostEntity postEntity) {
//        em.persist(postEntity); //게시물 등록
//    }
//
//}