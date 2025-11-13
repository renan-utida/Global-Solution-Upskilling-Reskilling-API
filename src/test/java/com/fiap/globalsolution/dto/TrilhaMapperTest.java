package com.fiap.globalsolution.dto;

import com.fiap.globalsolution.model.Trilha;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do TrilhaMapper")
public class TrilhaMapperTest {

    @Test
    @DisplayName("Deve converter TrilhaRequest para Trilha")
    public void deveConverterTrilhaRequestParaTrilha() {
        // Arrange
        TrilhaRequest request = new TrilhaRequest(
                "Fundamentos de IA",
                "Introdução à Inteligência Artificial",
                "INICIANTE",
                40,
                "Inteligência Artificial"
        );

        // Act
        Trilha trilha = TrilhaMapper.toEntity(request);

        // Assert
        assertAll("conversão request -> entity",
                () -> assertNull(trilha.getIdTrilha()), // ID não vem do request
                () -> assertEquals("Fundamentos de IA", trilha.getNome()),
                () -> assertEquals("Introdução à Inteligência Artificial", trilha.getDescricao()),
                () -> assertEquals("INICIANTE", trilha.getNivel()),
                () -> assertEquals(40, trilha.getCargaHoraria()),
                () -> assertEquals("Inteligência Artificial", trilha.getFocoPrincipal())
        );
    }

    @Test
    @DisplayName("Deve converter Trilha para TrilhaResponse")
    public void deveConverterTrilhaParaTrilhaResponse() {
        // Arrange
        Trilha trilha = new Trilha(
                3L,
                "Machine Learning",
                "ML Avançado",
                "AVANCADO",
                80,
                "IA"
        );

        // Act
        TrilhaResponse response = TrilhaMapper.toResponse(trilha);

        // Assert
        assertAll("conversão entity -> response",
                () -> assertEquals(3L, response.id()),
                () -> assertEquals("Machine Learning", response.nome()),
                () -> assertEquals("ML Avançado", response.descricao()),
                () -> assertEquals("AVANCADO", response.nivel()),
                () -> assertEquals(80, response.cargaHoraria()),
                () -> assertEquals("IA", response.focoPrincipal())
        );
    }

    @Test
    @DisplayName("Deve lidar com campos null em TrilhaRequest")
    public void deveLidarComCamposNullEmTrilhaRequest() {
        // Arrange
        TrilhaRequest request = new TrilhaRequest(
                "DevOps Básico",
                null, // descricao null
                "INTERMEDIARIO",
                60,
                null // focoPrincipal null
        );

        // Act
        Trilha trilha = TrilhaMapper.toEntity(request);

        // Assert
        assertAll("conversão com nulls",
                () -> assertEquals("DevOps Básico", trilha.getNome()),
                () -> assertNull(trilha.getDescricao()),
                () -> assertEquals("INTERMEDIARIO", trilha.getNivel()),
                () -> assertNull(trilha.getFocoPrincipal())
        );
    }

    @Test
    @DisplayName("Deve preservar carga horária corretamente")
    public void devePreservarCargaHorariaCorretamente() {
        // Arrange
        TrilhaRequest request = new TrilhaRequest(
                "Trilha Teste",
                "Desc",
                "INICIANTE",
                999, // carga horária específica
                "Teste"
        );

        // Act
        Trilha trilha = TrilhaMapper.toEntity(request);

        // Assert
        assertEquals(999, trilha.getCargaHoraria());
    }
}