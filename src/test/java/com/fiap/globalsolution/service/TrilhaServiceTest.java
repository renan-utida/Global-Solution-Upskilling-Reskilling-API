package com.fiap.globalsolution.service;

import com.fiap.globalsolution.dto.TrilhaRequest;
import com.fiap.globalsolution.dto.TrilhaResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.model.Trilha;
import com.fiap.globalsolution.repository.TrilhaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes da TrilhaService")
public class TrilhaServiceTest {

    @Mock
    private TrilhaRepository repository;

    @InjectMocks
    private TrilhaService service;

    private Trilha trilhaExistente;
    private TrilhaRequest trilhaRequest;

    @BeforeEach
    public void setUp() {
        trilhaExistente = new Trilha(
                1L,
                "Fundamentos de IA",
                "Introdução aos conceitos de IA",
                "INICIANTE",
                40,
                "Inteligência Artificial"
        );

        trilhaRequest = new TrilhaRequest(
                "Machine Learning Avançado",
                "Técnicas avançadas de ML",
                "AVANCADO",
                80,
                "Inteligência Artificial"
        );
    }

    @Test
    @DisplayName("Deve listar todas as trilhas com sucesso")
    public void testFindAll_DeveRetornarListaDeTrilhas() {
        // Arrange
        Trilha trilha2 = new Trilha(2L, "Python Básico", "Introdução ao Python", "INICIANTE", 30, "Programação");
        when(repository.findAll()).thenReturn(Arrays.asList(trilhaExistente, trilha2));

        // Act
        List<TrilhaResponse> resultado = service.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Fundamentos de IA", resultado.get(0).nome());
        assertEquals("Python Básico", resultado.get(1).nome());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar trilha por ID com sucesso")
    public void testFindById_QuandoTrilhaExiste_DeveRetornarTrilha() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(trilhaExistente));

        // Act
        Optional<TrilhaResponse> resultado = service.findById(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Fundamentos de IA", resultado.get().nome());
        assertEquals("INICIANTE", resultado.get().nivel());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve buscar trilhas por nível com sucesso")
    public void testFindByNivel_DeveRetornarTrilhasDoNivel() {
        // Arrange
        Trilha trilha2 = new Trilha(2L, "Python Básico", "Introdução ao Python", "INICIANTE", 30, "Programação");
        when(repository.findByNivel("INICIANTE")).thenReturn(Arrays.asList(trilhaExistente, trilha2));

        // Act
        List<TrilhaResponse> resultado = service.findByNivel("INICIANTE");

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("INICIANTE", resultado.get(0).nivel());
        assertEquals("INICIANTE", resultado.get(1).nivel());
        verify(repository, times(1)).findByNivel("INICIANTE");
    }

    @Test
    @DisplayName("Deve criar nova trilha com sucesso")
    public void testCreate_QuandoDadosValidos_DeveCriarTrilha() {
        // Arrange
        Trilha novaTrilha = new Trilha(3L, "Machine Learning Avançado", "Técnicas avançadas de ML", "AVANCADO", 80, "Inteligência Artificial");
        when(repository.findByAllFields(any(), any(), any(), any(), any())).thenReturn(Optional.empty());
        when(repository.save(any(Trilha.class))).thenReturn(novaTrilha);

        // Act
        TrilhaResponse resultado = service.create(trilhaRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals("Machine Learning Avançado", resultado.nome());
        assertEquals("AVANCADO", resultado.nivel());
        assertEquals(80, resultado.cargaHoraria());
        verify(repository, times(1)).save(any(Trilha.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar trilha duplicada")
    public void testCreate_QuandoTrilhaDuplicada_DeveLancarExcecao() {
        // Arrange
        when(repository.findByAllFields(any(), any(), any(), any(), any())).thenReturn(Optional.of(trilhaExistente));

        // Act & Assert
        DuplicateEntityException exception = assertThrows(
                DuplicateEntityException.class,
                () -> service.create(trilhaRequest)
        );

        assertTrue(exception.getMessage().contains("trilha"));
        verify(repository, times(1)).findByAllFields(any(), any(), any(), any(), any());
        verify(repository, never()).save(any(Trilha.class));
    }

    @Test
    @DisplayName("Deve atualizar trilha com sucesso")
    public void testUpdate_QuandoDadosValidos_DeveAtualizarTrilha() {
        // Arrange
        TrilhaRequest requestAtualizado = new TrilhaRequest(
                "Fundamentos de IA - Atualizado",
                "Descrição atualizada",
                "INTERMEDIARIO",
                50,
                "Inteligência Artificial"
        );
        Trilha trilhaAtualizada = new Trilha(1L, "Fundamentos de IA - Atualizado", "Descrição atualizada", "INTERMEDIARIO", 50, "Inteligência Artificial");

        when(repository.findById(1L)).thenReturn(Optional.of(trilhaExistente));
        when(repository.findByAllFields(any(), any(), any(), any(), any())).thenReturn(Optional.empty());
        when(repository.save(any(Trilha.class))).thenReturn(trilhaAtualizada);

        // Act
        Optional<TrilhaResponse> resultado = service.update(1L, requestAtualizado);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Fundamentos de IA - Atualizado", resultado.get().nome());
        assertEquals("INTERMEDIARIO", resultado.get().nivel());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Trilha.class));
    }

    @Test
    @DisplayName("Deve deletar trilha com sucesso")
    public void testDelete_QuandoTrilhaExiste_DeveDeletarTrilha() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(trilhaExistente));
        doNothing().when(repository).delete(trilhaExistente);

        // Act
        boolean resultado = service.delete(1L);

        // Assert
        assertTrue(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(trilhaExistente);
    }

    @Test
    @DisplayName("Deve retornar falso ao tentar deletar trilha inexistente")
    public void testDelete_QuandoTrilhaNaoExiste_DeveRetornarFalso() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act
        boolean resultado = service.delete(999L);

        // Assert
        assertFalse(resultado);
        verify(repository, times(1)).findById(999L);
        verify(repository, never()).delete(any(Trilha.class));
    }
}