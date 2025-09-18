package com.titan.tune.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${spring.profiles.active:prod}")
    private String activeProfile;

    private Server buildServer() {
        Server server = new Server();
        if ("prod".equalsIgnoreCase(activeProfile)) {
            server.setUrl("http://api.nitchcorp.com/api");
            server.setDescription("Production server");
        } else {
            server.setUrl("http://localhost:" + serverPort);
            server.setDescription("Development server");
        }
        return server;
    }

    private Contact buildContact() {
        return new Contact()
                .name("Nitchcorp Support")
                .email("support@nitchcorp.com")
                .url("https://nitchcorp.com");
    }

    // 🔹 Groupe MinIO
    @Bean
    public GroupedOpenApi minioApi() {
        return GroupedOpenApi.builder()
                .group("minio")
                .packagesToScan("com.titan.tune.minio.controller")
                .pathsToMatch("/files/**")
                .build();
    }

    // 🔹 Groupe Albums
    @Bean
    public GroupedOpenApi albumApi() {
        return GroupedOpenApi.builder()
                .group("albums")
                .packagesToScan("com.titan.tune.Controller")
                .pathsToMatch("/api/albums/**")
                .addOpenApiCustomizer(openApi ->
                        openApi.info(new Info()
                                        .title("Album Management API")
                                        .version("1.0.0")
                                        .description("Gestion des albums musicaux")
                                        .contact(buildContact()))
                                .servers(List.of(buildServer()))
                )
                .build();
    }

    // 🔹 Groupe Catégories
    @Bean
    public GroupedOpenApi categoriesApi() {
        return GroupedOpenApi.builder()
                .group("categories")
                .packagesToScan("com.titan.tune.Controller")
                .pathsToMatch("/api/categories/**")
                .addOpenApiCustomizer(openApi ->
                        openApi.info(new Info()
                                        .title("Categories API")
                                        .version("1.0.0")
                                        .description("Gestion des catégories musicales")
                                        .contact(buildContact()))
                                .servers(List.of(buildServer()))
                )
                .build();
    }

    // 🔹 Configuration principale OpenAPI
    @Bean
    @Primary
    public OpenAPI globalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Titan Tunes Backend API")
                        .version("1.0.0")
                        .description("Application de gestion des songs et artistes comme Spotify")
                        .contact(buildContact()))
                .servers(List.of(buildServer()));
    }

    // 🔹 Configuration spécifique MinIO avec sécurité
    @Bean
    public OpenAPI minioOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        Schema<?> fileSchema = new Schema<>()
                .type("string")
                .format("binary")
                .description("Fichier à uploader");

        return new OpenAPI()
                .info(new Info()
                        .title("API MinIO - Titan Tunes")
                        .version("1.0.0")
                        .description("API pour la gestion des fichiers multimédias avec MinIO")
                        .contact(buildContact()))
                .servers(List.of(buildServer()))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                        .addSchemas("fileSchema", fileSchema));
    }
}
