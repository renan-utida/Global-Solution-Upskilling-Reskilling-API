package com.fiap.globalsolution.dto;

import jakarta.validation.constraints.*;
import java.util.Set;

/**
 * DTO para criação/atualização de Trilha
 */
public record TrilhaRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
        String nome,

        @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
        String descricao,

        @NotBlank(message = "Nível é obrigatório")
        @Pattern(regexp = "INICIANTE|INTERMEDIARIO|AVANCADO",
                message = "Nível deve ser: INICIANTE, INTERMEDIARIO ou AVANCADO")
        String nivel,

        @NotNull(message = "Carga horária é obrigatória")
        @Min(value = 1, message = "Carga horária deve ser no mínimo 1 hora")
        @Max(value = 1000, message = "Carga horária deve ser no máximo 1000 horas")
        Integer cargaHoraria,

        @Size(max = 100, message = "Foco principal deve ter no máximo 100 caracteres")
        String focoPrincipal,

        Set<Long> competenciasIds
) { }