package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Trilha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para a entidade Trilha
 * Fornece operações CRUD e queries customizadas
 */
@Repository
public interface TrilhaRepository extends JpaRepository<Trilha, Long> {

    /**
     * Busca trilhas por nível
     * @param nivel Nível da trilha (INICIANTE, INTERMEDIARIO, AVANCADO)
     * @return Lista de trilhas
     */
    List<Trilha> findByNivel(String nivel);

    /**
     * Busca trilhas por foco principal
     * @param focoPrincipal Foco principal da trilha
     * @return Lista de trilhas
     */
    @Query("SELECT t FROM Trilha t WHERE LOWER(t.focoPrincipal) LIKE LOWER(CONCAT('%', :foco, '%'))")
    List<Trilha> findByFocoPrincipalContaining(@Param("foco") String focoPrincipal);

    /**
     * Busca trilhas com carga horária menor ou igual ao valor informado
     * @param maxHoras Carga horária máxima
     * @return Lista de trilhas
     */
    List<Trilha> findByCargaHorariaLessThanEqual(Integer maxHoras);

    /**
     * Busca trilhas por nome (busca parcial, case insensitive)
     * @param nome Nome da trilha
     * @return Lista de trilhas
     */
    @Query("SELECT t FROM Trilha t WHERE LOWER(t.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Trilha> findByNomeContaining(@Param("nome") String nome);
}