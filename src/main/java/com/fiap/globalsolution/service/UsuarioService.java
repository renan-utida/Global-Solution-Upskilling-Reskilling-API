package com.fiap.globalsolution.service;

import com.fiap.globalsolution.dto.UsuarioMapper;
import com.fiap.globalsolution.dto.UsuarioRequest;
import com.fiap.globalsolution.dto.UsuarioResponse;
import com.fiap.globalsolution.exception.UsuarioNaoEncontradoException;
import com.fiap.globalsolution.model.Usuario;
import com.fiap.globalsolution.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service para gerenciamento de Usuarios
 * Contém regras de negócio e validações
 */
@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Lista todos os usuários
     */
    public List<UsuarioResponse> findAll() {
        return repository.findAll().stream()
                .map(UsuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca um usuário por ID
     */
    public Optional<UsuarioResponse> findById(Long id) {
        return repository.findById(id)
                .map(UsuarioMapper::toResponse);
    }

    /**
     * Busca um usuário por email
     */
    public Optional<UsuarioResponse> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(UsuarioMapper::toResponse);
    }

    /**
     * Busca usuários por área de atuação
     */
    public List<UsuarioResponse> findByAreaAtuacao(String areaAtuacao) {
        return repository.findByAreaAtuacaoContaining(areaAtuacao).stream()
                .map(UsuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Cria um novo usuário
     * Validação: verifica se email já existe
     */
    public UsuarioResponse create(UsuarioRequest request) {
        // Validação: Email único
        if (repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException(
                    "Já existe um usuário cadastrado com o email: " + request.email()
            );
        }

        Usuario usuario = UsuarioMapper.toEntity(request);
        Usuario saved = repository.save(usuario);
        return UsuarioMapper.toResponse(saved);
    }

    /**
     * Atualiza um usuário existente
     * Validação: verifica se email já existe (exceto para o próprio usuário)
     */
    public Optional<UsuarioResponse> update(Long id, UsuarioRequest request) {
        return repository.findById(id)
                .map(usuario -> {
                    // Validação: Email único (permitindo manter o próprio email)
                    Optional<Usuario> usuarioComEmail = repository.findByEmail(request.email());
                    if (usuarioComEmail.isPresent() &&
                            !usuarioComEmail.get().getIdUsuario().equals(id)) {
                        throw new IllegalArgumentException(
                                "Já existe outro usuário cadastrado com o email: " + request.email()
                        );
                    }

                    UsuarioMapper.updateEntityFromRequest(usuario, request);
                    Usuario updated = repository.save(usuario);
                    return UsuarioMapper.toResponse(updated);
                });
    }

    /**
     * Deleta um usuário por ID
     */
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(usuario -> {
                    repository.delete(usuario);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Verifica se um usuário existe por ID
     */
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    /**
     * Busca entidade Usuario por ID (para uso interno)
     * Lança exceção se não encontrado
     */
    public Usuario getUsuarioEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        "Usuário não encontrado com ID: " + id
                ));
    }
}