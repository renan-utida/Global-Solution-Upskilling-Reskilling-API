package com.fiap.globalsolution.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da Entidade Usuario")
public class UsuarioTest {

    @Test
    @DisplayName("Deve criar Usuario com construtor completo")
    public void deveCriarUsuarioComConstrutorCompleto() {
        // Arrange & Act
        Usuario usuario = new Usuario(
                1L,
                "João Silva",
                "joao@email.com",
                "Desenvolvimento",
                "PLENO",
                LocalDate.of(2024, 1, 15)
        );

        // Assert
        assertAll("usuario",
                () -> assertEquals(1L, usuario.getIdUsuario()),
                () -> assertEquals("João Silva", usuario.getNome()),
                () -> assertEquals("joao@email.com", usuario.getEmail()),
                () -> assertEquals("Desenvolvimento", usuario.getAreaAtuacao()),
                () -> assertEquals("PLENO", usuario.getNivelCarreira()),
                () -> assertEquals(LocalDate.of(2024, 1, 15), usuario.getDataCadastro())
        );
    }

    @Test
    @DisplayName("Deve criar Usuario com construtor sem ID")
    public void deveCriarUsuarioComConstrutorSemId() {
        // Arrange & Act
        Usuario usuario = new Usuario(
                "Maria Santos",
                "maria@email.com",
                "Análise de Dados",
                "SENIOR",
                LocalDate.of(2024, 2, 20)
        );

        // Assert
        assertAll("usuario",
                () -> assertNull(usuario.getIdUsuario()),
                () -> assertEquals("Maria Santos", usuario.getNome()),
                () -> assertEquals("maria@email.com", usuario.getEmail()),
                () -> assertEquals("Análise de Dados", usuario.getAreaAtuacao()),
                () -> assertEquals("SENIOR", usuario.getNivelCarreira())
        );
    }

    @Test
    @DisplayName("Deve criar Usuario vazio com construtor padrão")
    public void deveCriarUsuarioVazioComConstrutorPadrao() {
        // Arrange & Act
        Usuario usuario = new Usuario();

        // Assert
        assertAll("usuario",
                () -> assertNull(usuario.getIdUsuario()),
                () -> assertNull(usuario.getNome()),
                () -> assertNull(usuario.getEmail()),
                () -> assertNull(usuario.getAreaAtuacao()),
                () -> assertNull(usuario.getNivelCarreira()),
                () -> assertNull(usuario.getDataCadastro())
        );
    }

    @Test
    @DisplayName("Deve permitir usar setters")
    public void devePermitirUsarSetters() {
        // Arrange
        Usuario usuario = new Usuario();

        // Act
        usuario.setIdUsuario(10L);
        usuario.setNome("Carlos Mendes");
        usuario.setEmail("carlos@email.com");
        usuario.setAreaAtuacao("Marketing");
        usuario.setNivelCarreira("JUNIOR");
        usuario.setDataCadastro(LocalDate.of(2024, 3, 10));

        // Assert
        assertAll("usuario",
                () -> assertEquals(10L, usuario.getIdUsuario()),
                () -> assertEquals("Carlos Mendes", usuario.getNome()),
                () -> assertEquals("carlos@email.com", usuario.getEmail()),
                () -> assertEquals("Marketing", usuario.getAreaAtuacao()),
                () -> assertEquals("JUNIOR", usuario.getNivelCarreira()),
                () -> assertEquals(LocalDate.of(2024, 3, 10), usuario.getDataCadastro())
        );
    }

    @Test
    @DisplayName("Deve gerar toString corretamente")
    public void deveGerarToStringCorretamente() {
        // Arrange
        Usuario usuario = new Usuario(
                5L,
                "Ana Costa",
                "ana@email.com",
                "RH",
                "PLENO",
                LocalDate.of(2024, 4, 5)
        );

        // Act
        String resultado = usuario.toString();

        // Assert
        assertAll("toString",
                () -> assertTrue(resultado.contains("idUsuario=5")),
                () -> assertTrue(resultado.contains("nome='Ana Costa'")),
                () -> assertTrue(resultado.contains("email='ana@email.com'")),
                () -> assertTrue(resultado.contains("areaAtuacao='RH'")),
                () -> assertTrue(resultado.contains("nivelCarreira='PLENO'"))
        );
    }

    @Test
    @DisplayName("Deve aceitar valores null em campos opcionais")
    public void deveAceitarValoresNullEmCamposOpcionais() {
        // Arrange & Act
        Usuario usuario = new Usuario(
                1L,
                "Pedro Silva",
                "pedro@email.com",
                null, // areaAtuacao pode ser null
                null, // nivelCarreira pode ser null
                LocalDate.now()
        );

        // Assert
        assertAll("usuario",
                () -> assertNotNull(usuario.getNome()),
                () -> assertNotNull(usuario.getEmail()),
                () -> assertNull(usuario.getAreaAtuacao()),
                () -> assertNull(usuario.getNivelCarreira())
        );
    }
}