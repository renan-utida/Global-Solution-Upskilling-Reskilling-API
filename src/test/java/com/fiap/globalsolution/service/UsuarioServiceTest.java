package com.fiap.globalsolution.service;

import com.fiap.globalsolution.dto.UsuarioRequest;
import com.fiap.globalsolution.dto.UsuarioResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.model.Usuario;
import com.fiap.globalsolution.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do UsuarioService")
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuarioExistente;
    private UsuarioRequest usuarioRequest;

    @BeforeEach
    public void setUp() {
        usuarioExistente = new Usuario(
                1L,
                "João Silva",
                "joao@email.com",
                "Tecnologia",
                "PLENO",
                LocalDate.of(2024, 1, 15)
        );

        usuarioRequest = new UsuarioRequest(
                "Maria Santos",
                "maria@email.com",
                "Marketing",
                "SENIOR",
                LocalDate.of(2024, 6, 10)
        );
    }

    @Test
    @DisplayName("Deve listar todos os usuários com sucesso")
    public void testFindAll_DeveRetornarListaDeUsuarios() {
        // Arrange
        Usuario usuario2 = new Usuario(2L, "Ana Costa", "ana@email.com", "RH", "JUNIOR", LocalDate.now());
        when(repository.findAll()).thenReturn(Arrays.asList(usuarioExistente, usuario2));

        // Act
        List<UsuarioResponse> resultado = service.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("João Silva", resultado.get(0).nome());
        assertEquals("Ana Costa", resultado.get(1).nome());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    public void testFindById_QuandoUsuarioExiste_DeveRetornarUsuario() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(usuarioExistente));

        // Act
        Optional<UsuarioResponse> resultado = service.findById(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().nome());
        assertEquals("joao@email.com", resultado.get().email());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar vazio quando usuário não existe")
    public void testFindById_QuandoUsuarioNaoExiste_DeveRetornarVazio() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<UsuarioResponse> resultado = service.findById(999L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(repository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve criar novo usuário com sucesso")
    public void testCreate_QuandoDadosValidos_DeveCriarUsuario() {
        // Arrange
        Usuario novoUsuario = new Usuario(3L, "Maria Santos", "maria@email.com", "Marketing", "SENIOR", LocalDate.of(2024, 6, 10));
        when(repository.findByEmail(usuarioRequest.email())).thenReturn(Optional.empty());
        when(repository.findByAllFields(any(), any(), any(), any())).thenReturn(Optional.empty());
        when(repository.save(any(Usuario.class))).thenReturn(novoUsuario);

        // Act
        UsuarioResponse resultado = service.create(usuarioRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals("Maria Santos", resultado.nome());
        assertEquals("maria@email.com", resultado.email());
        verify(repository, times(1)).findByEmail(usuarioRequest.email());
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com email duplicado")
    public void testCreate_QuandoEmailDuplicado_DeveLancarExcecao() {
        // Arrange
        when(repository.findByEmail(usuarioRequest.email())).thenReturn(Optional.of(usuarioExistente));

        // Act & Assert
        DuplicateEntityException exception = assertThrows(
                DuplicateEntityException.class,
                () -> service.create(usuarioRequest)
        );

        assertTrue(exception.getMessage().contains("email"));
        verify(repository, times(1)).findByEmail(usuarioRequest.email());
        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    public void testUpdate_QuandoDadosValidos_DeveAtualizarUsuario() {
        // Arrange
        UsuarioRequest requestAtualizado = new UsuarioRequest(
                "João Silva Atualizado",
                "joao@email.com",
                "Engenharia",
                "SENIOR",
                LocalDate.of(2024, 1, 15)
        );
        Usuario usuarioAtualizado = new Usuario(1L, "João Silva Atualizado", "joao@email.com", "Engenharia", "SENIOR", LocalDate.of(2024, 1, 15));

        when(repository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(repository.findByEmail(requestAtualizado.email())).thenReturn(Optional.of(usuarioExistente));
        when(repository.findByAllFields(any(), any(), any(), any())).thenReturn(Optional.empty());
        when(repository.save(any(Usuario.class))).thenReturn(usuarioAtualizado);

        // Act
        Optional<UsuarioResponse> resultado = service.update(1L, requestAtualizado);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("João Silva Atualizado", resultado.get().nome());
        assertEquals("Engenharia", resultado.get().areaAtuacao());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    public void testDelete_QuandoUsuarioExiste_DeveDeletarUsuario() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        doNothing().when(repository).delete(usuarioExistente);

        // Act
        boolean resultado = service.delete(1L);

        // Assert
        assertTrue(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(usuarioExistente);
    }

    @Test
    @DisplayName("Deve retornar falso ao tentar deletar usuário inexistente")
    public void testDelete_QuandoUsuarioNaoExiste_DeveRetornarFalso() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act
        boolean resultado = service.delete(999L);

        // Assert
        assertFalse(resultado);
        verify(repository, times(1)).findById(999L);
        verify(repository, never()).delete(any(Usuario.class));
    }
}