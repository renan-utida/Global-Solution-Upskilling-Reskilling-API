package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidade Trilha - Representa uma trilha de aprendizagem/curso na plataforma
 */
@Entity
@Table(name = "trilhas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trilha")
    private Long idTrilha;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, length = 50)
    private String nivel; // INICIANTE, INTERMEDIARIO, AVANCADO

    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @Column(name = "foco_principal", length = 100)
    private String focoPrincipal;

    // Relacionamento N:N com Competencia
    @ManyToMany
    @JoinTable(
            name = "trilha_competencia",
            joinColumns = @JoinColumn(name = "trilha_id"),
            inverseJoinColumns = @JoinColumn(name = "competencia_id")
    )
    private Set<Competencia> competencias = new HashSet<>();

    // Construtor sem ID (útil para criação)
    public Trilha(String nome, String descricao, String nivel, Integer cargaHoraria, String focoPrincipal) {
        this.nome = nome;
        this.descricao = descricao;
        this.nivel = nivel;
        this.cargaHoraria = cargaHoraria;
        this.focoPrincipal = focoPrincipal;
    }

    @Override
    public String toString() {
        return "Trilha{" +
                "idTrilha=" + idTrilha +
                ", nome='" + nome + '\'' +
                ", nivel='" + nivel + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", focoPrincipal='" + focoPrincipal + '\'' +
                '}';
    }
}