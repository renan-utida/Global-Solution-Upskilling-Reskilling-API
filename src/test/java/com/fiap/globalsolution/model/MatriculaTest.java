package com.fiap.globalsolution.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da Entidade Matricula")
public class MatriculaTest {

    @Test
    @DisplayName("Deve criar Matricula com construtor completo")
    public void deveCriarMatriculaComConstrutorCompleto() {
        // Arrange
        Usuario usuario = new Usuario(1L, "João", "joao@email.com", "Dev", "PLENO", LocalDate.now());
        Trilha trilha = new Trilha(1L, "IA", "Desc", "INICIANTE", 40, "IA");

        // Act
        Matricula matricula = new Matricula(
                1L,
                usuario,
                trilha,
                LocalDate.of(2024, 6, 1),
                "EM_ANDAMENTO"
        );

        // Assert
        assertAll("matricula",
                () -> assertEquals(1L, matricula.getIdMatricula()),
                () -> assertEquals(usuario, matricula.getUsuario()),
                () -> assertEquals(trilha, matricula.getTrilha()),
                () -> assertEquals(LocalDate.of(2024, 6, 1), matricula.getDataInscricao()),
                () -> assertEquals("EM_ANDAMENTO", matricula.getStatus())
        );
    }

    @Test
    @DisplayName("Deve criar Matricula com construtor sem ID")
    public void deveCriarMatriculaComConstrutorSemId() {
        // Arrange
        Usuario usuario = new Usuario(2L, "Maria", "maria@email.com", "Dados", "SENIOR", LocalDate.now());
        Trilha trilha = new Trilha(2L, "ML", "Desc", "AVANCADO", 80, "IA");

        // Act
        Matricula matricula = new Matricula(
                usuario,
                trilha,
                LocalDate.of(2024, 7, 15),
                "CONCLUIDA"
        );

        // Assert
        assertAll("matricula",
                () -> assertNull(matricula.getIdMatricula()),
                () -> assertEquals(usuario, matricula.getUsuario()),
                () -> assertEquals(trilha, matricula.getTrilha()),
                () -> assertEquals("CONCLUIDA", matricula.getStatus())
        );
    }

    @Test
    @DisplayName("Deve criar Matricula vazia com construtor padrão")
    public void deveCriarMatriculaVaziaComConstrutorPadrao() {
        // Arrange & Act
        Matricula matricula = new Matricula();

        // Assert
        assertAll("matricula",
                () -> assertNull(matricula.getIdMatricula()),
                () -> assertNull(matricula.getUsuario()),
                () -> assertNull(matricula.getTrilha()),
                () -> assertNull(matricula.getDataInscricao()),
                () -> assertNull(matricula.getStatus())
        );
    }

    @Test
    @DisplayName("Deve permitir usar setters")
    public void devePermitirUsarSetters() {
        // Arrange
        Matricula matricula = new Matricula();
        Usuario usuario = new Usuario(3L, "Carlos", "carlos@email.com", "RH", "JUNIOR", LocalDate.now());
        Trilha trilha = new Trilha(3L, "Liderança", "Desc", "INTERMEDIARIO", 30, "Soft Skills");

        // Act
        matricula.setIdMatricula(10L);
        matricula.setUsuario(usuario);
        matricula.setTrilha(trilha);
        matricula.setDataInscricao(LocalDate.of(2024, 8, 1));
        matricula.setStatus("CANCELADA");

        // Assert
        assertAll("matricula",
                () -> assertEquals(10L, matricula.getIdMatricula()),
                () -> assertEquals(usuario, matricula.getUsuario()),
                () -> assertEquals(trilha, matricula.getTrilha()),
                () -> assertEquals(LocalDate.of(2024, 8, 1), matricula.getDataInscricao()),
                () -> assertEquals("CANCELADA", matricula.getStatus())
        );
    }

    @Test
    @DisplayName("Deve gerar toString corretamente")
    public void deveGerarToStringCorretamente() {
        // Arrange
        Usuario usuario = new Usuario(4L, "Ana", "ana@email.com", "Marketing", "PLENO", LocalDate.now());
        Trilha trilha = new Trilha(4L, "Digital", "Desc", "INICIANTE", 25, "Marketing");
        Matricula matricula = new Matricula(
                5L,
                usuario,
                trilha,
                LocalDate.of(2024, 9, 10),
                "EM_ANDAMENTO"
        );

        // Act
        String resultado = matricula.toString();

        // Assert
        assertAll("toString",
                () -> assertTrue(resultado.contains("idMatricula=5")),
                () -> assertTrue(resultado.contains("usuario=Ana")),
                () -> assertTrue(resultado.contains("trilha=Digital")),
                () -> assertTrue(resultado.contains("status='EM_ANDAMENTO'"))
        );
    }

    @Test
    @DisplayName("Deve validar status válidos")
    public void deveValidarStatusValidos() {
        // Arrange
        Usuario usuario = new Usuario(1L, "Pedro", "pedro@email.com", "TI", "PLENO", LocalDate.now());
        Trilha trilha = new Trilha(1L, "DevOps", "Desc", "INTERMEDIARIO", 60, "Cloud");

        // Act & Assert
        assertDoesNotThrow(() -> {
            new Matricula(1L, usuario, trilha, LocalDate.now(), "EM_ANDAMENTO");
            new Matricula(2L, usuario, trilha, LocalDate.now(), "CONCLUIDA");
            new Matricula(3L, usuario, trilha, LocalDate.now(), "CANCELADA");
        });
    }

    @Test
    @DisplayName("ToString deve lidar com usuario null")
    public void toStringDeveLidarComUsuarioNull() {
        // Arrange
        Trilha trilha = new Trilha(1L, "Trilha Test", "Desc", "INICIANTE", 10, "Teste");
        Matricula matricula = new Matricula(
                1L,
                null, // usuario null
                trilha,
                LocalDate.now(),
                "EM_ANDAMENTO"
        );

        // Act
        String resultado = matricula.toString();

        // Assert
        assertTrue(resultado.contains("usuario=null"));
    }

    @Test
    @DisplayName("ToString deve lidar com trilha null")
    public void toStringDeveLidarComTrilhaNull() {
        // Arrange
        Usuario usuario = new Usuario(1L, "Teste", "teste@email.com", "TI", "PLENO", LocalDate.now());
        Matricula matricula = new Matricula(
                1L,
                usuario,
                null, // trilha null
                LocalDate.now(),
                "EM_ANDAMENTO"
        );

        // Act
        String resultado = matricula.toString();

        // Assert
        assertTrue(resultado.contains("trilha=null"));
    }
}