package com.fiap.globalsolution.service;

import com.fiap.globalsolution.dto.TrilhaMapper;
import com.fiap.globalsolution.dto.TrilhaRequest;
import com.fiap.globalsolution.dto.TrilhaResponse;
import com.fiap.globalsolution.exception.TrilhaNaoEncontradaException;
import com.fiap.globalsolution.model.Trilha;
import com.fiap.globalsolution.repository.TrilhaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service para gerenciamento de Trilhas
 * Contém regras de negócio e validações
 */
@Service
@Transactional
public class TrilhaService {

    private final TrilhaRepository repository;
    private final CompetenciaRepository competenciaRepository;

    public TrilhaService(TrilhaRepository repository, CompetenciaRepository competenciaRepository) {
        this.repository = repository;
        this.competenciaRepository = competenciaRepository;
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
     * Busca trilhas por nível
     */
    public List<TrilhaResponse> findByNivel(String nivel) {
        return repository.findByNivel(nivel).stream()
                .map(TrilhaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca trilhas por foco principal
     */
    public List<TrilhaResponse> findByFocoPrincipal(String foco) {
        return repository.findByFocoPrincipalContaining(foco).stream()
                .map(TrilhaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca trilhas por nome
     */
    public List<TrilhaResponse> findByNome(String nome) {
        return repository.findByNomeContaining(nome).stream()
                .map(TrilhaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova trilha
     * Validação: carga horária positiva
     */
    public TrilhaResponse create(TrilhaRequest request) {
        // Validação já feita pelo Bean Validation, mas pode adicionar regras extras
        Trilha trilha = TrilhaMapper.toEntity(request);

        // Associar competências se fornecidas
        if (request.competenciasIds() != null && !request.competenciasIds().isEmpty()) {
            Set<Competencia> competencias = new HashSet<>(
                    competenciaRepository.findAllById(request.competenciasIds())
            );
            trilha.setCompetencias(competencias);
        }

        Trilha saved = repository.save(trilha);
        return TrilhaMapper.toResponse(saved);
    }

    /**
     * Atualiza uma trilha existente
     */
    public Optional<TrilhaResponse> update(Long id, TrilhaRequest request) {
        return repository.findById(id)
                .map(trilha -> {
                    TrilhaMapper.updateEntityFromRequest(trilha, request);

                    // Atualizar competências
                    if (request.competenciasIds() != null) {
                        Set<Competencia> competencias = new HashSet<>(
                                competenciaRepository.findAllById(request.competenciasIds())
                        );
                        trilha.setCompetencias(competencias);
                    } else {
                        trilha.getCompetencias().clear();
                    }

                    Trilha updated = repository.save(trilha);
                    return TrilhaMapper.toResponse(updated);
                });
    }

    /**
     * Adiciona uma competência a uma trilha
     */
    public Optional<TrilhaResponse> addCompetencia(Long trilhaId, Long competenciaId) {
        return repository.findById(trilhaId)
                .flatMap(trilha -> competenciaRepository.findById(competenciaId)
                        .map(competencia -> {
                            trilha.getCompetencias().add(competencia);
                            Trilha updated = repository.save(trilha);
                            return TrilhaMapper.toResponse(updated);
                        }));
    }

    /**
     * Remove uma competência de uma trilha
     */
    public Optional<TrilhaResponse> removeCompetencia(Long trilhaId, Long competenciaId) {
        return repository.findById(trilhaId)
                .flatMap(trilha -> competenciaRepository.findById(competenciaId)
                        .map(competencia -> {
                            trilha.getCompetencias().remove(competencia);
                            Trilha updated = repository.save(trilha);
                            return TrilhaMapper.toResponse(updated);
                        }));
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

    /**
     * Verifica se uma trilha existe por ID
     */
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    /**
     * Busca entidade Trilha por ID (para uso interno)
     * Lança exceção se não encontrado
     */
    public Trilha getTrilhaEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TrilhaNaoEncontradaException(
                        "Trilha não encontrada com ID: " + id
                ));
    }
}