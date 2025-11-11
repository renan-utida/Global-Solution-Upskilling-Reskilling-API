package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.UsuarioRequest;
import com.fiap.globalsolution.dto.UsuarioResponse;
import com.fiap.globalsolution.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controller REST para gerenciamento de Usuarios
 * Endpoints: GET, POST, PUT, DELETE
 */
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Gerenciamento de usuários da plataforma de Upskilling/Reskilling")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    /**
     * GET /api/usuarios - Lista todos os usuários
     */
    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> list() {
        List<UsuarioResponse> usuarios = service.findAll();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * GET /api/usuarios/{id} - Busca usuário por ID
     */
    @Operation(summary = "Busca usuário por ID", description = "Retorna um usuário específico pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/usuarios/email/{email} - Busca usuário por email
     */
    @Operation(summary = "Busca usuário por email", description = "Retorna um usuário pelo email")
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponse> findByEmail(@PathVariable String email) {
        return service.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/usuarios/area/{area} - Busca usuários por área de atuação
     */
    @Operation(summary = "Busca usuários por área", description = "Retorna usuários filtrados por área de atuação")
    @GetMapping("/area/{area}")
    public ResponseEntity<List<UsuarioResponse>> findByAreaAtuacao(@PathVariable String area) {
        List<UsuarioResponse> usuarios = service.findByAreaAtuacao(area);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * POST /api/usuarios - Cria novo usuário
     */
    @Operation(summary = "Cria novo usuário", description = "Cadastra um novo usuário na plataforma")
    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse created = service.create(request);
        URI location = URI.create("/api/usuarios/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    /**
     * PUT /api/usuarios/{id} - Atualiza usuário existente
     */
    @Operation(summary = "Atualiza usuário", description = "Atualiza os dados de um usuário existente")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/usuarios/{id} - Deleta usuário
     */
    @Operation(summary = "Deleta usuário", description = "Remove um usuário do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}