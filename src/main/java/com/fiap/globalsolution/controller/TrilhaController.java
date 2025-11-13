package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.TrilhaRequest;
import com.fiap.globalsolution.dto.TrilhaResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.service.TrilhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para gerenciamento de Trilhas
 * Endpoints: GET, POST, PUT, DELETE + gerenciamento de competências
 */
@RestController
@RequestMapping("/api/trilhas")
@Tag(name = "Trilhas", description = "Endpoints para gerenciamento de trilhas de aprendizagem")
public class TrilhaController {

    private final TrilhaService service;

    public TrilhaController(TrilhaService service) {
        this.service = service;
    }

    /**
     * GET /api/trilhas - Lista todas as trilhas
     */
    @Operation(summary = "Lista todas as trilhas", description = "Retorna uma lista com todas as trilhas de aprendizagem cadastradas")
    @GetMapping
    public ResponseEntity<List<TrilhaResponse>> listarTodas() {
        List<TrilhaResponse> trilhas = service.findAll();
        return ResponseEntity.ok(trilhas);
    }

    /**
     * GET /api/trilhas/{id} - Busca trilha por ID
     */
    @Operation(summary = "Busca trilha por ID", description = "Retorna uma trilha específica pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<TrilhaResponse> buscarPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/trilhas/nivel/{nivel} - Busca trilhas por nível
     */
    @Operation(summary = "Busca trilhas por nível", description = "Retorna trilhas filtradas por nível (INICIANTE, INTERMEDIARIO, AVANCADO)")
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<TrilhaResponse>> buscarPorNivel(@PathVariable String nivel) {
        List<TrilhaResponse> trilhas = service.findByNivel(nivel);
        return trilhas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(trilhas);
    }

    /**
     * GET /api/trilhas/foco/{focoPrincipal} - Busca trilhas por foco principal
     */
    @Operation(summary = "Busca trilhas por foco principal", description = "Retorna trilhas filtradas por foco principal (ex: IA, Dados, Soft Skills)")
    @GetMapping("/foco/{focoPrincipal}")
    public ResponseEntity<List<TrilhaResponse>> buscarPorFocoPrincipal(@PathVariable String focoPrincipal) {
        List<TrilhaResponse> trilhas = service.findByFocoPrincipal(focoPrincipal);
        return trilhas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(trilhas);
    }

    /**
     * POST /api/trilhas - Cria nova trilha
     */
    @Operation(summary = "Cria uma nova trilha", description = "Cadastra uma nova trilha de aprendizagem")
    @PostMapping
    public ResponseEntity<TrilhaResponse> criar(@Valid @RequestBody TrilhaRequest request) {
        // O @RestControllerAdvice intercepta DuplicateEntityException automaticamente
        TrilhaResponse created = service.create(request);
        URI location = URI.create("/api/trilhas/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    /**
     * PUT /api/trilhas/{id} - Atualiza trilha existente
     */
    @Operation(summary = "Atualiza uma trilha", description = "Atualiza os dados de uma trilha existente")
    @PutMapping("/{id}")
    public ResponseEntity<TrilhaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TrilhaRequest request) {
        // O @RestControllerAdvice intercepta DuplicateEntityException automaticamente
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/trilhas/{id} - Deleta uma trilha por ID
     */
    @Operation(summary = "Deleta uma trilha", description = "Remove uma trilha do sistema pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}