package com.fiap.globalsolution.dto;

/**
 * DTO para respostas de Trilha
 */
public record TrilhaResponse(
        Long id,
        String nome,
        String descricao,
        String nivel,
        Integer cargaHoraria,
        String focoPrincipal
) {}