package com.fiap.globalsolution.dto;

import java.time.LocalDate;

/**
 * DTO para resposta de Usuario
 */
public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String areaAtuacao,
        String nivelCarreira,
        LocalDate dataCadastro
) { }
