package com.futbol.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title       = "API Gestión Equipo de Fútbol",
        version     = "1.0",
        description = "API REST completa para gestionar equipos, jugadores, entrenadores, "
                    + "partidos y estadísticas de jugadores. Proyecto académico desarrollado "
                    + "con Spring Boot 3 + Supabase (PostgreSQL).",
        contact = @Contact(
            name  = "Equipo Académico",
            email = "contacto@futbol-api.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url  = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(url = "/", description = "Servidor actual")
    }
)
@Configuration
public class OpenApiConfig {
    // La configuración principal se realiza mediante las anotaciones @OpenAPIDefinition
}
