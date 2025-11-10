package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidade Usuario - Representa um profissional/aluno na plataforma de Upskilling/Reskilling
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "area_atuacao", length = 100)
    private String areaAtuacao;

    @Column(name = "nivel_carreira", length = 50)
    private String nivelCarreira;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    // Construtor sem ID (útil para criação)
    public Usuario(String nome, String email, String areaAtuacao, String nivelCarreira, LocalDate dataCadastro) {
        this.nome = nome;
        this.email = email;
        this.areaAtuacao = areaAtuacao;
        this.nivelCarreira = nivelCarreira;
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", areaAtuacao='" + areaAtuacao + '\'' +
                ", nivelCarreira='" + nivelCarreira + '\'' +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}