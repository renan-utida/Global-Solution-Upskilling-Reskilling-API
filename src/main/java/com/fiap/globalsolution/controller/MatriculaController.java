package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.MatriculaRequest;
import com.fiap.globalsolution.dto.MatriculaResponse;
import com.fiap.globalsolution.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controller REST para gerenciamento de Matriculas
 * Endpoints: GET, POST, PUT, DELETE
 */
@RestController
@RequestMapping("/api/matriculas")
@Tag(name = "Matrículas", description = "Gerenciamento de matrículas de usuários em trilhas")
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
    public ResponseEntity<List<MatriculaResponse>> list() {
        List<MatriculaResponse> matriculas = service.findAll();
        return ResponseEntity.ok(matriculas);
    }

    /**
     * GET /api/matriculas/{id} - Busca matrícula por ID
     */
    @Operation(summary = "Busca matrícula por ID", description = "Retorna uma matrícula específica pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<MatriculaResponse> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/matriculas/usuario/{usuarioId} - Busca matrículas de um usuário
     */
    @Operation(summary = "Busca matrículas por usuário", description = "Retorna todas as matrículas de um usuário")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MatriculaResponse>> findByUsuario(@PathVariable Long usuarioId) {
        List<MatriculaResponse> matriculas = service.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(matriculas);
    }

    /**
     * GET /api/matriculas/trilha/{trilhaId} - Busca matrículas de uma trilha
     */
    @Operation(summary = "Busca matrículas por trilha", description = "Retorna todas as matrículas de uma trilha")
    @GetMapping("/trilha/{trilhaId}")
    public ResponseEntity<List<MatriculaResponse>> findByTrilha(@PathVariable Long trilhaId) {
        List<MatriculaResponse> matriculas = service.findByTrilhaId(trilhaId);
        return ResponseEntity.ok(matriculas);
    }

    /**
     * GET /api/matriculas/status/{status} - Busca matrículas por status
     */
    @Operation(summary = "Busca matrículas por status", description = "Retorna matrículas filtradas por status (EM_ANDAMENTO, CONCLUIDA, CANCELADA)")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MatriculaResponse>> findByStatus(@PathVariable String status) {
        List<MatriculaResponse> matriculas = service.findByStatus(status);
        return ResponseEntity.ok(matriculas);
    }

    /**
     * POST /api/matriculas - Cria nova matrícula
     */
    @Operation(summary = "Cria nova matrícula", description = "Matricula um usuário em uma trilha de aprendizagem")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MatriculaRequest request) {
        try {
            MatriculaResponse created = service.create(request);
            URI location = URI.create("/api/matriculas/" + created.id());
            return ResponseEntity.created(location).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    /**
     * PUT /api/matriculas/{id} - Atualiza matrícula existente
     */
    @Operation(summary = "Atualiza matrícula", description = "Atualiza os dados de uma matrícula existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody MatriculaRequest request) {
        try {
            return service.update(id, request)
                    .map(updated -> ResponseEntity.ok((Object) updated))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    /**
     * PATCH /api/matriculas/{id}/status - Atualiza apenas o status da matrícula
     */
    @Operation(summary = "Atualiza status da matrícula", description = "Atualiza apenas o status de uma matrícula (EM_ANDAMENTO, CONCLUIDA, CANCELADA)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/matriculas/{id} - Deleta matrícula
     */
    @Operation(summary = "Deleta matrícula", description = "Remove uma matrícula do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    /**
     * Classe interna para respostas de erro
     */
    private record ErrorResponse(int status, String message) {}
}