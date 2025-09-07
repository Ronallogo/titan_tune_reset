package com.titan.tune.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Bean
    public OpenAPI customOpenAPI() {


        Server server = new Server();
        if ("prod".equalsIgnoreCase(activeProfile)) {
            server.setUrl("https://api.nitchcorp.com/api");
            server.setDescription("Production server");
        } else {
            server.setUrl("http://localhost:" + serverPort);
            server.setDescription("Development server");
        }


        Contact contact = new Contact()
                .name("Nitchcorp Support")
                .email("support@nitchcorp.com")
                .url("https://nitchcorp.com");


        Info info = new Info()
                .title("Titan_tunes Backend API")
                .version("1.0.0")
                .description("application de gestion des songs et artistes comme sportify")
                .contact(contact);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
