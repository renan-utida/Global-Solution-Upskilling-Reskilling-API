package com.fiap.globalsolution.exception;

/**
 * Exceção lançada quando uma trilha não é encontrada
 */
public class TrilhaNaoEncontradaException extends RuntimeException {

    public TrilhaNaoEncontradaException(String message) {
        super(message);
    }

    public TrilhaNaoEncontradaException(Long id) {
        super("Trilha não encontrada com ID: " + id);
    }
}