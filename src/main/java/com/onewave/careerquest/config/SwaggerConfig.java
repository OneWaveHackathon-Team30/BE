package com.onewave.careerquest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwt = "jwtAuth";

        // API 요청 시 사용할 보안 요구 사항 설정
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);

        // SecurityScheme 설정 (Bearer Token 방식)
        Components components = new Components()
                .addSecuritySchemes(jwt, new SecurityScheme()
                        .name(jwt)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")); // Swagger UI에 표시될 형식

        return new OpenAPI()
                .info(apiInfo())
                .addServersItem(new Server().url("https://be-393047322674.asia-northeast3.run.app").description("Production Server"))
                .addServersItem(new Server().url("http://localhost:8080").description("Local Server"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
    private Info apiInfo() {
        return new Info()
                .title("API Test") // API의 제목
                .description("Let's practice Swagger UI") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }


}
