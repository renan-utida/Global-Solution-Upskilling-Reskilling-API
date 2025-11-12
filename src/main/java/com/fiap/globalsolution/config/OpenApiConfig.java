package com.fiap.globalsolution.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração do OpenAPI/Swagger para documentação da API
 * Acesse: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // Informações sobre a API
                .info(new Info()
                        .title("Global Solution 2025 - API Upskilling/Reskilling")
                        .version("1.0.0")
                        .description("""
                                # 🌍 Plataforma de Upskilling/Reskilling para o Futuro do Trabalho
                                
                                API RESTful desenvolvida para a **Global Solution 2025** com o tema "O Futuro do Trabalho".
                                
                                ## 📋 Sobre o Projeto
                                
                                Esta plataforma permite que profissionais:
                                - 👤 Se cadastrem como usuários
                                - 📖 Acessem trilhas de aprendizagem focadas em competências do futuro
                                - ✅ Se inscrevam em trilhas para se requalificar profissionalmente
                                
                                ## 🎯 Objetivos de Desenvolvimento Sustentável (ODS)
                                
                                - **ODS 4** - Educação de Qualidade
                                - **ODS 8** - Trabalho Decente e Crescimento Econômico
                                - **ODS 9** - Indústria, Inovação e Infraestrutura
                                - **ODS 10** - Redução das Desigualdades
                                
                                ## 🔐 Autenticação
                                
                                A API utiliza **JWT (JSON Web Token)** para autenticação.
                                
                                **Como usar:**
                                1. Faça login em `/auth/login` com: `admin/admin` ou `user/user`
                                2. Copie o token retornado
                                3. Clique em **"Authorize"** (cadeado) no topo da página
                                4. Cole o token no campo "Value"
                                5. Clique em "Authorize"
                                
                                ## 🛠️ Tecnologias
                                
                                - Java 17
                                - Spring Boot 3.4.10
                                - Spring Data JPA
                                - Spring Security + JWT
                                - H2 Database
                                - Flyway Migrations
                                - Bean Validation
                                - Thymeleaf
                                
                                ## 📚 Recursos Disponíveis
                                
                                - **Usuários** - Profissionais/alunos da plataforma
                                - **Trilhas** - Cursos de aprendizagem
                                - **Competências** - Habilidades do futuro
                                - **Matrículas** - Inscrições em trilhas
                                """)
                        .contact(new Contact()
                                .name("Renan Dias Utida")
                                .email("rm558540@fiap.com.br")
                                .url("https://github.com/renan-utida"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))

                // Servidores
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local de Desenvolvimento")))

                // Esquema de segurança JWT
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Insira o token JWT obtido no endpoint /auth/login")))

                // Requisito de segurança global
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }
}