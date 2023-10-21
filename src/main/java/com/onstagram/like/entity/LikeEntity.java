package com.onstagram.like.entity;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@Table(name="like_info")
@AllArgsConstructor
public class LikeEntity {

    public LikeEntity() { super();}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_info_sequence")
    @SequenceGenerator(name = "like_info_sequence", sequenceName = "like_info_sequence", allocationSize = 1)
    private Long likeId; //좋아요 id(시퀀스)

    @ManyToOne//회원(1) : 게시물(many)
    @JoinColumn(name = "user_id")
    private MemberEntity memberEntity; //Member 테이블의 user_id 참조

    @ManyToOne//회원(1) : 게시물(many)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity; //POST 테이블의  post_id 참조

    @CreatedDate
    private LocalDate likeDate; //좋아요 날짜

    @PrePersist //엔티티를 만들고 persist(save) 메서드를 호출할 때
    public void onPrePersist(){
        this.likeDate = LocalDate.now();
    }



}
