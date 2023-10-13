package com.onstagram.member.domain;

import com.onstagram.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JoinStatus {

    private int status;
    private String errormessage;
    private MemberEntity memberEntity;

}