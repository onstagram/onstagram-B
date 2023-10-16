package com.onstagram.comment.entity;

import com.onstagram.member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
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
@Table(name = "reply")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    private String content;

    @CreatedDate
    private LocalDate commentDate;

    @PrePersist //엔티티를 만들고 persist(save) 메서드를 호출할 때
    public void onPrePersist() {
        this.commentDate = LocalDate.now();
    }
}
