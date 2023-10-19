package com.onstagram.post.entity;

import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.domain.PostModifyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@Table(name="post")
@AllArgsConstructor
public class PostEntity {

    public PostEntity() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    @SequenceGenerator(name = "post_sequence", sequenceName = "post_sequence", allocationSize = 1)
    private Long postId;

    @ManyToOne//회원(1) : 게시물(many)
    @JoinColumn(name = "user_id")
    private MemberEntity memberEntity; //회원테이블 참조(외래키)

    @Column(nullable = false, columnDefinition = "CLOB")
    private String caption;

    @Column(nullable = false)
    private String postImg;

    private Long likeCount; //좋아요 개수

    @CreatedDate
    private LocalDate postDate;

    @PrePersist //엔티티를 만들고 persist(save) 메서드를 호출할 때
    public void onPrePersist(){
        this.postDate = LocalDate.now();
    }
    
    public void modifyEntity(PostModifyDto postModifyDto) {
        caption = postModifyDto.getCaption();
        postDate = LocalDate.now(); //수정한 날짜로 변경
    }

}