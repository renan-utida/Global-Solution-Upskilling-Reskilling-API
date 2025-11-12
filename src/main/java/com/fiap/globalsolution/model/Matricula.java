package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidade Matricula - Representa a inscrição de um usuário em uma trilha de aprendizagem
 * Relacionamento: Usuario 1:N Matricula N:1 Trilha
 */
@Entity
@Table(name = "matriculas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Long idMatricula;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "trilha_id", nullable = false)
    private Trilha trilha;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDate dataInscricao;

    @Column(name = "status", nullable = false, length = 50)
    private String status; // EM_ANDAMENTO, CONCLUIDA, CANCELADA

    // Construtor sem ID (útil para criação)
    public Matricula(Usuario usuario, Trilha trilha, LocalDate dataInscricao, String status) {
        this.usuario = usuario;
        this.trilha = trilha;
        this.dataInscricao = dataInscricao;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "idMatricula=" + idMatricula +
                ", usuario=" + (usuario != null ? usuario.getNome() : "null") +
                ", trilha=" + (trilha != null ? trilha.getNome() : "null") +
                ", dataInscricao=" + dataInscricao +
                ", status='" + status + '\'' +
                '}';
    }
}