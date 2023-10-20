package com.onstagram.search.domain;

import com.onstagram.Member.domain.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class searchResult {
    private int errcode;
    private boolean success;
    private List<MemberDto> data;
}
