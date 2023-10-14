package com.onstagram.member.domain;

import com.onstagram.member.entity.MemberEntity;
import lombok.*;

@Getter@Setter
@Builder
@AllArgsConstructor
public class JoinStatus {

    private int status;
    private String errormessage;
    private MemberEntity memberEntity;

}