package com.onstagram.comment.domain;

import com.onstagram.member.entity.MemberEntity;
import com.onstagram.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentDto {

    private Long commentId;

    private Long userId;

    private Long postId;

    private String content;

    private LocalDate commentDate;
}
