package me.seungpang.swagger.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(title = "스웨거 테스트 API",
                description = "스웨거 테스트 API 문서입니다"
        ))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Value("${server.host}")
    private String serverHost;

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("오픈 API")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Authorization");

        Server server = new Server()
                .url(serverHost);

        return new OpenAPI()
                .servers(List.of(server))
                .components(new Components().addSecuritySchemes("Authorization", securityScheme))
                .security(List.of(securityRequirement));
    }
}
