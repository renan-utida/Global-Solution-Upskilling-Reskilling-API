package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.TrilhaRequest;
import com.fiap.globalsolution.dto.TrilhaResponse;
import com.fiap.globalsolution.service.TrilhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controller REST para gerenciamento de Trilhas
 * Endpoints: GET, POST, PUT, DELETE + gerenciamento de competências
 */
@RestController
@RequestMapping("/api/trilhas")
@Tag(name = "Trilhas", description = "Gerenciamento de trilhas de aprendizagem")
public class TrilhaController {

    private final TrilhaService service;

    public TrilhaController(TrilhaService service) {
        this.service = service;
    }

    /**
     * GET /api/trilhas - Lista todas as trilhas
     */
    @Operation(summary = "Lista todas as trilhas", description = "Retorna uma lista com todas as trilhas cadastradas")
    @GetMapping
    public ResponseEntity<List<TrilhaResponse>> list() {
        List<TrilhaResponse> trilhas = service.findAll();
        return ResponseEntity.ok(trilhas);
    }

    /**
     * GET /api/trilhas/{id} - Busca trilha por ID
     */
    @Operation(summary = "Busca trilha por ID", description = "Retorna uma trilha específica pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<TrilhaResponse> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/trilhas/nivel/{nivel} - Busca trilhas por nível
     */
    @Operation(summary = "Busca trilhas por nível", description = "Retorna trilhas filtradas por nível (INICIANTE, INTERMEDIARIO, AVANCADO)")
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<TrilhaResponse>> findByNivel(@PathVariable String nivel) {
        List<TrilhaResponse> trilhas = service.findByNivel(nivel);
        return ResponseEntity.ok(trilhas);
    }

    /**
     * GET /api/trilhas/foco/{foco} - Busca trilhas por foco principal
     */
    @Operation(summary = "Busca trilhas por foco", description = "Retorna trilhas filtradas por foco principal")
    @GetMapping("/foco/{foco}")
    public ResponseEntity<List<TrilhaResponse>> findByFoco(@PathVariable String foco) {
        List<TrilhaResponse> trilhas = service.findByFocoPrincipal(foco);
        return ResponseEntity.ok(trilhas);
    }

    /**
     * GET /api/trilhas/nome/{nome} - Busca trilhas por nome
     */
    @Operation(summary = "Busca trilhas por nome", description = "Retorna trilhas que contenham o nome informado")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<TrilhaResponse>> findByNome(@PathVariable String nome) {
        List<TrilhaResponse> trilhas = service.findByNome(nome);
        return ResponseEntity.ok(trilhas);
    }

    /**
     * POST /api/trilhas - Cria nova trilha
     */
    @Operation(summary = "Cria nova trilha", description = "Cadastra uma nova trilha de aprendizagem")
    @PostMapping
    public ResponseEntity<TrilhaResponse> create(@Valid @RequestBody TrilhaRequest request) {
        TrilhaResponse created = service.create(request);
        URI location = URI.create("/api/trilhas/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    /**
     * PUT /api/trilhas/{id} - Atualiza trilha existente
     */
    @Operation(summary = "Atualiza trilha", description = "Atualiza os dados de uma trilha existente")
    @PutMapping("/{id}")
    public ResponseEntity<TrilhaResponse> update(@PathVariable Long id, @Valid @RequestBody TrilhaRequest request) {
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/trilhas/{trilhaId}/competencias/{competenciaId} - Adiciona competência à trilha
     */
    @Operation(summary = "Adiciona competência", description = "Associa uma competência a uma trilha")
    @PostMapping("/{trilhaId}/competencias/{competenciaId}")
    public ResponseEntity<?> addCompetencia(@PathVariable Long trilhaId, @PathVariable Long competenciaId) {
        return service.addCompetencia(trilhaId, competenciaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/trilhas/{trilhaId}/competencias/{competenciaId} - Remove competência da trilha
     */
    @Operation(summary = "Remove competência", description = "Remove a associação de uma competência com uma trilha")
    @DeleteMapping("/{trilhaId}/competencias/{competenciaId}")
    public ResponseEntity<?> removeCompetencia(@PathVariable Long trilhaId, @PathVariable Long competenciaId) {
        return service.removeCompetencia(trilhaId, competenciaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/trilhas/{id} - Deleta trilha
     */
    @Operation(summary = "Deleta trilha", description = "Remove uma trilha do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}