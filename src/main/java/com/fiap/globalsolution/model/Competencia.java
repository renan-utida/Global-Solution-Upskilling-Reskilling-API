package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidade Competencia - Representa uma competência do futuro (IA, Dados, Soft Skills, etc)
 */
@Entity
@Table(name = "competencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_competencia")
    private Long idCompetencia;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100)
    private String categoria; // Tecnológica, Humana, Gestão, etc

    @Column(columnDefinition = "TEXT")
    private String descricao;

    // Relacionamento N:N com Trilha (lado inverso)
    @ManyToMany(mappedBy = "competencias")
    private Set<Trilha> trilhas = new HashSet<>();

    // Construtor sem ID (útil para criação)
    public Competencia(String nome, String categoria, String descricao) {
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Competencia{" +
                "idCompetencia=" + idCompetencia +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}