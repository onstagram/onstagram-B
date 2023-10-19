package com.onstagram;

import com.onstagram.post.domain.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostDataList {
    private int errcode;
    private boolean success;
    private List<PostDto> data;
}