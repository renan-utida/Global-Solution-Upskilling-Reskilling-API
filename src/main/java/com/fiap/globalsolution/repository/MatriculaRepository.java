package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Matricula;
import com.fiap.globalsolution.model.Usuario;
import com.fiap.globalsolution.model.Trilha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para a entidade Matricula
 * Fornece operações CRUD e queries customizadas
 */
@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    /**
     * Busca todas as matrículas de um usuário
     * @param usuario Usuário
     * @return Lista de matrículas
     */
    List<Matricula> findByUsuario(Usuario usuario);

    /**
     * Busca todas as matrículas de uma trilha
     * @param trilha Trilha
     * @return Lista de matrículas
     */
    List<Matricula> findByTrilha(Trilha trilha);

    /**
     * Busca matrículas por status
     * @param status Status da matrícula (EM_ANDAMENTO, CONCLUIDA, CANCELADA)
     * @return Lista de matrículas
     */
    List<Matricula> findByStatus(String status);

    /**
     * Busca matrículas de um usuário em uma trilha específica
     * @param usuario Usuário
     * @param trilha Trilha
     * @return Lista de matrículas
     */
    List<Matricula> findByUsuarioAndTrilha(Usuario usuario, Trilha trilha);

    /**
     * Verifica se um usuário já está matriculado em uma trilha específica
     * @param usuarioId ID do usuário
     * @param trilhaId ID da trilha
     * @return true se já existe matrícula, false caso contrário
     */
    @Query("SELECT COUNT(m) > 0 FROM Matricula m WHERE m.usuario.idUsuario = :usuarioId AND m.trilha.idTrilha = :trilhaId")
    boolean existsByUsuarioIdAndTrilhaId(@Param("usuarioId") Long usuarioId, @Param("trilhaId") Long trilhaId);

    /**
     * Busca matrículas de um usuário por ID
     * @param usuarioId ID do usuário
     * @return Lista de matrículas
     */
    @Query("SELECT m FROM Matricula m WHERE m.usuario.idUsuario = :usuarioId")
    List<Matricula> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    /**
     * Busca matrículas de uma trilha por ID
     * @param trilhaId ID da trilha
     * @return Lista de matrículas
     */
    @Query("SELECT m FROM Matricula m WHERE m.trilha.idTrilha = :trilhaId")
    List<Matricula> findByTrilhaId(@Param("trilhaId") Long trilhaId);
}