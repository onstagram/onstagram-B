package com.onstagram.user.domain;

import com.onstagram.user.entity.UserEntity;
import lombok.*;

@Getter@Setter
@Builder
@AllArgsConstructor
public class JoinStatus {

    private int status;
    private String errormessage;
    private UserEntity userEntity;

}