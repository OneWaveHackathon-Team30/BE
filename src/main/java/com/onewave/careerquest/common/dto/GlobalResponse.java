package com.onewave.careerquest.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalResponse<T> {
    private final Integer status;
    private T data;
    private String message;
    private ErrorResponse.ErrorDetails error;

    // 성공 (데이터 포함)
    public static <T> GlobalResponse<T> success(int status, T data) {
        return new GlobalResponse<>(status, data, null, null);
    }

    // 성공 (메시지만 포함)
    public static <T> GlobalResponse<T> success(int status, String message) {
        return new GlobalResponse<>(status, null, message, null);
    }
    
    // 실패
    public static <T> GlobalResponse<T> error(int status, String errorMessage) {
        return new GlobalResponse<>(status, null, null, new ErrorResponse.ErrorDetails(status, errorMessage));
    }

    private GlobalResponse(Integer status, T data, String message, ErrorResponse.ErrorDetails error) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.error = error;
    }
}
