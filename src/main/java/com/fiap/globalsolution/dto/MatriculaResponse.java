package com.fiap.globalsolution.dto;

import java.time.LocalDate;

/**
 * DTO para respostas de Matricula
 * Inclui dados resumidos do usuário e trilha
 */
public record MatriculaResponse(
        Long id,
        UsuarioResponse usuario,
        TrilhaResponse trilha,
        LocalDate dataInscricao,
        String status
) {}