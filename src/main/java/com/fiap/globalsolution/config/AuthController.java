package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller para autenticação JWT
 * Endpoint de login para gerar tokens
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação JWT")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    /**
     * Request para login
     */
    public record LoginRequest(String username, String password) {}

    /**
     * Response com token JWT
     */
    public record TokenResponse(String token) {}

    /**
     * POST /auth/login - Endpoint de login
     *
     * Usuários disponíveis:
     * - admin/admin (ROLE_ADMIN)
     * - user/user (ROLE_USER)
     */
    @Operation(
            summary = "Login na aplicação",
            description = """
                Autentica o usuário e retorna um token JWT válido por 1 hora.
                
                **Usuários disponíveis:**
                - username: `admin` / password: `admin` (ROLE_ADMIN)
                - username: `user` / password: `user` (ROLE_USER)
                
                **Como usar o token:**
                1. Copie o token retornado
                2. Adicione no header Authorization: `Bearer {token}`
                3. Faça requisições para endpoints protegidos
                
                **Exemplo de Request:**
                ```json
                {
                  "username": "admin",
                  "password": "admin"
                }
                ```
                
                **Exemplo de Response:**
                ```json
                {
                  "token": "eyJhbGciOiJIUzI1NiJ9..."
                }
                ```
                """,
            tags = {"Autenticação"}
    )
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        // Autentica o usuário
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );

        // Gera o token JWT
        String token = jwtService.generateToken(
                auth.getName(),
                Map.of("role", auth.getAuthorities().iterator().next().getAuthority())
        );

        return ResponseEntity.ok(new TokenResponse(token));
    }
}