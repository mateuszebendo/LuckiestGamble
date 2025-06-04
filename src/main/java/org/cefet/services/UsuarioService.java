package org.cefet.services;

import org.cefet.config.ConnectionFactory;
import org.cefet.dao.UsuarioDAO;
import org.cefet.dtos.CreateUsuarioDto;
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.models.UsuarioModel;

import java.sql.SQLException;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() throws SQLException {
        usuarioDAO = new UsuarioDAO(ConnectionFactory.getConnection());
    }

    public ResponseUsuarioDto SaveUsuario (CreateUsuarioDto dto) throws Exception {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setDataNascimento(dto.getDataNascimento());

        var usuarioResponse = new ResponseUsuarioDto(usuarioDAO.save(usuario));

        return usuarioResponse;
    }

}
