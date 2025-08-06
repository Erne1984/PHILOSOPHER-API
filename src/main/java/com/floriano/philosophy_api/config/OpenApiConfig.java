package com.floriano.philosophy_api.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ernesto",
                        email = "ernestofamorim@gmail.com"
                ),
                description = "OpenApi documentation for Philosopher API",
                title = "OpenApi specification - Philosopher Api",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/api"
                )
        }
)
public class OpenApiConfig {

}

