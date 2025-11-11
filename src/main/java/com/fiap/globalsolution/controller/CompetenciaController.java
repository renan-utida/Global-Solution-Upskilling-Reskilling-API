package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.CompetenciaRequest;
import com.fiap.globalsolution.dto.CompetenciaResponse;
import com.fiap.globalsolution.service.CompetenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controller REST para gerenciamento de Competencias
 * Endpoints: GET, POST, PUT, DELETE
 */
@RestController
@RequestMapping("/api/competencias")
@Tag(name = "Competências", description = "Gerenciamento de competências do futuro")
public class CompetenciaController {

    private final CompetenciaService service;

    public CompetenciaController(CompetenciaService service) {
        this.service = service;
    }

    /**
     * GET /api/competencias - Lista todas as competências
     */
    @Operation(summary = "Lista todas as competências", description = "Retorna uma lista com todas as competências cadastradas")
    @GetMapping
    public ResponseEntity<List<CompetenciaResponse>> list() {
        List<CompetenciaResponse> competencias = service.findAll();
        return ResponseEntity.ok(competencias);
    }

    /**
     * GET /api/competencias/{id} - Busca competência por ID
     */
    @Operation(summary = "Busca competência por ID", description = "Retorna uma competência específica pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<CompetenciaResponse> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/competencias/categoria/{categoria} - Busca competências por categoria
     */
    @Operation(summary = "Busca competências por categoria", description = "Retorna competências filtradas por categoria")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<CompetenciaResponse>> findByCategoria(@PathVariable String categoria) {
        List<CompetenciaResponse> competencias = service.findByCategoria(categoria);
        return ResponseEntity.ok(competencias);
    }

    /**
     * GET /api/competencias/nome/{nome} - Busca competências por nome
     */
    @Operation(summary = "Busca competências por nome", description = "Retorna competências que contenham o nome informado")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<CompetenciaResponse>> findByNome(@PathVariable String nome) {
        List<CompetenciaResponse> competencias = service.findByNome(nome);
        return ResponseEntity.ok(competencias);
    }

    /**
     * POST /api/competencias - Cria nova competência
     */
    @Operation(summary = "Cria nova competência", description = "Cadastra uma nova competência do futuro")
    @PostMapping
    public ResponseEntity<CompetenciaResponse> create(@Valid @RequestBody CompetenciaRequest request) {
        CompetenciaResponse created = service.create(request);
        URI location = URI.create("/api/competencias/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    /**
     * PUT /api/competencias/{id} - Atualiza competência existente
     */
    @Operation(summary = "Atualiza competência", description = "Atualiza os dados de uma competência existente")
    @PutMapping("/{id}")
    public ResponseEntity<CompetenciaResponse> update(@PathVariable Long id, @Valid @RequestBody CompetenciaRequest request) {
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/competencias/{id} - Deleta competência
     */
    @Operation(summary = "Deleta competência", description = "Remove uma competência do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}