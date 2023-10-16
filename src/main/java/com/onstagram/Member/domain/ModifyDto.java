package com.onstagram.Member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ModifyDto {
    private String userImg;
    private String introduction;
    private String password;
}
