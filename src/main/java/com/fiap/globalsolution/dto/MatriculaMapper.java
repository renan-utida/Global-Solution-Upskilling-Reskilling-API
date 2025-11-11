package com.fiap.globalsolution.dto;

import com.fiap.globalsolution.model.Matricula;
import com.fiap.globalsolution.model.Usuario;
import com.fiap.globalsolution.model.Trilha;

/**
 * Mapper para conversão entre Matricula e seus DTOs
 */
public class MatriculaMapper {

    /**
     * Converte MatriculaRequest para entidade Matricula
     * Nota: Usuario e Trilha devem ser setados separadamente no Service
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
        UsuarioResponse usuarioResponse = UsuarioMapper.toResponse(matricula.getUsuario());
        TrilhaResponse trilhaResponse = TrilhaMapper.toResponseWithoutCompetencias(matricula.getTrilha());

        return new MatriculaResponse(
                matricula.getIdMatricula(),
                usuarioResponse,
                trilhaResponse,
                matricula.getDataInscricao(),
                matricula.getStatus()
        );
    }

    /**
     * Atualiza uma entidade Matricula existente com dados do MatriculaRequest
     */
    public static void updateEntityFromRequest(Matricula matricula, MatriculaRequest request,
                                               Usuario usuario, Trilha trilha) {
        matricula.setUsuario(usuario);
        matricula.setTrilha(trilha);
        matricula.setDataInscricao(request.dataInscricao());
        matricula.setStatus(request.status());
    }
}