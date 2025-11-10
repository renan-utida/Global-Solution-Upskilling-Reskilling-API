package com.fiap.globalsolution.dto;

import jakarta.validation.constraints.*;

/**
 * DTO para criação/atualização de Competencia
 */
public record CompetenciaRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        String nome,

        @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
        String categoria,

        @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
        String descricao
) { }