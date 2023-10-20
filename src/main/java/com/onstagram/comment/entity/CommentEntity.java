package com.onstagram.comment.entity;


import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="reply")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", allocationSize = 1)
    private Long commentId;

    @ManyToOne//회원(1) : 댓글(many)
    @JoinColumn(name = "USER_ID")
    private MemberEntity memberEntity;

    @ManyToOne//게시물(1) : 댓글(many)
    @JoinColumn(name = "POST_ID")
    private PostEntity postEntity;

    private String content;

    private LocalDate commentDate;

    @PrePersist //엔티티를 만들고 persist(save) 메서드를 호출할 때
    public void onPrePersist() {
        this.commentDate = LocalDate.now();
    }

}