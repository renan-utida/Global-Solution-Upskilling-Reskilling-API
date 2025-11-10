package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para a entidade Competencia
 * Fornece operações CRUD e queries customizadas
 */
@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {

    /**
     * Busca competências por categoria
     * @param categoria Categoria da competência (Tecnológica, Humana, Gestão, etc)
     * @return Lista de competências
     */
    List<Competencia> findByCategoria(String categoria);

    /**
     * Busca competências por nome (busca parcial, case insensitive)
     * @param nome Nome da competência
     * @return Lista de competências
     */
    @Query("SELECT c FROM Competencia c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Competencia> findByNomeContaining(@Param("nome") String nome);

    /**
     * Verifica se já existe uma competência com o nome informado
     * @param nome Nome da competência
     * @return true se existe, false caso contrário
     */
    boolean existsByNome(String nome);
}