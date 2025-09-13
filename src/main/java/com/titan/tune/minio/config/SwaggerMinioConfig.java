package com.titan.tune.minio.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.media.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
@Configuration
public class SwaggerMinioConfig {

    @Value("${server.port:8081}")
    private String serverPort;

    @Bean
    public GroupedOpenApi minioApi() {
        return GroupedOpenApi.builder()
                .group("minio")  // Changé de "nitchcorp/titan/tunes/minio" à "minio"
                .packagesToScan("nitchcorp.titan.tunes.minio.controller")
                .pathsToMatch("/files/**")  // Supprimé le préfixe /api car il est déjà dans le context-path
                .build();
    }

    @Bean
    @Primary
    public OpenAPI customMinioOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        Server server = new Server()
                .url("/api")  // Simplifié car le context-path est déjà géré
                .description("Serveur de développement MinIO");

        // Schéma pour les fichiers multiples
        Schema<?> fileSchema = new Schema<Object>()
                .type("string")
                .format("binary")
                .description("Fichier à uploader");

        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("API MinIO - Titan Tunes")
                        .description("API pour la gestion des fichiers multimédias avec MinIO")
                        .version("1.0.0"))
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