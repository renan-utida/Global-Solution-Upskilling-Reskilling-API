package com.fiap.globalsolution.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO para criação/atualização de Usuario
 */
public record UsuarioRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
        String email,

        @Size(max = 100, message = "Área de atuação deve ter no máximo 100 caracteres")
        String areaAtuacao,

        @Size(max = 50, message = "Nível de carreira deve ter no máximo 50 caracteres")
        String nivelCarreira,

        @NotNull(message = "Data de cadastro é obrigatória")
        LocalDate dataCadastro
) { }