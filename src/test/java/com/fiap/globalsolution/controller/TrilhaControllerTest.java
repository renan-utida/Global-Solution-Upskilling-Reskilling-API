package com.fiap.globalsolution.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.globalsolution.dto.TrilhaRequest;
import com.fiap.globalsolution.dto.TrilhaResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.security.JwtAuthFilter;
import com.fiap.globalsolution.security.JwtService;
import com.fiap.globalsolution.service.TrilhaService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrilhaController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do TrilhaController")
public class TrilhaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrilhaService service;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    private TrilhaResponse trilhaResponse;
    private TrilhaRequest trilhaRequest;

    @BeforeEach
    public void setUp() {
        trilhaResponse = new TrilhaResponse(
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
    @DisplayName("GET /api/trilhas - Deve listar todas as trilhas")
    public void testListarTodas_DeveRetornarListaDeTrilhas() throws Exception {
        // Arrange
        TrilhaResponse trilha2 = new TrilhaResponse(2L, "Python Básico", "Introdução ao Python", "INICIANTE", 30, "Programação");
        List<TrilhaResponse> trilhas = Arrays.asList(trilhaResponse, trilha2);
        when(service.findAll()).thenReturn(trilhas);

        // Act & Assert
        mockMvc.perform(get("/api/trilhas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Fundamentos de IA"))
                .andExpect(jsonPath("$[1].nome").value("Python Básico"));

        verify(service, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /api/trilhas/{id} - Deve retornar trilha quando existe")
    public void testBuscarPorId_QuandoTrilhaExiste_DeveRetornar200() throws Exception {
        // Arrange
        when(service.findById(1L)).thenReturn(Optional.of(trilhaResponse));

        // Act & Assert
        mockMvc.perform(get("/api/trilhas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Fundamentos de IA"))
                .andExpect(jsonPath("$.nivel").value("INICIANTE"));

        verify(service, times(1)).findById(1L);
    }

    @Test
    @DisplayName("GET /api/trilhas/{id} - Deve retornar 404 quando trilha não existe")
    public void testBuscarPorId_QuandoTrilhaNaoExiste_DeveRetornar404() throws Exception {
        // Arrange
        when(service.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/trilhas/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findById(999L);
    }

    @Test
    @DisplayName("GET /api/trilhas/nivel/{nivel} - Deve retornar trilhas por nível")
    public void testBuscarPorNivel_DeveRetornarTrilhasDoNivel() throws Exception {
        // Arrange
        TrilhaResponse trilha2 = new TrilhaResponse(2L, "Python Básico", "Intro Python", "INICIANTE", 30, "Programação");
        List<TrilhaResponse> trilhas = Arrays.asList(trilhaResponse, trilha2);
        when(service.findByNivel("INICIANTE")).thenReturn(trilhas);

        // Act & Assert
        mockMvc.perform(get("/api/trilhas/nivel/INICIANTE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nivel").value("INICIANTE"));

        verify(service, times(1)).findByNivel("INICIANTE");
    }

    @Test
    @DisplayName("POST /api/trilhas - Deve criar trilha com sucesso")
    public void testCriar_QuandoDadosValidos_DeveRetornar201() throws Exception {
        // Arrange
        TrilhaResponse novaTrilha = new TrilhaResponse(3L, "Machine Learning Avançado", "Técnicas avançadas", "AVANCADO", 80, "IA");
        when(service.create(any(TrilhaRequest.class))).thenReturn(novaTrilha);

        // Act & Assert
        mockMvc.perform(post("/api/trilhas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trilhaRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nome").value("Machine Learning Avançado"));

        verify(service, times(1)).create(any(TrilhaRequest.class));
    }

    @Test
    @DisplayName("POST /api/trilhas - Deve retornar 409 quando trilha duplicada")
    public void testCriar_QuandoTrilhaDuplicada_DeveRetornar409() throws Exception {
        // Arrange
        when(service.create(any(TrilhaRequest.class)))
                .thenThrow(new DuplicateEntityException("Já existe uma trilha cadastrada com estes dados"));

        // Act & Assert
        mockMvc.perform(post("/api/trilhas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trilhaRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").exists());

        verify(service, times(1)).create(any(TrilhaRequest.class));
    }

    @Test
    @DisplayName("PUT /api/trilhas/{id} - Deve atualizar trilha com sucesso")
    public void testAtualizar_QuandoDadosValidos_DeveRetornar200() throws Exception {
        // Arrange
        TrilhaResponse trilhaAtualizada = new TrilhaResponse(1L, "Fundamentos de IA - Atualizado", "Descrição atualizada", "INTERMEDIARIO", 50, "IA");
        when(service.update(eq(1L), any(TrilhaRequest.class))).thenReturn(Optional.of(trilhaAtualizada));

        // Act & Assert
        mockMvc.perform(put("/api/trilhas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trilhaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Fundamentos de IA - Atualizado"));

        verify(service, times(1)).update(eq(1L), any(TrilhaRequest.class));
    }

    @Test
    @DisplayName("DELETE /api/trilhas/{id} - Deve deletar trilha com sucesso")
    public void testDeletar_QuandoTrilhaExiste_DeveRetornar204() throws Exception {
        // Arrange
        when(service.delete(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/trilhas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
}