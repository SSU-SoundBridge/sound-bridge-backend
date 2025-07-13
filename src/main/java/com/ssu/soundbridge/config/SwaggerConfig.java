package com.ssu.soundbridge.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        // 액세스 토큰 (표준 Bearer, Authorization 헤더)
        SecurityScheme accessTokenScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization")          // ← 이름 무시돼도 관례상 적어 둡니다
                .in(SecurityScheme.In.HEADER);

        // 리프레시 토큰 (커스텀 헤더, apiKey 방식)
        SecurityScheme refreshTokenScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)  // ← 중요!
                .name("Authorization-refresh")     // 실제 헤더 이름
                .in(SecurityScheme.In.HEADER)
                .description("값을 넣을 때 'Bearer ' 접두사를 포함하세요");

        Components components = new Components()
                .addSecuritySchemes("AccessToken", accessTokenScheme)
                .addSecuritySchemes("RefreshToken", refreshTokenScheme);

        // reissue 같은 엔드포인트에서 둘 다 쓰고 싶다면 ↓
        SecurityRequirement global = new SecurityRequirement()
                .addList("AccessToken")
                .addList("RefreshToken");

        return new OpenAPI()
                .components(components)
                .addSecurityItem(global)  // 또는 각 메서드에 @SecurityRequirement
                .info(new Info()
                        .title("SoundBridge API")
                        .description("SoundBridge 프로젝트의 API 명세서입니다.")
                        .version("v1.0.0"));
    }
}

