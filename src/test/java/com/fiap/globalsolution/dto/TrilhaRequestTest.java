package com.fiap.globalsolution.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Validação do TrilhaRequest")
public class TrilhaRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve aceitar TrilhaRequest válido")
    public void deveAceitarTrilhaRequestValido() {
        // Arrange
        TrilhaRequest request = new TrilhaRequest(
                "Fundamentos de IA",
                "Introdução à Inteligência Artificial",
                "INICIANTE",
                40,
                "Inteligência Artificial"
        );

        // Act
        Set<ConstraintViolation<TrilhaRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Deve rejeitar nome em branco")
    public void deveRejeitarNomeEmBranco() {
        // Arrange
        TrilhaRequest request = new TrilhaRequest(
                "", // nome em branco
                "Descrição",
                "INICIANTE",
                40,
                "IA"
        );

        // Act
        Set<ConstraintViolation<TrilhaRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
    }

    @Test
    @DisplayName("Deve rejeitar nome com mais de 150 caracteres")
    public void deveRejeitarNomeComMaisDe150Caracteres() {
        // Arrange
        String nomeLongo = "A".repeat(151);
        TrilhaRequest request = new TrilhaRequest(
                nomeLongo,
                "Descrição",
                "INICIANTE",
                40,
                "IA"
        );

        // Act
        Set<ConstraintViolation<TrilhaRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
    }

    @Test
    @DisplayName("Deve rejeitar nível inválido")
    public void deveRejeitarNivelInvalido() {
        // Arrange
        TrilhaRequest request = new TrilhaRequest(
                "Trilha Teste",
                "Descrição",
                "EXPERT", // nível inválido
                40,
                "IA"
        );

        // Act
        Set<ConstraintViolation<TrilhaRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nivel")));
    }

    @Test
    @DisplayName("Deve aceitar níveis válidos")
    public void deveAceitarNiveisValidos() {
        // Arrange & Act & Assert
        assertAll("níveis válidos",
                () -> {
                    TrilhaRequest req1 = new TrilhaRequest("Nome", "Desc", "INICIANTE", 10, "IA");
                    assertTrue(validator.validate(req1).isEmpty());
                },
                () -> {
                    TrilhaRequest req2 = new TrilhaRequest("Nome", "Desc", "INTERMEDIARIO", 20, "Dados");
                    assertTrue(validator.validate(req2).isEmpty());
                },
                () -> {
                    TrilhaRequest req3 = new TrilhaRequest("Nome", "Desc", "AVANCADO", 30, "Cloud");
                    assertTrue(validator.validate(req3).isEmpty());
                }
        );
    }

    @Test
    @DisplayName("Deve rejeitar cargaHoraria null")
    public void deveRejeitarCargaHorariaNull() {
        // Arrange
        TrilhaRequest request = new TrilhaRequest(
                "Trilha Teste",
                "Descrição",
                "INICIANTE",
                null, // carga horária null
                "IA"
        );

        // Act
        Set<ConstraintViolation<TrilhaRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cargaHoraria")));
    }

    @Test
    @DisplayName("Deve rejeitar cargaHoraria menor que 1")
    public void deveRejeitarCargaHorariaMenorQue1() {
        // Arrange
        TrilhaRequest request = new TrilhaRequest(
                "Trilha Teste",
                "Descrição",
                "INICIANTE",
                0, // carga horária inválida
                "IA"
        );

        // Act
        Set<ConstraintViolation<TrilhaRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cargaHoraria")));
    }

    @Test
    @DisplayName("Deve rejeitar cargaHoraria maior que 1000")
    public void deveRejeitarCargaHorariaMaiorQue1000() {
        // Arrange
        TrilhaRequest request = new TrilhaRequest(
                "Trilha Teste",
                "Descrição",
                "INICIANTE",
                1001, // carga horária inválida
                "IA"
        );

        // Act
        Set<ConstraintViolation<TrilhaRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cargaHoraria")));
    }

    @Test
    @DisplayName("Deve aceitar cargaHoraria nos limites válidos")
    public void deveAceitarCargaHorariaNosLimitesValidos() {
        // Arrange & Act & Assert
        assertAll("cargas horárias válidas",
                () -> {
                    TrilhaRequest req1 = new TrilhaRequest("Nome", "Desc", "INICIANTE", 1, "IA");
                    assertTrue(validator.validate(req1).isEmpty());
                },
                () -> {
                    TrilhaRequest req2 = new TrilhaRequest("Nome", "Desc", "INTERMEDIARIO", 500, "Dados");
                    assertTrue(validator.validate(req2).isEmpty());
                },
                () -> {
                    TrilhaRequest req3 = new TrilhaRequest("Nome", "Desc", "AVANCADO", 1000, "Cloud");
                    assertTrue(validator.validate(req3).isEmpty());
                }
        );
    }

    @Test
    @DisplayName("Deve aceitar campos opcionais null")
    public void deveAceitarCamposOpcionaisNull() {
        // Arrange
        TrilhaRequest request = new TrilhaRequest(
                "Trilha Básica",
                null, // descrição pode ser null
                "INICIANTE",
                20,
                null // focoPrincipal pode ser null
        );

        // Act
        Set<ConstraintViolation<TrilhaRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }
}