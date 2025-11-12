package com.fiap.globalsolution.dto;

import com.fiap.globalsolution.model.Matricula;
import com.fiap.globalsolution.model.Usuario;
import com.fiap.globalsolution.model.Trilha;

/**
 * Mapper para conversão entre Matricula e DTOs
 */
public class MatriculaMapper {

    /**
     * Converte MatriculaRequest para entidade Matricula
     * Nota: Usuario e Trilha devem ser buscados no banco antes de chamar este método
     */
    public static Matricula toEntity(MatriculaRequest request, Usuario usuario, Trilha trilha) {
        Matricula matricula = new Matricula();
        matricula.setUsuario(usuario);
        matricula.setTrilha(trilha);
        matricula.setDataInscricao(request.dataInscricao());
        matricula.setStatus(request.status());
        return matricula;
    }

    /**
     * Converte entidade Matricula para MatriculaResponse
     */
    public static MatriculaResponse toResponse(Matricula matricula) {
        return new MatriculaResponse(
                matricula.getIdMatricula(),
                UsuarioMapper.toResponse(matricula.getUsuario()),
                TrilhaMapper.toResponse(matricula.getTrilha()),
                matricula.getDataInscricao(),
                matricula.getStatus()
        );
    }
}