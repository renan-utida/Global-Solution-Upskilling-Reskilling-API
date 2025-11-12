package com.fiap.globalsolution.exception;

/**
 * Exceção lançada quando se tenta criar/atualizar uma entidade duplicada
 */
public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}