package com.fiap.globalsolution.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Validação do UsuarioRequest")
public class UsuarioRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve aceitar UsuarioRequest válido")
    public void deveAceitarUsuarioRequestValido() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest(
                "João Silva",
                "joao@email.com",
                "Desenvolvimento",
                "PLENO",
                LocalDate.now()
        );

        // Act
        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty(), "Não deve haver violações");
    }

    @Test
    @DisplayName("Deve rejeitar nome em branco")
    public void deveRejeitarNomeEmBranco() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest(
                "", // nome em branco
                "joao@email.com",
                "Desenvolvimento",
                "PLENO",
                LocalDate.now()
        );

        // Act
        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty(), "Deve haver violação");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
    }

    @Test
    @DisplayName("Deve rejeitar nome null")
    public void deveRejeitarNomeNull() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest(
                null, // nome null
                "joao@email.com",
                "Desenvolvimento",
                "PLENO",
                LocalDate.now()
        );

        // Act
        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
    }

    @Test
    @DisplayName("Deve rejeitar nome com mais de 100 caracteres")
    public void deveRejeitarNomeComMaisDe100Caracteres() {
        // Arrange
        String nomeLongo = "A".repeat(101); // 101 caracteres
        UsuarioRequest request = new UsuarioRequest(
                nomeLongo,
                "joao@email.com",
                "Desenvolvimento",
                "PLENO",
                LocalDate.now()
        );

        // Act
        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
    }

    @Test
    @DisplayName("Deve rejeitar email em branco")
    public void deveRejeitarEmailEmBranco() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest(
                "João Silva",
                "", // email em branco
                "Desenvolvimento",
                "PLENO",
                LocalDate.now()
        );

        // Act
        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Deve rejeitar email inválido")
    public void deveRejeitarEmailInvalido() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest(
                "João Silva",
                "email-invalido", // email sem @
                "Desenvolvimento",
                "PLENO",
                LocalDate.now()
        );

        // Act
        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Deve rejeitar email com mais de 150 caracteres")
    public void deveRejeitarEmailComMaisDe150Caracteres() {
        // Arrange
        String emailLongo = "a".repeat(140) + "@email.com"; // > 150 caracteres
        UsuarioRequest request = new UsuarioRequest(
                "João Silva",
                emailLongo,
                "Desenvolvimento",
                "PLENO",
                LocalDate.now()
        );

        // Act
        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Deve rejeitar dataCadastro null")
    public void deveRejeitarDataCadastroNull() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest(
                "João Silva",
                "joao@email.com",
                "Desenvolvimento",
                "PLENO",
                null // data null
        );

        // Act
        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("dataCadastro")));
    }

    @Test
    @DisplayName("Deve aceitar campos opcionais null")
    public void deveAceitarCamposOpcionaisNull() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest(
                "João Silva",
                "joao@email.com",
                null, // areaAtuacao pode ser null
                null, // nivelCarreira pode ser null
                LocalDate.now()
        );

        // Act
        Set<ConstraintViolation<UsuarioRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Deve aceitar email válido com formato correto")
    public void deveAceitarEmailValidoComFormatoCorreto() {
        // Arrange & Act & Assert
        assertAll("emails válidos",
                () -> {
                    UsuarioRequest req1 = new UsuarioRequest("Nome", "teste@email.com", null, null, LocalDate.now());
                    assertTrue(validator.validate(req1).isEmpty());
                },
                () -> {
                    UsuarioRequest req2 = new UsuarioRequest("Nome", "user.name@company.com.br", null, null, LocalDate.now());
                    assertTrue(validator.validate(req2).isEmpty());
                },
                () -> {
                    UsuarioRequest req3 = new UsuarioRequest("Nome", "user+tag@domain.co", null, null, LocalDate.now());
                    assertTrue(validator.validate(req3).isEmpty());
                }
        );
    }
}