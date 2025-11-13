package com.fiap.globalsolution.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Exceções Customizadas")
public class ExceptionsTest {

    @Test
    @DisplayName("Deve criar DuplicateEntityException com mensagem")
    public void deveCriarDuplicateEntityException() {
        String mensagem = "Entidade duplicada";

        DuplicateEntityException exception = new DuplicateEntityException(mensagem);

        assertEquals(mensagem, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Deve criar UsuarioNaoEncontradoException com mensagem")
    public void deveCriarUsuarioNaoEncontradoException() {
        String mensagem = "Usuário não encontrado";

        UsuarioNaoEncontradoException exception = new UsuarioNaoEncontradoException(mensagem);

        assertEquals(mensagem, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Deve criar TrilhaNaoEncontradaException com mensagem")
    public void deveCriarTrilhaNaoEncontradaException() {
        String mensagem = "Trilha não encontrada";

        TrilhaNaoEncontradaException exception = new TrilhaNaoEncontradaException(mensagem);

        assertEquals(mensagem, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Deve criar MatriculaNaoEncontradaException com mensagem")
    public void deveCriarMatriculaNaoEncontradaException() {
        String mensagem = "Matrícula não encontrada";

        MatriculaNaoEncontradaException exception = new MatriculaNaoEncontradaException(mensagem);

        assertEquals(mensagem, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Deve permitir lançar e capturar DuplicateEntityException")
    public void deveLancarECapturarDuplicateEntityException() {
        assertThrows(DuplicateEntityException.class, () -> {
            throw new DuplicateEntityException("Erro");
        });
    }
}