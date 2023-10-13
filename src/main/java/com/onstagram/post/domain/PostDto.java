package com.onstagram.post.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
public class PostDto {

    private Long userId; ////회원테이블 참조(외래키)

    @NotEmpty(message = "게시물 설명은 작성")
    private String caption; //게시물 설명

    private LocalDate postDate; //게시물 생성일

}