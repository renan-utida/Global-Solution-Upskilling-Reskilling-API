package com.fiap.globalsolution.dto;

import java.time.LocalDate;

/**
 * DTO para resposta de Matricula
 */
public record MatriculaResponse(
        Long id,
        UsuarioResponse usuario,
        TrilhaResponse trilha,
        LocalDate dataInscricao,
        String status
) { }