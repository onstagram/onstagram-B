package com.onstagram.post.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostModifyDto {
    private Long postId;
    private String caption;
}
