package com.fiap.globalsolution.dto;

/**
 * Mapper para conversão entre Competencia e seus DTOs
 */
public class CompetenciaMapper {

    /**
     * Converte CompetenciaRequest para entidade Competencia
     */
    public static Competencia toEntity(CompetenciaRequest request) {
        Competencia competencia = new Competencia();
        competencia.setNome(request.nome());
        competencia.setCategoria(request.categoria());
        competencia.setDescricao(request.descricao());
        return competencia;
    }

    /**
     * Converte entidade Competencia para CompetenciaResponse
     */
    public static CompetenciaResponse toResponse(Competencia competencia) {
        return new CompetenciaResponse(
                competencia.getIdCompetencia(),
                competencia.getNome(),
                competencia.getCategoria(),
                competencia.getDescricao()
        );
    }

    /**
     * Atualiza uma entidade Competencia existente com dados do CompetenciaRequest
     */
    public static void updateEntityFromRequest(Competencia competencia, CompetenciaRequest request) {
        competencia.setNome(request.nome());
        competencia.setCategoria(request.categoria());
        competencia.setDescricao(request.descricao());
    }
}