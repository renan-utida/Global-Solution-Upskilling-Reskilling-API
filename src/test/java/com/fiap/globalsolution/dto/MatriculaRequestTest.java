package com.fiap.globalsolution.dto;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes - MatriculaRequest")
public class MatriculaRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve validar matrícula com todos os campos corretos")
    public void deveValidarMatriculaValida() {
        MatriculaRequest request = new MatriculaRequest(
                1L, 1L, LocalDate.now(), "EM_ANDAMENTO"
        );

        Set<ConstraintViolation<MatriculaRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Deve rejeitar status diferente de EM_ANDAMENTO, CONCLUIDA ou CANCELADA")
    public void deveRejeitarStatusInvalido() {
        MatriculaRequest request = new MatriculaRequest(
                1L, 1L, LocalDate.now(), "STATUS_INVALIDO"
        );

        Set<ConstraintViolation<MatriculaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Deve rejeitar data de inscrição futura (@PastOrPresent)")
    public void deveRejeitarDataFutura() {
        MatriculaRequest request = new MatriculaRequest(
                1L, 1L, LocalDate.now().plusDays(1), "EM_ANDAMENTO"
        );

        Set<ConstraintViolation<MatriculaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
}