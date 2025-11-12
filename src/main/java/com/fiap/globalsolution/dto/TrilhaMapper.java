package com.fiap.globalsolution.dto;

import com.fiap.globalsolution.model.Trilha;

/**
 * Mapper para conversão entre Trilha e DTOs
 */
public class TrilhaMapper {

    /**
     * Converte TrilhaRequest para entidade Trilha
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
     * Converte entidade Trilha para TrilhaResponse
     */
    public static TrilhaResponse toResponse(Trilha trilha) {
        return new TrilhaResponse(
                trilha.getIdTrilha(),
                trilha.getNome(),
                trilha.getDescricao(),
                trilha.getNivel(),
                trilha.getCargaHoraria(),
                trilha.getFocoPrincipal()
        );
    }
}