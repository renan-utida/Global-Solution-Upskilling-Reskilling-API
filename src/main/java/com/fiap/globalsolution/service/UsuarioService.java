package com.fiap.globalsolution.service;

import com.fiap.globalsolution.dto.*;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.exception.UsuarioNaoEncontradoException;
import com.fiap.globalsolution.model.Usuario;
import com.fiap.globalsolution.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service para regras de negócio de Usuario
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
    public Optional<UsuarioResponse> findByAreaAtuacao(String areaAtuacao) {
        return repository.findByAreaAtuacao(areaAtuacao)
                .map(UsuarioMapper::toResponse);
    }

    /**
     * Cria um novo usuário
     * Validações:
     * - Email não pode estar duplicado
     * - Não permite cadastro de usuário com todos os dados iguais
     */
    public UsuarioResponse create(UsuarioRequest request) {
        // Validação 1: Email único
        Optional<Usuario> usuarioComMesmoEmail = repository.findByEmail(request.email());
        if (usuarioComMesmoEmail.isPresent()) {
            throw new DuplicateEntityException(
                    "Já existe um usuário cadastrado com o email: " + request.email()
            );
        }

        // Validação 2: Não permitir duplicata completa
        Optional<Usuario> usuarioExistente = repository.findByAllFields(
                request.nome(),
                request.email(),
                request.areaAtuacao(),
                request.nivelCarreira()
        );

        if (usuarioExistente.isPresent()) {
            throw new DuplicateEntityException(
                    "Já existe um usuário cadastrado com estes dados: " + request.nome()
            );
        }

        // Se passou nas validações, cria o usuário
        Usuario usuario = UsuarioMapper.toEntity(request);
        Usuario saved = repository.save(usuario);
        return UsuarioMapper.toResponse(saved);
    }

    /**
     * Atualiza um usuário existente
     * Validações:
     * - Não permite alterar para um email já existente (exceto se for o mesmo usuário)
     * - Não permite atualizar para dados idênticos a outro usuário
     */
    public Optional<UsuarioResponse> update(Long id, UsuarioRequest request) {
        return repository.findById(id)
                .map(usuario -> {
                    // Validação 1: Email único (exceto se for o mesmo email do próprio usuário)
                    Optional<Usuario> usuarioComMesmoEmail = repository.findByEmail(request.email());
                    if (usuarioComMesmoEmail.isPresent() && !usuarioComMesmoEmail.get().getIdUsuario().equals(id)) {
                        throw new DuplicateEntityException(
                                "Já existe outro usuário cadastrado com o email: " + request.email()
                        );
                    }

                    // Validação 2: Não permitir duplicata completa com outro usuário
                    Optional<Usuario> usuarioExistente = repository.findByAllFields(
                            request.nome(),
                            request.email(),
                            request.areaAtuacao(),
                            request.nivelCarreira()
                    );

                    if (usuarioExistente.isPresent() && !usuarioExistente.get().getIdUsuario().equals(id)) {
                        throw new DuplicateEntityException(
                                "Já existe outro usuário cadastrado com estes dados: " + request.nome()
                        );
                    }

                    // Atualiza os campos
                    usuario.setNome(request.nome());
                    usuario.setEmail(request.email());
                    usuario.setAreaAtuacao(request.areaAtuacao());
                    usuario.setNivelCarreira(request.nivelCarreira());
                    usuario.setDataCadastro(request.dataCadastro());

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
}