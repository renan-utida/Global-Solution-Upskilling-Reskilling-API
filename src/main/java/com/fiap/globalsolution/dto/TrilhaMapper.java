package com.fiap.globalsolution.dto;

import com.fiap.globalsolution.model.Trilha;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper para conversão entre Trilha e seus DTOs
 */
public class TrilhaMapper {

    /**
     * Converte TrilhaRequest para entidade Trilha (sem competências)
     */
    public static Trilha toEntity(TrilhaRequest request) {
        Trilha trilha = new Trilha();
        trilha.setNome(request.nome());
        trilha.setDescricao(request.descricao());
        trilha.setNivel(request.nivel());
        trilha.setCargaHoraria(request.cargaHoraria());
        trilha.setFocoPrincipal(request.focoPrincipal());
        return trilha;
    }

    /**
     * Converte entidade Trilha para TrilhaResponse (com competências)
     */
    public static TrilhaResponse toResponse(Trilha trilha) {
        Set<CompetenciaResponse> competenciasResponse = trilha.getCompetencias().stream()
                .map(CompetenciaMapper::toResponse)
                .collect(Collectors.toSet());

        return new TrilhaResponse(
                trilha.getIdTrilha(),
                trilha.getNome(),
                trilha.getDescricao(),
                trilha.getNivel(),
                trilha.getCargaHoraria(),
                trilha.getFocoPrincipal(),
                competenciasResponse
        );
    }

    /**
     * Converte Trilha para TrilhaResponse sem as competências (evita referência circular)
     */
    public static TrilhaResponse toResponseWithoutCompetencias(Trilha trilha) {
        return new TrilhaResponse(
                trilha.getIdTrilha(),
                trilha.getNome(),
                trilha.getDescricao(),
                trilha.getNivel(),
                trilha.getCargaHoraria(),
                trilha.getFocoPrincipal(),
                Set.of() // Set vazio
        );
    }

    /**
     * Atualiza uma entidade Trilha existente com dados do TrilhaRequest
     */
    public static void updateEntityFromRequest(Trilha trilha, TrilhaRequest request) {
        trilha.setNome(request.nome());
        trilha.setDescricao(request.descricao());
        trilha.setNivel(request.nivel());
        trilha.setCargaHoraria(request.cargaHoraria());
        trilha.setFocoPrincipal(request.focoPrincipal());
    }
}