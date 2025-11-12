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
 * Controller REST para autenticação JWT
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação JWT")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    /**
     * DTO para requisição de login
     */
    public record LoginRequest(String username, String password) {}

    /**
     * DTO para resposta com token
     */
    public record TokenResponse(String token, String type, long expiresIn) {}

    /**
     * POST /auth/login - Autentica usuário e retorna token JWT
     */
    @Operation(
            summary = "Login",
            description = "Autentica usuário e retorna token JWT. Usuários: admin/admin ou user/user"
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

        // Retorna o token
        return ResponseEntity.ok(new TokenResponse(token, "Bearer", 3600000));
    }
}