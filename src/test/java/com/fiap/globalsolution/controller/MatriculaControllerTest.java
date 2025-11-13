package com.fiap.globalsolution.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.globalsolution.dto.MatriculaRequest;
import com.fiap.globalsolution.dto.MatriculaResponse;
import com.fiap.globalsolution.dto.TrilhaResponse;
import com.fiap.globalsolution.dto.UsuarioResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.exception.UsuarioNaoEncontradoException;
import com.fiap.globalsolution.security.JwtAuthFilter;
import com.fiap.globalsolution.security.JwtService;
import com.fiap.globalsolution.service.MatriculaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatriculaController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do MatriculaController")
public class MatriculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MatriculaService service;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    private MatriculaResponse matriculaResponse;
    private MatriculaRequest matriculaRequest;
    private UsuarioResponse usuarioResponse;
    private TrilhaResponse trilhaResponse;

    @BeforeEach
    public void setUp() {
        usuarioResponse = new UsuarioResponse(1L, "João Silva", "joao@email.com", "Tecnologia", "PLENO", LocalDate.now());
        trilhaResponse = new TrilhaResponse(1L, "Fundamentos de IA", "Introdução à IA", "INICIANTE", 40, "IA");

        matriculaResponse = new MatriculaResponse(
                1L,
                usuarioResponse,
                trilhaResponse,
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
    @DisplayName("GET /api/matriculas - Deve listar todas as matrículas")
    public void testListarTodas_DeveRetornarListaDeMatriculas() throws Exception {
        // Arrange
        MatriculaResponse matricula2 = new MatriculaResponse(2L, usuarioResponse, trilhaResponse, LocalDate.now(), "CONCLUIDA");
        List<MatriculaResponse> matriculas = Arrays.asList(matriculaResponse, matricula2);
        when(service.findAll()).thenReturn(matriculas);

        // Act & Assert
        mockMvc.perform(get("/api/matriculas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].status").value("EM_ANDAMENTO"))
                .andExpect(jsonPath("$[1].status").value("CONCLUIDA"));

        verify(service, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /api/matriculas/{id} - Deve retornar matrícula quando existe")
    public void testBuscarPorId_QuandoMatriculaExiste_DeveRetornar200() throws Exception {
        // Arrange
        when(service.findById(1L)).thenReturn(Optional.of(matriculaResponse));

        // Act & Assert
        mockMvc.perform(get("/api/matriculas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("EM_ANDAMENTO"))
                .andExpect(jsonPath("$.usuario.nome").value("João Silva"));

        verify(service, times(1)).findById(1L);
    }

    @Test
    @DisplayName("GET /api/matriculas/usuario/{usuarioId} - Deve retornar matrículas do usuário")
    public void testBuscarPorUsuario_DeveRetornarMatriculasDoUsuario() throws Exception {
        // Arrange
        List<MatriculaResponse> matriculas = Arrays.asList(matriculaResponse);
        when(service.findByUsuarioId(1L)).thenReturn(matriculas);

        // Act & Assert
        mockMvc.perform(get("/api/matriculas/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(service, times(1)).findByUsuarioId(1L);
    }

    @Test
    @DisplayName("POST /api/matriculas - Deve criar matrícula com sucesso")
    public void testCriar_QuandoDadosValidos_DeveRetornar201() throws Exception {
        // Arrange
        MatriculaResponse novaMatricula = new MatriculaResponse(3L, usuarioResponse, trilhaResponse, LocalDate.of(2024, 11, 10), "EM_ANDAMENTO");
        when(service.create(any(MatriculaRequest.class))).thenReturn(novaMatricula);

        // Act & Assert
        mockMvc.perform(post("/api/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matriculaRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.status").value("EM_ANDAMENTO"));

        verify(service, times(1)).create(any(MatriculaRequest.class));
    }

    @Test
    @DisplayName("POST /api/matriculas - Deve retornar 404 quando usuário não existe")
    public void testCriar_QuandoUsuarioNaoExiste_DeveRetornar404() throws Exception {
        // Arrange
        when(service.create(any(MatriculaRequest.class)))
                .thenThrow(new UsuarioNaoEncontradoException("Usuário com ID 1 não encontrado"));

        // Act & Assert
        mockMvc.perform(post("/api/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matriculaRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Usuário com ID 1 não encontrado"));

        verify(service, times(1)).create(any(MatriculaRequest.class));
    }

    @Test
    @DisplayName("POST /api/matriculas - Deve retornar 409 quando matrícula duplicada")
    public void testCriar_QuandoMatriculaDuplicada_DeveRetornar409() throws Exception {
        // Arrange
        when(service.create(any(MatriculaRequest.class)))
                .thenThrow(new DuplicateEntityException("Usuário já está matriculado na trilha"));

        // Act & Assert
        mockMvc.perform(post("/api/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matriculaRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409));

        verify(service, times(1)).create(any(MatriculaRequest.class));
    }

    @Test
    @DisplayName("PUT /api/matriculas/{id} - Deve atualizar matrícula com sucesso")
    public void testAtualizar_QuandoDadosValidos_DeveRetornar200() throws Exception {
        // Arrange
        MatriculaResponse matriculaAtualizada = new MatriculaResponse(1L, usuarioResponse, trilhaResponse, LocalDate.of(2024, 6, 1), "CONCLUIDA");
        when(service.update(eq(1L), any(MatriculaRequest.class))).thenReturn(Optional.of(matriculaAtualizada));

        // Act & Assert
        mockMvc.perform(put("/api/matriculas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matriculaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONCLUIDA"));

        verify(service, times(1)).update(eq(1L), any(MatriculaRequest.class));
    }

    @Test
    @DisplayName("DELETE /api/matriculas/{id} - Deve deletar matrícula com sucesso")
    public void testDeletar_QuandoMatriculaExiste_DeveRetornar204() throws Exception {
        // Arrange
        when(service.delete(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/matriculas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
}