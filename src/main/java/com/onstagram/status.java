package com.onstagram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class status {
    private boolean success;
    private int ErrorCode;
    private String ErrorMessage;
}