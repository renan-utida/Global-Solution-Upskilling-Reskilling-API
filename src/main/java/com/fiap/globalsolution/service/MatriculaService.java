package com.fiap.globalsolution.service;

import com.fiap.globalsolution.dto.MatriculaMapper;
import com.fiap.globalsolution.dto.MatriculaRequest;
import com.fiap.globalsolution.dto.MatriculaResponse;
import com.fiap.globalsolution.exception.MatriculaNaoEncontradaException;
import com.fiap.globalsolution.model.Matricula;
import com.fiap.globalsolution.model.Usuario;
import com.fiap.globalsolution.model.Trilha;
import com.fiap.globalsolution.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service para gerenciamento de Matriculas
 * Contém regras de negócio e validações
 */
@Service
@Transactional
public class MatriculaService {

    private final MatriculaRepository repository;
    private final UsuarioService usuarioService;
    private final TrilhaService trilhaService;

    public MatriculaService(MatriculaRepository repository,
                            UsuarioService usuarioService,
                            TrilhaService trilhaService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.trilhaService = trilhaService;
    }

    /**
     * Lista todas as matrículas
     */
    public List<MatriculaResponse> findAll() {
        return repository.findAll().stream()
                .map(MatriculaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma matrícula por ID
     */
    public Optional<MatriculaResponse> findById(Long id) {
        return repository.findById(id)
                .map(MatriculaMapper::toResponse);
    }

    /**
     * Busca todas as matrículas de um usuário
     */
    public List<MatriculaResponse> findByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(MatriculaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca todas as matrículas de uma trilha
     */
    public List<MatriculaResponse> findByTrilhaId(Long trilhaId) {
        return repository.findByTrilhaId(trilhaId).stream()
                .map(MatriculaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca matrículas por status
     */
    public List<MatriculaResponse> findByStatus(String status) {
        return repository.findByStatus(status).stream()
                .map(MatriculaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova matrícula
     * Validações:
     * - Verifica se usuário existe
     * - Verifica se trilha existe
     * - Verifica se usuário já está matriculado na trilha
     */
    public MatriculaResponse create(MatriculaRequest request) {
        // Busca usuário e trilha (lança exceção se não existir)
        Usuario usuario = usuarioService.getUsuarioEntityById(request.usuarioId());
        Trilha trilha = trilhaService.getTrilhaEntityById(request.trilhaId());

        // Validação: Verifica se já existe matrícula ativa do usuário nesta trilha
        if (repository.existsByUsuarioIdAndTrilhaId(request.usuarioId(), request.trilhaId())) {
            throw new IllegalArgumentException(
                    "O usuário " + usuario.getNome() +
                            " já está matriculado na trilha " + trilha.getNome()
            );
        }

        Matricula matricula = MatriculaMapper.toEntity(request, usuario, trilha);
        Matricula saved = repository.save(matricula);
        return MatriculaMapper.toResponse(saved);
    }

    /**
     * Atualiza uma matrícula existente
     * Permite atualizar status, data, ou até trocar de trilha
     */
    public Optional<MatriculaResponse> update(Long id, MatriculaRequest request) {
        return repository.findById(id)
                .map(matricula -> {
                    // Busca usuário e trilha (lança exceção se não existir)
                    Usuario usuario = usuarioService.getUsuarioEntityById(request.usuarioId());
                    Trilha trilha = trilhaService.getTrilhaEntityById(request.trilhaId());

                    // Validação: Se estiver trocando de trilha, verifica duplicata
                    if (!matricula.getTrilha().getIdTrilha().equals(request.trilhaId())) {
                        if (repository.existsByUsuarioIdAndTrilhaId(request.usuarioId(), request.trilhaId())) {
                            throw new IllegalArgumentException(
                                    "O usuário " + usuario.getNome() +
                                            " já está matriculado na trilha " + trilha.getNome()
                            );
                        }
                    }

                    MatriculaMapper.updateEntityFromRequest(matricula, request, usuario, trilha);
                    Matricula updated = repository.save(matricula);
                    return MatriculaMapper.toResponse(updated);
                });
    }

    /**
     * Atualiza apenas o status de uma matrícula
     */
    public Optional<MatriculaResponse> updateStatus(Long id, String novoStatus) {
        return repository.findById(id)
                .map(matricula -> {
                    matricula.setStatus(novoStatus);
                    Matricula updated = repository.save(matricula);
                    return MatriculaMapper.toResponse(updated);
                });
    }

    /**
     * Deleta uma matrícula por ID
     */
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(matricula -> {
                    repository.delete(matricula);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Verifica se uma matrícula existe por ID
     */
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    /**
     * Busca entidade Matricula por ID (para uso interno)
     * Lança exceção se não encontrado
     */
    public Matricula getMatriculaEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MatriculaNaoEncontradaException(
                        "Matrícula não encontrada com ID: " + id
                ));
    }
}