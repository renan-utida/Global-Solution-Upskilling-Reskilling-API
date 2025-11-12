package com.fiap.globalsolution.exception;

/**
 * Exceção lançada quando uma matrícula não é encontrada
 */
public class MatriculaNaoEncontradaException extends RuntimeException {
    public MatriculaNaoEncontradaException(String message) {
        super(message);
    }
}