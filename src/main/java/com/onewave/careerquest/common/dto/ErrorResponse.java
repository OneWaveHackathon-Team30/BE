package com.onewave.careerquest.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final ErrorDetails error;

    @Getter
    @RequiredArgsConstructor
    public static class ErrorDetails {
        private final int status;
        private final String message;
    }
}
