package com.onewave.careerquest.comments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 생성 요청 DTO")
public class CommentCreateRequestDto {
    
    @Schema(description = "댓글 내용", 
            example = "정말 훌륭한 솔루션입니다! 특히 성능 최적화 부분이 인상적이네요. 다만 에러 핸들링 부분을 추가하면 더 좋을 것 같습니다.", 
            required = true)
    private String content;
}
