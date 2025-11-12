package com.fiap.globalsolution.dto;

import com.fiap.globalsolution.model.Usuario;

/**
 * Mapper para conversão entre Usuario e DTOs
 */
public class UsuarioMapper {

    /**
     * Converte UsuarioRequest para entidade Usuario
     */
    public static Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setAreaAtuacao(request.areaAtuacao());
        usuario.setNivelCarreira(request.nivelCarreira());
        usuario.setDataCadastro(request.dataCadastro());
        return usuario;
    }

    /**
     * Converte entidade Usuario para UsuarioResponse
     */
    public static UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getAreaAtuacao(),
                usuario.getNivelCarreira(),
                usuario.getDataCadastro()
        );
    }
}
