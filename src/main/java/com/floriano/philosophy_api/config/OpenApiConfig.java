package com.floriano.philosophy_api.config;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "Philosopher API - OpenAPI Specification",
                description = "API for querying, creating, and managing philosophers, works, "
                        + "schools of thought, and quotes. "
                        + "Provides endpoints to explore the history of philosophy and its connections.",
                version = "1.0",
                contact = @Contact(
                        name = "Ernesto Floriano Amorim",
                        email = "ernestofamorim@gmail.com",
                        url = "https://github.com/Erne1984"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8080/api"
                ),
                @Server(
                        description = "Production Environment",
                        url = "https://philosopher-api.onrender.com/api/"
                )
        },
        tags = {
                @Tag(name = "Philosophers", description = "Endpoints related to philosophers"),
                @Tag(name = "Works", description = "Management of philosophical works"),
                @Tag(name = "Quotes", description = "Query and creation of quotes"),
                @Tag(name = "Themes", description = "Management of philosophical themes"),
                @Tag(name = "SchoolsOfThought", description = "Philosophical schools and intellectual traditions"),
                @Tag(name = "Countries", description = "Current or historical countries registered in the system"),
                @Tag(name = "Authentications", description = "Authentication routes")
        },
        externalDocs = @ExternalDocumentation(
                description = "Full documentation, request/response examples, and usage guides",
                url = "https://github.com/ernestofamorim/philosophy-api-docs"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Authentication using the Bearer scheme. "
                + "Include the JWT token in the Authorization header: Bearer {token}",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
