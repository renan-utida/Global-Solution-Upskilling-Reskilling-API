package com.fiap.globalsolution.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da Entidade Trilha")
public class TrilhaTest {

    @Test
    @DisplayName("Deve criar Trilha com construtor completo")
    public void deveCriarTrilhaComConstrutorCompleto() {
        // Arrange & Act
        Trilha trilha = new Trilha(
                1L,
                "Fundamentos de IA",
                "Introdução à Inteligência Artificial",
                "INICIANTE",
                40,
                "Inteligência Artificial"
        );

        // Assert
        assertAll("trilha",
                () -> assertEquals(1L, trilha.getIdTrilha()),
                () -> assertEquals("Fundamentos de IA", trilha.getNome()),
                () -> assertEquals("Introdução à Inteligência Artificial", trilha.getDescricao()),
                () -> assertEquals("INICIANTE", trilha.getNivel()),
                () -> assertEquals(40, trilha.getCargaHoraria()),
                () -> assertEquals("Inteligência Artificial", trilha.getFocoPrincipal())
        );
    }

    @Test
    @DisplayName("Deve criar Trilha com construtor sem ID")
    public void deveCriarTrilhaComConstrutorSemId() {
        // Arrange & Act
        Trilha trilha = new Trilha(
                "Machine Learning Avançado",
                "Técnicas avançadas de ML",
                "AVANCADO",
                80,
                "IA"
        );

        // Assert
        assertAll("trilha",
                () -> assertNull(trilha.getIdTrilha()),
                () -> assertEquals("Machine Learning Avançado", trilha.getNome()),
                () -> assertEquals("Técnicas avançadas de ML", trilha.getDescricao()),
                () -> assertEquals("AVANCADO", trilha.getNivel()),
                () -> assertEquals(80, trilha.getCargaHoraria())
        );
    }

    @Test
    @DisplayName("Deve criar Trilha vazia com construtor padrão")
    public void deveCriarTrilhaVaziaComConstrutorPadrao() {
        // Arrange & Act
        Trilha trilha = new Trilha();

        // Assert
        assertAll("trilha",
                () -> assertNull(trilha.getIdTrilha()),
                () -> assertNull(trilha.getNome()),
                () -> assertNull(trilha.getDescricao()),
                () -> assertNull(trilha.getNivel()),
                () -> assertNull(trilha.getCargaHoraria()),
                () -> assertNull(trilha.getFocoPrincipal())
        );
    }

    @Test
    @DisplayName("Deve permitir usar setters")
    public void devePermitirUsarSetters() {
        // Arrange
        Trilha trilha = new Trilha();

        // Act
        trilha.setIdTrilha(5L);
        trilha.setNome("DevOps");
        trilha.setDescricao("Práticas DevOps");
        trilha.setNivel("INTERMEDIARIO");
        trilha.setCargaHoraria(60);
        trilha.setFocoPrincipal("Cloud");

        // Assert
        assertAll("trilha",
                () -> assertEquals(5L, trilha.getIdTrilha()),
                () -> assertEquals("DevOps", trilha.getNome()),
                () -> assertEquals("Práticas DevOps", trilha.getDescricao()),
                () -> assertEquals("INTERMEDIARIO", trilha.getNivel()),
                () -> assertEquals(60, trilha.getCargaHoraria()),
                () -> assertEquals("Cloud", trilha.getFocoPrincipal())
        );
    }

    @Test
    @DisplayName("Deve gerar toString corretamente")
    public void deveGerarToStringCorretamente() {
        // Arrange
        Trilha trilha = new Trilha(
                3L,
                "Análise de Dados",
                "Análise com Python",
                "INICIANTE",
                50,
                "Dados"
        );

        // Act
        String resultado = trilha.toString();

        // Assert
        assertAll("toString",
                () -> assertTrue(resultado.contains("idTrilha=3")),
                () -> assertTrue(resultado.contains("nome='Análise de Dados'")),
                () -> assertTrue(resultado.contains("nivel='INICIANTE'")),
                () -> assertTrue(resultado.contains("cargaHoraria=50"))
        );
    }

    @Test
    @DisplayName("Deve validar níveis válidos")
    public void deveValidarNiveisValidos() {
        // Arrange & Act & Assert
        assertDoesNotThrow(() -> {
            new Trilha(1L, "Trilha 1", "Desc", "INICIANTE", 10, "IA");
            new Trilha(2L, "Trilha 2", "Desc", "INTERMEDIARIO", 20, "Dados");
            new Trilha(3L, "Trilha 3", "Desc", "AVANCADO", 30, "Cloud");
        });
    }

    @Test
    @DisplayName("Deve aceitar valores null em campos opcionais")
    public void deveAceitarValoresNullEmCamposOpcionais() {
        // Arrange & Act
        Trilha trilha = new Trilha(
                1L,
                "Trilha Básica",
                null, // descrição pode ser null
                "INICIANTE",
                20,
                null // focoPrincipal pode ser null
        );

        // Assert
        assertAll("trilha",
                () -> assertNotNull(trilha.getNome()),
                () -> assertNull(trilha.getDescricao()),
                () -> assertNull(trilha.getFocoPrincipal())
        );
    }
}