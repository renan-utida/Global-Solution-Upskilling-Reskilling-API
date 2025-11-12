package com.fiap.globalsolution.service;

import com.fiap.globalsolution.dto.MatriculaMapper;
import com.fiap.globalsolution.dto.MatriculaRequest;
import com.fiap.globalsolution.dto.MatriculaResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.exception.TrilhaNaoEncontradaException;
import com.fiap.globalsolution.exception.UsuarioNaoEncontradoException;
import com.fiap.globalsolution.model.Matricula;
import com.fiap.globalsolution.model.Trilha;
import com.fiap.globalsolution.model.Usuario;
import com.fiap.globalsolution.repository.MatriculaRepository;
import com.fiap.globalsolution.repository.TrilhaRepository;
import com.fiap.globalsolution.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service para regras de negócio de Matricula
 */
@Service
@Transactional
public class MatriculaService {

    private final MatriculaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final TrilhaRepository trilhaRepository;

    public MatriculaService(MatriculaRepository repository,
                            UsuarioRepository usuarioRepository,
                            TrilhaRepository trilhaRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.trilhaRepository = trilhaRepository;
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
     * Cria uma nova matrícula
     * Validações:
     * - Usuario deve existir
     * - Trilha deve existir
     * - Não permite matrícula duplicada (mesmo usuário na mesma trilha com status EM_ANDAMENTO)
     * - Não permite cadastro de matrícula com todos os dados iguais
     */
    public MatriculaResponse create(MatriculaRequest request) {
        // Validação 1: Verifica se o usuário existe
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        "Usuário com ID " + request.usuarioId() + " não encontrado"
                ));

        // Validação 2: Verifica se a trilha existe
        Trilha trilha = trilhaRepository.findById(request.trilhaId())
                .orElseThrow(() -> new TrilhaNaoEncontradaException(
                        "Trilha com ID " + request.trilhaId() + " não encontrada"
                ));

        // Validação 3: Não permitir matrícula duplicada (usuário já matriculado na mesma trilha com status EM_ANDAMENTO)
        Optional<Matricula> matriculaAtiva = repository.findMatriculaAtivaByUsuarioAndTrilha(usuario, trilha);
        if (matriculaAtiva.isPresent()) {
            throw new DuplicateEntityException(
                    "Usuário " + usuario.getNome() + " já está matriculado na trilha " + trilha.getNome() +
                            " com status EM_ANDAMENTO"
            );
        }

        // Validação 4: Não permitir duplicata completa
        Optional<Matricula> matriculaExistente = repository.findByAllFields(
                usuario,
                trilha,
                request.dataInscricao(),
                request.status()
        );

        if (matriculaExistente.isPresent()) {
            throw new DuplicateEntityException(
                    "Já existe uma matrícula cadastrada com estes dados"
            );
        }

        // Se passou nas validações, cria a matrícula
        Matricula matricula = MatriculaMapper.toEntity(request, usuario, trilha);
        Matricula saved = repository.save(matricula);
        return MatriculaMapper.toResponse(saved);
    }

    /**
     * Atualiza uma matrícula existente
     * Validações:
     * - Usuario deve existir
     * - Trilha deve existir
     * - Não permite atualizar para matrícula duplicada
     * - Não permite atualizar para dados idênticos a outra matrícula
     */
    public Optional<MatriculaResponse> update(Long id, MatriculaRequest request) {
        return repository.findById(id)
                .map(matricula -> {
                    // Validação 1: Verifica se o usuário existe
                    Usuario usuario = usuarioRepository.findById(request.usuarioId())
                            .orElseThrow(() -> new UsuarioNaoEncontradoException(
                                    "Usuário com ID " + request.usuarioId() + " não encontrado"
                            ));

                    // Validação 2: Verifica se a trilha existe
                    Trilha trilha = trilhaRepository.findById(request.trilhaId())
                            .orElseThrow(() -> new TrilhaNaoEncontradaException(
                                    "Trilha com ID " + request.trilhaId() + " não encontrada"
                            ));

                    // Validação 3: Se status for EM_ANDAMENTO, não permitir duplicata (mesmo usuário na mesma trilha)
                    if ("EM_ANDAMENTO".equals(request.status())) {
                        Optional<Matricula> matriculaAtiva = repository.findMatriculaAtivaByUsuarioAndTrilha(usuario, trilha);
                        if (matriculaAtiva.isPresent() && !matriculaAtiva.get().getIdMatricula().equals(id)) {
                            throw new DuplicateEntityException(
                                    "Usuário " + usuario.getNome() + " já está matriculado na trilha " + trilha.getNome() +
                                            " com status EM_ANDAMENTO"
                            );
                        }
                    }

                    // Validação 4: Não permitir duplicata completa com outra matrícula
                    Optional<Matricula> matriculaExistente = repository.findByAllFields(
                            usuario,
                            trilha,
                            request.dataInscricao(),
                            request.status()
                    );

                    if (matriculaExistente.isPresent() && !matriculaExistente.get().getIdMatricula().equals(id)) {
                        throw new DuplicateEntityException(
                                "Já existe outra matrícula cadastrada com estes dados"
                        );
                    }

                    // Atualiza os campos
                    matricula.setUsuario(usuario);
                    matricula.setTrilha(trilha);
                    matricula.setDataInscricao(request.dataInscricao());
                    matricula.setStatus(request.status());

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
}