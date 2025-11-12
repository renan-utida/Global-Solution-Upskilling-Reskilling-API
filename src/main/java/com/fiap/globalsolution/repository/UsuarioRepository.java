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
     * Busca um usuário pelo email (útil para validação de duplicatas)
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica se já existe um usuário com todos os dados iguais
     * (usado para evitar duplicatas completas)
     */
    @Query("SELECT u FROM Usuario u WHERE u.nome = :nome " +
            "AND u.email = :email " +
            "AND (u.areaAtuacao = :areaAtuacao OR (u.areaAtuacao IS NULL AND :areaAtuacao IS NULL)) " +
            "AND (u.nivelCarreira = :nivelCarreira OR (u.nivelCarreira IS NULL AND :nivelCarreira IS NULL))")
    Optional<Usuario> findByAllFields(
            @Param("nome") String nome,
            @Param("email") String email,
            @Param("areaAtuacao") String areaAtuacao,
            @Param("nivelCarreira") String nivelCarreira
    );

    /**
     * Busca usuários por área de atuação
     */
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.areaAtuacao) LIKE LOWER(CONCAT('%', :areaAtuacao, '%'))")
    Optional<Usuario> findByAreaAtuacao(@Param("areaAtuacao") String areaAtuacao);

    /**
     * Busca usuários por nível de carreira
     */
    @Query("SELECT u FROM Usuario u WHERE u.nivelCarreira = :nivelCarreira")
    Optional<Usuario> findByNivelCarreira(@Param("nivelCarreira") String nivelCarreira);
}