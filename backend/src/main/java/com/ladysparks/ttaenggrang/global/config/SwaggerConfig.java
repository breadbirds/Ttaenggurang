package com.ladysparks.ttaenggrang.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SwaggerConfig {

    private Info apiInfo() {
        return new Info()
                    .title("땡그랑 API")
                    .version("v1")
                    .description("땡그랑 API 명세서");
    }

    @Bean
    public OpenAPI openAPI() {
//        String jwt = "JWT";
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
//        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
//                .name(jwt)
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT")
//        );

        Server server = new Server();
        server.setUrl("https://i12d107.p.ssafy.io");

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .addServersItem(server);
//                .addSecurityItem(securityRequirement)
//                .components(components);
    }

    @Bean
    public GroupedOpenApi teacherApi() {
        return GroupedOpenApi.builder()
                .group("teachers")
                .pathsToMatch("/teachers/**")
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GroupedOpenApi studentApi() {
        return GroupedOpenApi.builder()
                .group("students")
                .pathsToMatch("/students/**")
                .build();
    }

    @Bean
    public GroupedOpenApi itemApi() {
        return GroupedOpenApi.builder()
                .group("items")
                .pathsToMatch("/items/**", "/item-transactions/**")
                .build();
    }

    @Bean
    public GroupedOpenApi savingsGoalApi() {
        return GroupedOpenApi.builder()
                .group("savings-goal")
                .pathsToMatch("/savings-goals/**")
                .build();
    }

    @Bean
    public GroupedOpenApi bankApi() {
        return GroupedOpenApi.builder()
                .group("bank")
                .pathsToMatch("/bank-account/**", "/bank-transactions/**", "/savings-products/**", "/savings-subscriptions/**")
                .build();
    }
    @Bean
    public GroupedOpenApi stockApi() {
        return GroupedOpenApi.builder()
                .group("stock")
                .pathsToMatch("/stocks/**")
                .build();
    }
    @Bean
    public GroupedOpenApi etfApi() {
        return GroupedOpenApi.builder()
                .group("etf")
                .pathsToMatch("/etfs/**")
                .build();
    }

}