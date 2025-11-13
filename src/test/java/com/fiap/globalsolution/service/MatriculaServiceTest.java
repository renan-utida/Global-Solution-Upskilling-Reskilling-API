package com.fiap.globalsolution.service;

import com.fiap.globalsolution.dto.MatriculaRequest;
import com.fiap.globalsolution.dto.MatriculaResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.exception.TrilhaNaoEncontradaException;
import com.fiap.globalsolution.exception.UsuarioNaoEncontradoException;
import com.fiap.globalsolution.model.Matricula;
import com.fiap.globalsolution.model.Trilha;
import com.fiap.globalsolution.model.Usuario;
import com.fiap.globalsolution.repository.MatriculaRepository;
import com.fiap.globalsolution.repository.TrilhaRepository;
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
@DisplayName("Testes da MatriculaService")
public class MatriculaServiceTest {

    @Mock
    private MatriculaRepository repository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TrilhaRepository trilhaRepository;

    @InjectMocks
    private MatriculaService service;

    private Usuario usuario;
    private Trilha trilha;
    private Matricula matriculaExistente;
    private MatriculaRequest matriculaRequest;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario(1L, "João Silva", "joao@email.com", "Tecnologia", "PLENO", LocalDate.now());
        trilha = new Trilha(1L, "Fundamentos de IA", "Introdução à IA", "INICIANTE", 40, "IA");

        matriculaExistente = new Matricula(
                1L,
                usuario,
                trilha,
                LocalDate.of(2024, 6, 1),
                "EM_ANDAMENTO"
        );

        matriculaRequest = new MatriculaRequest(
                1L,
                1L,
                LocalDate.of(2024, 11, 10),
                "EM_ANDAMENTO"
        );
    }

    @Test
    @DisplayName("Deve listar todas as matrículas com sucesso")
    public void testFindAll_DeveRetornarListaDeMatriculas() {
        // Arrange
        Matricula matricula2 = new Matricula(2L, usuario, trilha, LocalDate.now(), "CONCLUIDA");
        when(repository.findAll()).thenReturn(Arrays.asList(matriculaExistente, matricula2));

        // Act
        List<MatriculaResponse> resultado = service.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("EM_ANDAMENTO", resultado.get(0).status());
        assertEquals("CONCLUIDA", resultado.get(1).status());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar matrícula por ID com sucesso")
    public void testFindById_QuandoMatriculaExiste_DeveRetornarMatricula() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(matriculaExistente));

        // Act
        Optional<MatriculaResponse> resultado = service.findById(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("EM_ANDAMENTO", resultado.get().status());
        assertEquals("João Silva", resultado.get().usuario().nome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve criar nova matrícula com sucesso")
    public void testCreate_QuandoDadosValidos_DeveCriarMatricula() {
        // Arrange
        Matricula novaMatricula = new Matricula(3L, usuario, trilha, LocalDate.of(2024, 11, 10), "EM_ANDAMENTO");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(trilhaRepository.findById(1L)).thenReturn(Optional.of(trilha));
        when(repository.findMatriculaAtivaByUsuarioAndTrilha(usuario, trilha)).thenReturn(Optional.empty());
        when(repository.findByAllFields(any(), any(), any(), any())).thenReturn(Optional.empty());
        when(repository.save(any(Matricula.class))).thenReturn(novaMatricula);

        // Act
        MatriculaResponse resultado = service.create(matriculaRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals("EM_ANDAMENTO", resultado.status());
        assertEquals("João Silva", resultado.usuario().nome());
        verify(repository, times(1)).save(any(Matricula.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não existe")
    public void testCreate_QuandoUsuarioNaoExiste_DeveLancarExcecao() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> service.create(matriculaRequest)
        );

        assertTrue(exception.getMessage().contains("Usuário"));
        verify(usuarioRepository, times(1)).findById(1L);
        verify(repository, never()).save(any(Matricula.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando trilha não existe")
    public void testCreate_QuandoTrilhaNaoExiste_DeveLancarExcecao() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(trilhaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        TrilhaNaoEncontradaException exception = assertThrows(
                TrilhaNaoEncontradaException.class,
                () -> service.create(matriculaRequest)
        );

        assertTrue(exception.getMessage().contains("Trilha"));
        verify(trilhaRepository, times(1)).findById(1L);
        verify(repository, never()).save(any(Matricula.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário já matriculado na trilha")
    public void testCreate_QuandoMatriculaDuplicada_DeveLancarExcecao() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(trilhaRepository.findById(1L)).thenReturn(Optional.of(trilha));
        when(repository.findMatriculaAtivaByUsuarioAndTrilha(usuario, trilha))
                .thenReturn(Optional.of(matriculaExistente));

        // Act & Assert
        DuplicateEntityException exception = assertThrows(
                DuplicateEntityException.class,
                () -> service.create(matriculaRequest)
        );

        assertTrue(exception.getMessage().contains("já está matriculado"));
        verify(repository, never()).save(any(Matricula.class));
    }

    @Test
    @DisplayName("Deve deletar matrícula com sucesso")
    public void testDelete_QuandoMatriculaExiste_DeveDeletarMatricula() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(matriculaExistente));
        doNothing().when(repository).delete(matriculaExistente);

        // Act
        boolean resultado = service.delete(1L);

        // Assert
        assertTrue(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(matriculaExistente);
    }
}