package com.fiap.globalsolution.exception;

/**
 * Exceção lançada quando uma matrícula não é encontrada
 */
public class MatriculaNaoEncontradaException extends RuntimeException {

    public MatriculaNaoEncontradaException(String message) {
        super(message);
    }

    public MatriculaNaoEncontradaException(Long id) {
        super("Matrícula não encontrada com ID: " + id);
    }
}