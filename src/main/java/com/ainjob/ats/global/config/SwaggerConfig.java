package com.ainjob.ats.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ainjobOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AinJob ATS API")
                        .description("지원자 검색 및 진행상태 변경 API 문서")
                        .version("v1.0.0")
                );
    }
}