package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Matricula;
import com.fiap.globalsolution.model.Usuario;
import com.fiap.globalsolution.model.Trilha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository para a entidade Matricula
 * Fornece operações CRUD e queries customizadas
 */
@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    /**
     * Verifica se já existe uma matrícula com todos os dados iguais
     * (usado para evitar duplicatas completas)
     */
    @Query("SELECT m FROM Matricula m WHERE m.usuario = :usuario " +
            "AND m.trilha = :trilha " +
            "AND (m.dataInscricao = :dataInscricao OR (m.dataInscricao IS NULL AND :dataInscricao IS NULL)) " +
            "AND m.status = :status")
    Optional<Matricula> findByAllFields(
            @Param("usuario") Usuario usuario,
            @Param("trilha") Trilha trilha,
            @Param("dataInscricao") LocalDate dataInscricao,
            @Param("status") String status
    );

    /**
     * Verifica se já existe uma matrícula ATIVA do usuário na mesma trilha
     * (regra de negócio: não permitir matrícula duplicada na mesma trilha)
     */
    @Query("SELECT m FROM Matricula m WHERE m.usuario = :usuario " +
            "AND m.trilha = :trilha " +
            "AND m.status = 'EM_ANDAMENTO'")
    Optional<Matricula> findMatriculaAtivaByUsuarioAndTrilha(
            @Param("usuario") Usuario usuario,
            @Param("trilha") Trilha trilha
    );

    /**
     * Busca todas as matrículas de um usuário
     */
    @Query("SELECT m FROM Matricula m WHERE m.usuario = :usuario")
    List<Matricula> findByUsuario(@Param("usuario") Usuario usuario);

    /**
     * Busca todas as matrículas de uma trilha
     */
    @Query("SELECT m FROM Matricula m WHERE m.trilha = :trilha")
    List<Matricula> findByTrilha(@Param("trilha") Trilha trilha);

    /**
     * Busca matrículas por status
     */
    @Query("SELECT m FROM Matricula m WHERE m.status = :status")
    List<Matricula> findByStatus(@Param("status") String status);

    /**
     * Busca matrículas de um usuário por status
     */
    @Query("SELECT m FROM Matricula m WHERE m.usuario = :usuario AND m.status = :status")
    List<Matricula> findByUsuarioAndStatus(
            @Param("usuario") Usuario usuario,
            @Param("status") String status
    );

    /**
     * Conta quantas matrículas ativas um usuário possui
     */
    @Query("SELECT COUNT(m) FROM Matricula m WHERE m.usuario = :usuario AND m.status = 'EM_ANDAMENTO'")
    Long countMatriculasAtivasByUsuario(@Param("usuario") Usuario usuario);

    /**
     * Conta quantas matrículas uma trilha possui
     */
    @Query("SELECT COUNT(m) FROM Matricula m WHERE m.trilha = :trilha")
    Long countMatriculasByTrilha(@Param("trilha") Trilha trilha);
}