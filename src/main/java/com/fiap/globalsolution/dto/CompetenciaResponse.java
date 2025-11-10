package com.fiap.globalsolution.dto;

/**
 * DTO para resposta de Competencia
 */
public record CompetenciaResponse(
        Long id,
        String nome,
        String categoria,
        String descricao
) { }