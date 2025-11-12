package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.MatriculaRequest;
import com.fiap.globalsolution.dto.MatriculaResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.exception.TrilhaNaoEncontradaException;
import com.fiap.globalsolution.exception.UsuarioNaoEncontradoException;
import com.fiap.globalsolution.service.MatriculaService;
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
 * Controller REST para gerenciamento de Matriculas
 * Endpoints: GET, POST, PUT, DELETE
 */
@RestController
@RequestMapping("/api/matriculas")
@Tag(name = "Matrículas", description = "Endpoints para gerenciamento de matrículas de usuários em trilhas")
public class MatriculaController {

    private final MatriculaService service;

    public MatriculaController(MatriculaService service) {
        this.service = service;
    }

    /**
     * GET /api/matriculas - Lista todas as matrículas
     */
    @Operation(summary = "Lista todas as matrículas", description = "Retorna uma lista com todas as matrículas cadastradas")
    @GetMapping
    public ResponseEntity<List<MatriculaResponse>> listarTodas() {
        List<MatriculaResponse> matriculas = service.findAll();
        return ResponseEntity.ok(matriculas);
    }

    /**
     * GET /api/matriculas/{id} - Busca matrícula por ID
     */
    @Operation(summary = "Busca matrícula por ID", description = "Retorna uma matrícula específica pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<MatriculaResponse> buscarPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/matriculas/usuario/{usuarioId} - Busca matrículas de um usuário
     */
    @Operation(summary = "Busca matrículas de um usuário", description = "Retorna todas as matrículas de um usuário específico")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<MatriculaResponse> matriculas = service.findByUsuarioId(usuarioId);
            return matriculas.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(matriculas);
        } catch (UsuarioNaoEncontradoException e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("status", HttpStatus.NOT_FOUND.value());
            error.put("error", "Not Found");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * GET /api/matriculas/trilha/{trilhaId} - Busca matrículas de uma trilha
     */
    @Operation(summary = "Busca matrículas de uma trilha", description = "Retorna todas as matrículas de uma trilha específica")
    @GetMapping("/trilha/{trilhaId}")
    public ResponseEntity<?> buscarPorTrilha(@PathVariable Long trilhaId) {
        try {
            List<MatriculaResponse> matriculas = service.findByTrilhaId(trilhaId);
            return matriculas.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(matriculas);
        } catch (TrilhaNaoEncontradaException e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("status", HttpStatus.NOT_FOUND.value());
            error.put("error", "Not Found");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * GET /api/matriculas/status/{status} - Busca matrículas por status
     */
    @Operation(summary = "Busca matrículas por status", description = "Retorna matrículas filtradas por status (EM_ANDAMENTO, CONCLUIDA, CANCELADA)")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MatriculaResponse>> buscarPorStatus(@PathVariable String status) {
        List<MatriculaResponse> matriculas = service.findByStatus(status);
        return matriculas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(matriculas);
    }

    /**
     * POST /api/matriculas - Cria nova matrícula
     */
    @Operation(summary = "Cria uma nova matrícula", description = "Cadastra uma nova matrícula de usuário em trilha")
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody MatriculaRequest request) {
        try {
            MatriculaResponse created = service.create(request);
            URI location = URI.create("/api/matriculas/" + created.id());
            return ResponseEntity.created(location).body(created);
        } catch (UsuarioNaoEncontradoException | TrilhaNaoEncontradaException e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("status", HttpStatus.NOT_FOUND.value());
            error.put("error", "Not Found");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (DuplicateEntityException e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("status", HttpStatus.CONFLICT.value());
            error.put("error", "Duplicate Entity");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

    /**
     * PUT /api/matriculas/{id} - Atualiza matrícula existente
     */
    @Operation(summary = "Atualiza uma matrícula", description = "Atualiza os dados de uma matrícula existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MatriculaRequest request) {
        try {
            return service.update(id, request)
                    .map(matricula -> ResponseEntity.ok((Object) matricula))
                    .orElse(ResponseEntity.notFound().build());
        } catch (UsuarioNaoEncontradoException | TrilhaNaoEncontradaException e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("status", HttpStatus.NOT_FOUND.value());
            error.put("error", "Not Found");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (DuplicateEntityException e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("status", HttpStatus.CONFLICT.value());
            error.put("error", "Duplicate Entity");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

    /**
     * DELETE /api/matriculas/{id} - Deleta matrícula por ID
     */
    @Operation(summary = "Deleta uma matrícula", description = "Remove uma matrícula do sistema pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}