package com.fiap.globalsolution.dto;

import java.util.Set;

/**
 * DTO para resposta de Trilha
 */
public record TrilhaResponse(
        Long id,
        String nome,
        String descricao,
        String nivel,
        Integer cargaHoraria,
        String focoPrincipal,
        Set<CompetenciaResponse> competencias
) { }