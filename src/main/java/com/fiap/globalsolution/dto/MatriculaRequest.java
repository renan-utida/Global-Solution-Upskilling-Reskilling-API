package com.fiap.globalsolution.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO para criação/atualização de Matricula
 */
public record MatriculaRequest(
        @NotNull(message = "ID do usuário é obrigatório")
        Long usuarioId,

        @NotNull(message = "ID da trilha é obrigatório")
        Long trilhaId,

        @NotNull(message = "Data de inscrição é obrigatória")
        LocalDate dataInscricao,

        @NotBlank(message = "Status é obrigatório")
        @Pattern(regexp = "EM_ANDAMENTO|CONCLUIDA|CANCELADA",
                message = "Status deve ser: EM_ANDAMENTO, CONCLUIDA ou CANCELADA")
        String status
) { }