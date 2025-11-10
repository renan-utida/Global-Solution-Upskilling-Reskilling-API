package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para a entidade Usuario
 * Fornece operações CRUD e queries customizadas
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário por email
     * @param email Email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica se já existe um usuário com o email informado
     * @param email Email a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);

    /**
     * Busca usuários por área de atuação
     * @param areaAtuacao Área de atuação
     * @return Lista de usuários
     */
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.areaAtuacao) LIKE LOWER(CONCAT('%', :areaAtuacao, '%'))")
    java.util.List<Usuario> findByAreaAtuacaoContaining(@Param("areaAtuacao") String areaAtuacao);
}