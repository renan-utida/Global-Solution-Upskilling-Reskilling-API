package com.fiap.globalsolution.dto;

import com.fiap.globalsolution.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do UsuarioMapper")
public class UsuarioMapperTest {

    @Test
    @DisplayName("Deve converter UsuarioRequest para Usuario")
    public void deveConverterUsuarioRequestParaUsuario() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest(
                "João Silva",
                "joao@email.com",
                "Desenvolvimento",
                "PLENO",
                LocalDate.of(2024, 1, 15)
        );

        // Act
        Usuario usuario = UsuarioMapper.toEntity(request);

        // Assert
        assertAll("conversão request -> entity",
                () -> assertNull(usuario.getIdUsuario()), // ID não vem do request
                () -> assertEquals("João Silva", usuario.getNome()),
                () -> assertEquals("joao@email.com", usuario.getEmail()),
                () -> assertEquals("Desenvolvimento", usuario.getAreaAtuacao()),
                () -> assertEquals("PLENO", usuario.getNivelCarreira()),
                () -> assertEquals(LocalDate.of(2024, 1, 15), usuario.getDataCadastro())
        );
    }

    @Test
    @DisplayName("Deve converter Usuario para UsuarioResponse")
    public void deveConverterUsuarioParaUsuarioResponse() {
        // Arrange
        Usuario usuario = new Usuario(
                5L,
                "Maria Santos",
                "maria@email.com",
                "Análise de Dados",
                "SENIOR",
                LocalDate.of(2024, 2, 20)
        );

        // Act
        UsuarioResponse response = UsuarioMapper.toResponse(usuario);

        // Assert
        assertAll("conversão entity -> response",
                () -> assertEquals(5L, response.id()),
                () -> assertEquals("Maria Santos", response.nome()),
                () -> assertEquals("maria@email.com", response.email()),
                () -> assertEquals("Análise de Dados", response.areaAtuacao()),
                () -> assertEquals("SENIOR", response.nivelCarreira()),
                () -> assertEquals(LocalDate.of(2024, 2, 20), response.dataCadastro())
        );
    }

    @Test
    @DisplayName("Deve lidar com campos null em UsuarioRequest")
    public void deveLidarComCamposNullEmUsuarioRequest() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest(
                "Pedro Costa",
                "pedro@email.com",
                null, // areaAtuacao null
                null, // nivelCarreira null
                LocalDate.now()
        );

        // Act
        Usuario usuario = UsuarioMapper.toEntity(request);

        // Assert
        assertAll("conversão com nulls",
                () -> assertEquals("Pedro Costa", usuario.getNome()),
                () -> assertEquals("pedro@email.com", usuario.getEmail()),
                () -> assertNull(usuario.getAreaAtuacao()),
                () -> assertNull(usuario.getNivelCarreira())
        );
    }

    @Test
    @DisplayName("Deve lidar com campos null em Usuario")
    public void deveLidarComCamposNullEmUsuario() {
        // Arrange
        Usuario usuario = new Usuario(
                10L,
                "Ana Lima",
                "ana@email.com",
                null, // areaAtuacao null
                null, // nivelCarreira null
                LocalDate.now()
        );

        // Act
        UsuarioResponse response = UsuarioMapper.toResponse(usuario);

        // Assert
        assertAll("conversão com nulls",
                () -> assertEquals(10L, response.id()),
                () -> assertEquals("Ana Lima", response.nome()),
                () -> assertNull(response.areaAtuacao()),
                () -> assertNull(response.nivelCarreira())
        );
    }

    @Test
    @DisplayName("Deve preservar data corretamente")
    public void devePreservarDataCorretamente() {
        // Arrange
        LocalDate dataEspecifica = LocalDate.of(2023, 12, 25);
        UsuarioRequest request = new UsuarioRequest(
                "Teste",
                "teste@email.com",
                "TI",
                "JUNIOR",
                dataEspecifica
        );

        // Act
        Usuario usuario = UsuarioMapper.toEntity(request);

        // Assert
        assertEquals(dataEspecifica, usuario.getDataCadastro());
    }
}