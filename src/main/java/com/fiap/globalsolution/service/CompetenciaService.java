package com.fiap.globalsolution.service;

import com.fiap.globalsolution.dto.CompetenciaMapper;
import com.fiap.globalsolution.dto.CompetenciaRequest;
import com.fiap.globalsolution.dto.CompetenciaResponse;
import com.fiap.globalsolution.exception.CompetenciaNaoEncontradaException;
import com.fiap.globalsolution.repository.CompetenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service para gerenciamento de Competencias
 * Contém regras de negócio e validações
 */
@Service
@Transactional
public class CompetenciaService {

    private final CompetenciaRepository repository;

    public CompetenciaService(CompetenciaRepository repository) {
        this.repository = repository;
    }

    /**
     * Lista todas as competências
     */
    public List<CompetenciaResponse> findAll() {
        return repository.findAll().stream()
                .map(CompetenciaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma competência por ID
     */
    public Optional<CompetenciaResponse> findById(Long id) {
        return repository.findById(id)
                .map(CompetenciaMapper::toResponse);
    }

    /**
     * Busca competências por categoria
     */
    public List<CompetenciaResponse> findByCategoria(String categoria) {
        return repository.findByCategoria(categoria).stream()
                .map(CompetenciaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca competências por nome
     */
    public List<CompetenciaResponse> findByNome(String nome) {
        return repository.findByNomeContaining(nome).stream()
                .map(CompetenciaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova competência
     * Validação: verifica se nome já existe
     */
    public CompetenciaResponse create(CompetenciaRequest request) {
        // Validação: Nome único
        if (repository.existsByNome(request.nome())) {
            throw new IllegalArgumentException(
                    "Já existe uma competência cadastrada com o nome: " + request.nome()
            );
        }

        Competencia competencia = CompetenciaMapper.toEntity(request);
        Competencia saved = repository.save(competencia);
        return CompetenciaMapper.toResponse(saved);
    }

    /**
     * Atualiza uma competência existente
     * Validação: verifica se nome já existe (exceto para a própria competência)
     */
    public Optional<CompetenciaResponse> update(Long id, CompetenciaRequest request) {
        return repository.findById(id)
                .map(competencia -> {
                    // Validação: Nome único (permitindo manter o próprio nome)
                    if (!competencia.getNome().equals(request.nome()) &&
                            repository.existsByNome(request.nome())) {
                        throw new IllegalArgumentException(
                                "Já existe outra competência cadastrada com o nome: " + request.nome()
                        );
                    }

                    CompetenciaMapper.updateEntityFromRequest(competencia, request);
                    Competencia updated = repository.save(competencia);
                    return CompetenciaMapper.toResponse(updated);
                });
    }

    /**
     * Deleta uma competência por ID
     */
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(competencia -> {
                    repository.delete(competencia);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Verifica se uma competência existe por ID
     */
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    /**
     * Busca entidade Competencia por ID (para uso interno)
     * Lança exceção se não encontrado
     */
    public Competencia getCompetenciaEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CompetenciaNaoEncontradaException(
                        "Competência não encontrada com ID: " + id
                ));
    }
}