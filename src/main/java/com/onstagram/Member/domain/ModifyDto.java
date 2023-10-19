package com.onstagram.Member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ModifyDto {
    private String password;
    private String userImg;
    private String introduction;
}
