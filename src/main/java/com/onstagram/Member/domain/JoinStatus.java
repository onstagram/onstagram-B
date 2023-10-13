package com.onstagram.Member.domain;

import com.onstagram.Member.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class JoinStatus {

    private int status;
    private String errormessage;
    private MemberEntity memberEntity;

}