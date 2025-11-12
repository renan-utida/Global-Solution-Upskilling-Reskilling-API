package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Trilha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para a entidade Trilha
 * Fornece operações CRUD e queries customizadas
 */
@Repository
public interface TrilhaRepository extends JpaRepository<Trilha, Long> {

    /**
     * Verifica se já existe uma trilha com todos os dados iguais
     * (usado para evitar duplicatas completas)
     */
    @Query("SELECT t FROM Trilha t WHERE t.nome = :nome " +
            "AND (t.descricao = :descricao OR (t.descricao IS NULL AND :descricao IS NULL)) " +
            "AND t.nivel = :nivel " +
            "AND t.cargaHoraria = :cargaHoraria " +
            "AND (t.focoPrincipal = :focoPrincipal OR (t.focoPrincipal IS NULL AND :focoPrincipal IS NULL))")
    Optional<Trilha> findByAllFields(
            @Param("nome") String nome,
            @Param("descricao") String descricao,
            @Param("nivel") String nivel,
            @Param("cargaHoraria") Integer cargaHoraria,
            @Param("focoPrincipal") String focoPrincipal
    );

    /**
     * Busca trilhas por nível (INICIANTE, INTERMEDIARIO, AVANCADO)
     */
    @Query("SELECT t FROM Trilha t WHERE t.nivel = :nivel")
    List<Trilha> findByNivel(@Param("nivel") String nivel);

    /**
     * Busca trilhas por foco principal (IA, Dados, Soft Skills, etc)
     */
    @Query("SELECT t FROM Trilha t WHERE LOWER(t.focoPrincipal) LIKE LOWER(CONCAT('%', :focoPrincipal, '%'))")
    List<Trilha> findByFocoPrincipal(@Param("focoPrincipal") String focoPrincipal);

    /**
     * Busca trilhas com carga horária mínima
     */
    @Query("SELECT t FROM Trilha t WHERE t.cargaHoraria >= :cargaHorariaMinima")
    List<Trilha> findByCargaHorariaMinima(@Param("cargaHorariaMinima") Integer cargaHorariaMinima);
}