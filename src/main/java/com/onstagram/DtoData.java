package com.onstagram;

import com.onstagram.Member.domain.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DtoData {
    private int errcode;
    private boolean success;
    private Object data;
}
