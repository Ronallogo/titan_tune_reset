package com.titan.tune.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class  OpenApiConfig {


    @Bean
    public GroupedOpenApi Albums(){
        return GroupedOpenApi.builder()
                .displayName("Albums")
                .group("albums")
                .packagesToScan("com.titan.tune.Controller")
                .pathsToMatch("/albums/**")
                .build();
    }


    @Bean
    public GroupedOpenApi  Categories(){
        return GroupedOpenApi.builder()
                .displayName("Categories")
                .group("categories")
                .packagesToScan("com.titan.tune.Controller")
                .pathsToMatch("/categories/**")
                .build();
    }

    @Bean GroupedOpenApi Suivre(){
        return  GroupedOpenApi.builder()
                .displayName("Suivre")
                .group("suivre")
                .packagesToScan("com.titan.tune.Controller")
                .pathsToMatch("/suivre/**")
                .build();
    }

    @Bean
    public GroupedOpenApi User(){
        return GroupedOpenApi.builder()
                .displayName("user")
                .group("user")
                .packagesToScan("com.titan.tune.Controller")
                .pathsToMatch("/user/**")
                .build();
    }
    @Bean
    public GroupedOpenApi Favoris(){
        return  GroupedOpenApi.builder()
                .displayName("favoris")
                .group("favoris")
                .packagesToScan("com.titan.tune.Controller")
                .pathsToMatch("/favoris/**")
                .build();
    }


    @Bean
    public GroupedOpenApi Playlist(){
        return  GroupedOpenApi.builder()
                .displayName("playlist")
                .group("playlist")
                .packagesToScan("com.titan.tune.Controller")
                .pathsToMatch("/playlist/**")
                .build();
    }

    @Bean
    public GroupedOpenApi Song(){
        return GroupedOpenApi.builder()
                .displayName("song")
                .group("song")
                .packagesToScan("com.titan.tune.Controller")
                .pathsToMatch("/song/**")
                .build();
    }

    @Bean
    public GroupedOpenApi SongEcouter(){

        return  GroupedOpenApi.builder()
                .displayName("song_ecouter")
                .group("songEcouter")
                .packagesToScan("com.titan.tune.Controller")
                .pathsToMatch("/songEcouter/**")
                .build();
    }


    @Bean
    public GroupedOpenApi Statistique(){
        return  GroupedOpenApi.builder()
                .displayName("statistique")
                .group("stats")
                .packagesToScan("com.titan.tune.Controller")
                .pathsToMatch("/stats/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Titan Tune API")
                        .version("1.0.0")
                        .description("Documentation de l'API pour l'application Titan Tune.")
                        .contact(new Contact()
                                .name("nitchcorp")
                                .email("nitchcorp@gmail.com")
                                .url("http://www.nitchcorp.tech"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}
