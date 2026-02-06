package com.onewave.careerquest.accounts.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @Schema(example = "200", description = "HTTP 상태 코드")
    private int status;

    @Schema(example = "로그인 상태 확인 성공", description = "응답 메시지")
    private String message;
}
