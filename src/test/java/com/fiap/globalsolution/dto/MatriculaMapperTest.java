package com.fiap.globalsolution.dto;

import com.fiap.globalsolution.model.Matricula;
import com.fiap.globalsolution.model.Trilha;
import com.fiap.globalsolution.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes - MatriculaMapper")
public class MatriculaMapperTest {

    @Test
    @DisplayName("Deve converter MatriculaRequest para entidade Matricula")
    public void deveConverterRequestParaEntity() {
        Usuario usuario = new Usuario(1L, "João", "joao@email.com", "TI", "PLENO", LocalDate.now());
        Trilha trilha = new Trilha(1L, "IA", "Curso IA", "INICIANTE", 40, "IA");

        MatriculaRequest request = new MatriculaRequest(
                1L, 1L, LocalDate.of(2024, 6, 1), "EM_ANDAMENTO"
        );

        Matricula matricula = MatriculaMapper.toEntity(request, usuario, trilha);

        assertAll(
                () -> assertNull(matricula.getIdMatricula()),
                () -> assertEquals("João", matricula.getUsuario().getNome()),
                () -> assertEquals("IA", matricula.getTrilha().getNome()),
                () -> assertEquals("EM_ANDAMENTO", matricula.getStatus())
        );
    }

    @Test
    @DisplayName("Deve converter entidade Matricula para MatriculaResponse")
    public void deveConverterEntityParaResponse() {
        Usuario usuario = new Usuario(1L, "Maria", "maria@email.com", "Dev", "SENIOR", LocalDate.now());
        Trilha trilha = new Trilha(1L, "Python", "Curso", "AVANCADO", 60, "Programação");
        Matricula matricula = new Matricula(1L, usuario, trilha, LocalDate.now(), "CONCLUIDA");

        MatriculaResponse response = MatriculaMapper.toResponse(matricula);

        assertAll(
                () -> assertEquals(1L, response.id()),
                () -> assertEquals("Maria", response.usuario().nome()),
                () -> assertEquals("Python", response.trilha().nome()),
                () -> assertEquals("CONCLUIDA", response.status())
        );
    }
}