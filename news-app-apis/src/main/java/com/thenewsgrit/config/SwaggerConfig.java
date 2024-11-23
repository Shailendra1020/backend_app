package com.thenewsgrit.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Define a SecurityScheme for JWT Bearer tokens
    private SecurityScheme apiKeyScheme() {
        return new SecurityScheme()
                .type(Type.HTTP)      // Specify the scheme type as HTTP
                .scheme("bearer")     // Use bearer tokens
                .bearerFormat("JWT")  // Specify the token format as JWT
                .in(In.HEADER)        // JWT is sent in the header
                .name(AUTHORIZATION_HEADER);  // Header name where the JWT will be sent
    }

    // Define the SecurityRequirement for JWT
    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList("JWT");  // Referencing the security scheme by name "JWT"
    }

    // OpenAPI Bean with JWT Security and API Info
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getApiInfo())  // API info
                .addSecurityItem(securityRequirement())  // Add JWT SecurityRequirement globally
                .components(new Components()
                        .addSecuritySchemes("JWT", apiKeyScheme()));  // Add SecurityScheme named "JWT"
    }

    // API Info (title, description, version, etc.)
    private Info getApiInfo() {
        return new Info()
                .title("News Application : TheNewsGrit")
                .description("Your news destination")
                .version("1.0")
                .termsOfService("Terms of service")
                .contact(new Contact()
                        .name("TheNewsGrit")
                        .url("https://thenewsgrit.com")
                        .email("thenewsgrit@gmail.com"))
                .license(new License()
                        .name("License of news platform")
                        .url("website license URL"));
    }

    // Grouped API configuration (if needed for path-based grouping)
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")  // Group name
                .pathsToMatch("/api/**")  // Adjust the paths as per your API structure
                .build();
    }

    // You can define additional groups if necessary, e.g., for admin API
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin-api")  // Another group name
                .pathsToMatch("/admin/**")
                .build();
    }
}
