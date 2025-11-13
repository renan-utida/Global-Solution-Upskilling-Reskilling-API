package com.fiap.globalsolution.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.globalsolution.dto.UsuarioRequest;
import com.fiap.globalsolution.dto.UsuarioResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.security.JwtAuthFilter;
import com.fiap.globalsolution.security.JwtService;
import com.fiap.globalsolution.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do UsuarioController")
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService service;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    private UsuarioResponse usuarioResponse;
    private UsuarioRequest usuarioRequest;

    @BeforeEach
    public void setUp() {
        usuarioResponse = new UsuarioResponse(
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
    @DisplayName("GET /api/usuarios - Deve listar todos os usuários")
    public void testListarTodos_DeveRetornarListaDeUsuarios() throws Exception {
        // Arrange
        UsuarioResponse usuario2 = new UsuarioResponse(2L, "Ana Costa", "ana@email.com", "RH", "JUNIOR", LocalDate.now());
        List<UsuarioResponse> usuarios = Arrays.asList(usuarioResponse, usuario2);
        when(service.findAll()).thenReturn(usuarios);

        // Act & Assert
        mockMvc.perform(get("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("João Silva"))
                .andExpect(jsonPath("$[1].nome").value("Ana Costa"));

        verify(service, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} - Deve retornar usuário quando existe")
    public void testBuscarPorId_QuandoUsuarioExiste_DeveRetornar200() throws Exception {
        // Arrange
        when(service.findById(1L)).thenReturn(Optional.of(usuarioResponse));

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(service, times(1)).findById(1L);
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} - Deve retornar 404 quando usuário não existe")
    public void testBuscarPorId_QuandoUsuarioNaoExiste_DeveRetornar404() throws Exception {
        // Arrange
        when(service.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findById(999L);
    }

    @Test
    @DisplayName("GET /api/usuarios/email/{email} - Deve retornar usuário por email")
    public void testBuscarPorEmail_QuandoUsuarioExiste_DeveRetornar200() throws Exception {
        // Arrange
        when(service.findByEmail("joao@email.com")).thenReturn(Optional.of(usuarioResponse));

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/email/joao@email.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.nome").value("João Silva"));

        verify(service, times(1)).findByEmail("joao@email.com");
    }

    @Test
    @DisplayName("POST /api/usuarios - Deve criar usuário com sucesso")
    public void testCriar_QuandoDadosValidos_DeveRetornar201() throws Exception {
        // Arrange
        UsuarioResponse novoUsuario = new UsuarioResponse(3L, "Maria Santos", "maria@email.com", "Marketing", "SENIOR", LocalDate.of(2024, 6, 10));
        when(service.create(any(UsuarioRequest.class))).thenReturn(novoUsuario);

        // Act & Assert
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nome").value("Maria Santos"));

        verify(service, times(1)).create(any(UsuarioRequest.class));
    }

    @Test
    @DisplayName("POST /api/usuarios - Deve retornar 409 quando email duplicado")
    public void testCriar_QuandoEmailDuplicado_DeveRetornar409() throws Exception {
        // Arrange
        when(service.create(any(UsuarioRequest.class)))
                .thenThrow(new DuplicateEntityException("Já existe um usuário cadastrado com o email: maria@email.com"));

        // Act & Assert
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Já existe um usuário cadastrado com o email: maria@email.com"));

        verify(service, times(1)).create(any(UsuarioRequest.class));
    }

    @Test
    @DisplayName("PUT /api/usuarios/{id} - Deve atualizar usuário com sucesso")
    public void testAtualizar_QuandoDadosValidos_DeveRetornar200() throws Exception {
        // Arrange
        UsuarioResponse usuarioAtualizado = new UsuarioResponse(1L, "João Silva Atualizado", "joao@email.com", "Engenharia", "SENIOR", LocalDate.of(2024, 1, 15));
        when(service.update(eq(1L), any(UsuarioRequest.class))).thenReturn(Optional.of(usuarioAtualizado));

        // Act & Assert
        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva Atualizado"))
                .andExpect(jsonPath("$.areaAtuacao").value("Engenharia"));

        verify(service, times(1)).update(eq(1L), any(UsuarioRequest.class));
    }

    @Test
    @DisplayName("DELETE /api/usuarios/{id} - Deve deletar usuário com sucesso")
    public void testDeletar_QuandoUsuarioExiste_DeveRetornar204() throws Exception {
        // Arrange
        when(service.delete(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
}