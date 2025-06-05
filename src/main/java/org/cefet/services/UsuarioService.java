package org.cefet.services;

import org.cefet.config.ConnectionFactory;
import org.cefet.dao.UsuarioDAO;
import org.cefet.dtos.usuario.CreateUsuarioDto;
import org.cefet.dtos.usuario.LoginUsuarioDto;
import org.cefet.dtos.usuario.ResponseUsuarioDto;
import org.cefet.enums.TipoUsuario;
import org.cefet.models.UsuarioModel;

import java.sql.SQLException;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() throws SQLException {
        usuarioDAO = new UsuarioDAO(ConnectionFactory.getConnection());
    }

    public ResponseUsuarioDto saveUsuario (CreateUsuarioDto dto) throws Exception {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setSaldo(0);
        usuario.setTipoUsuario(TipoUsuario.COMUM);

        var usuarioResponse = new ResponseUsuarioDto(usuarioDAO.save(usuario));

        return usuarioResponse;
    }

    public ResponseUsuarioDto login(LoginUsuarioDto usuario) throws Exception {
        UsuarioModel usuarioModel = usuarioDAO.login(usuario.getUsuario(), usuario.getSenha());

        return new ResponseUsuarioDto(usuarioModel);
    }

}
