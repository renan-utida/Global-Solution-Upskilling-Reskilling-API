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
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários da plataforma de Upskilling/Reskilling")
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
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        List<UsuarioResponse> usuarios = service.findAll();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * GET /api/usuarios/{id} - Busca usuário por ID
     */
    @Operation(summary = "Busca usuário por ID", description = "Retorna um usuário específico pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/usuarios/email/{email} - Busca usuário por email
     */
    @Operation(summary = "Busca usuário por email", description = "Retorna um usuário específico pelo seu email")
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponse> buscarPorEmail(@PathVariable String email) {
        return service.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/usuarios/area/{areaAtuacao} - Busca usuários por área de atuação
     */
    @Operation(summary = "Busca usuários por área de atuação", description = "Retorna usuários filtrados por área de atuação")
    @GetMapping("/area/{areaAtuacao}")
    public ResponseEntity<UsuarioResponse> buscarPorAreaAtuacao(@PathVariable String areaAtuacao) {
        return service.findByAreaAtuacao(areaAtuacao)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/usuarios - Cria novo usuário
     * O GlobalExceptionHandler trata DuplicateEntityException automaticamente
     */
    @Operation(summary = "Cria um novo usuário", description = "Cadastra um novo usuário na plataforma")
    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse created = service.create(request);
        URI location = URI.create("/api/usuarios/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    /**
     * PUT /api/usuarios/{id} - Atualiza usuário existente
     * O GlobalExceptionHandler trata DuplicateEntityException automaticamente
     */
    @Operation(summary = "Atualiza um usuário", description = "Atualiza os dados de um usuário existente")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request) {
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/usuarios/{id} - Deleta usuário por ID
     */
    @Operation(summary = "Deleta um usuário", description = "Remove um usuário do sistema pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}