package com.fiap.globalsolution.exception;

/**
 * Exceção lançada quando uma competência não é encontrada
 */
public class CompetenciaNaoEncontradaException extends RuntimeException {

    public CompetenciaNaoEncontradaException(String message) {
        super(message);
    }

    public CompetenciaNaoEncontradaException(Long id) {
        super("Competência não encontrada com ID: " + id);
    }
}