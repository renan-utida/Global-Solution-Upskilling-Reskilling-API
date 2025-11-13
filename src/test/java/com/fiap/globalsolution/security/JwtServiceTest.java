package com.fiap.globalsolution.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do JwtService")
public class JwtServiceTest {

    private JwtService jwtService;

    private final String SECRET = "global-solution-2025-fiap-upskilling-reskilling-secret-key";
    private final long EXPIRATION = 3600000; // 1 hora

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService(SECRET, EXPIRATION);
    }

    @Test
    @DisplayName("Deve gerar um token JWT válido")
    public void deveGerarTokenJwtValido() {
        // Arrange
        String username = "testuser";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_USER");

        // Act
        String token = jwtService.generateToken(username, claims);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3); // JWT tem 3 partes separadas por ponto
    }

    @Test
    @DisplayName("Deve extrair o username corretamente do token")
    public void deveExtrairUsernameDoToken() {
        // Arrange
        String username = "testuser";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_USER");
        String token = jwtService.generateToken(username, claims);

        // Act
        String extractedUsername = jwtService.extractUsername(token);

        // Assert
        assertNotNull(extractedUsername);
        assertEquals(username, extractedUsername);
    }

    @Test
    @DisplayName("Deve validar token como válido quando username corresponde")
    public void deveValidarTokenComoValidoQuandoUsernameCorresponde() {
        // Arrange
        String username = "testuser";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_USER");
        String token = jwtService.generateToken(username, claims);

        // Act
        boolean isValid = jwtService.isTokenValid(token, username);

        // Assert
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Deve validar token como inválido quando username não corresponde")
    public void deveValidarTokenComoInvalidoQuandoUsernameNaoCorresponde() {
        // Arrange
        String username = "testuser";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_USER");
        String token = jwtService.generateToken(username, claims);

        // Act
        boolean isValid = jwtService.isTokenValid(token, "differentuser");

        // Assert
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Deve extrair claims customizados do token")
    public void deveExtrairClaimsCustomizadosDoToken() {
        // Arrange
        String username = "testuser";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_ADMIN");
        String token = jwtService.generateToken(username, claims);

        // Act
        String role = jwtService.extractClaim(token, c -> c.get("role", String.class));

        // Assert
        assertNotNull(role);
        assertEquals("ROLE_ADMIN", role);
    }
}