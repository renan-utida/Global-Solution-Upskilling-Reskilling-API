package com.fiap.globalsolution.service;

import com.fiap.globalsolution.dto.*;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.model.Trilha;
import com.fiap.globalsolution.repository.TrilhaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service para regras de negócio de Trilha
 */
@Service
@Transactional
public class TrilhaService {

    private final TrilhaRepository repository;

    public TrilhaService(TrilhaRepository repository) {
        this.repository = repository;
    }

    /**
     * Lista todas as trilhas
     */
    public List<TrilhaResponse> findAll() {
        return repository.findAll().stream()
                .map(TrilhaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma trilha por ID
     */
    public Optional<TrilhaResponse> findById(Long id) {
        return repository.findById(id)
                .map(TrilhaMapper::toResponse);
    }

    /**
     * Busca trilhas por nível (INICIANTE, INTERMEDIARIO, AVANCADO)
     */
    public List<TrilhaResponse> findByNivel(String nivel) {
        return repository.findByNivel(nivel).stream()
                .map(TrilhaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca trilhas por foco principal
     */
    public List<TrilhaResponse> findByFocoPrincipal(String focoPrincipal) {
        return repository.findByFocoPrincipal(focoPrincipal).stream()
                .map(TrilhaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova trilha
     * Validações:
     * - Não permite cadastro de trilha com todos os dados iguais
     */
    public TrilhaResponse create(TrilhaRequest request) {
        // Validação: Não permitir duplicata completa
        Optional<Trilha> trilhaExistente = repository.findByAllFields(
                request.nome(),
                request.descricao(),
                request.nivel(),
                request.cargaHoraria(),
                request.focoPrincipal()
        );

        if (trilhaExistente.isPresent()) {
            throw new DuplicateEntityException(
                    "Já existe uma trilha cadastrada com estes dados: " + request.nome()
            );
        }

        // Se passou na validação, cria a trilha
        Trilha trilha = TrilhaMapper.toEntity(request);
        Trilha saved = repository.save(trilha);
        return TrilhaMapper.toResponse(saved);
    }

    /**
     * Atualiza uma trilha existente
     * Validações:
     * - Não permite atualizar para dados idênticos a outra trilha
     */
    public Optional<TrilhaResponse> update(Long id, TrilhaRequest request) {
        return repository.findById(id)
                .map(trilha -> {
                    // Validação: Não permitir duplicata completa com outra trilha
                    Optional<Trilha> trilhaExistente = repository.findByAllFields(
                            request.nome(),
                            request.descricao(),
                            request.nivel(),
                            request.cargaHoraria(),
                            request.focoPrincipal()
                    );

                    if (trilhaExistente.isPresent() && !trilhaExistente.get().getIdTrilha().equals(id)) {
                        throw new DuplicateEntityException(
                                "Já existe outra trilha cadastrada com estes dados: " + request.nome()
                        );
                    }

                    // Atualiza os campos
                    trilha.setNome(request.nome());
                    trilha.setDescricao(request.descricao());
                    trilha.setNivel(request.nivel());
                    trilha.setCargaHoraria(request.cargaHoraria());
                    trilha.setFocoPrincipal(request.focoPrincipal());

                    Trilha updated = repository.save(trilha);
                    return TrilhaMapper.toResponse(updated);
                });
    }

    /**
     * Deleta uma trilha por ID
     */
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(trilha -> {
                    repository.delete(trilha);
                    return true;
                })
                .orElse(false);
    }
}