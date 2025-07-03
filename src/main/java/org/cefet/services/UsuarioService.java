package org.cefet.services;

import org.cefet.config.ConnectionFactory;
import org.cefet.dao.UsuarioDAO;
import org.cefet.dtos.CreateUsuarioDto;
import org.cefet.dtos.LoginUsuarioDto;
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.dtos.UpdateUsuarioDto;
import org.cefet.enums.TipoUsuario;
import org.cefet.models.UsuarioModel;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() throws SQLException {
        try {
            usuarioDAO = new UsuarioDAO(ConnectionFactory.getConnection());
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados para o serviço de usuário.", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado na inicialização do serviço de usuário.", e);
        }
    }

    public ResponseUsuarioDto saveUsuario(CreateUsuarioDto dto) throws Exception {
        try {
            UsuarioModel usuario = new UsuarioModel();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(dto.getSenha());
            usuario.setDataNascimento(dto.getDataNascimento());
            usuario.setSaldo(0);
            usuario.setTipoUsuario(TipoUsuario.COMUM);

            var usuarioResponse = new ResponseUsuarioDto(usuarioDAO.save(usuario));
            return usuarioResponse;
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao salvar usuário: " + e.getMessage());
            throw new SQLException("Erro de banco de dados ao criar a conta.", e);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar usuário: " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado ao criar a conta.", e);
        }
    }

    public ResponseUsuarioDto login(LoginUsuarioDto dto) throws Exception {
        try {
            UsuarioModel usuarioModel = usuarioDAO.login(dto.getUsuario(), dto.getSenha());

            if (usuarioModel == null) {
                throw new NoSuchElementException("Usuário ou senha inválidos.");
            }

            return new ResponseUsuarioDto(usuarioModel);
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados no login: " + e.getMessage());
            throw new SQLException("Erro de banco de dados ao tentar logar.", e);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Erro inesperado no login: " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado ao tentar logar.", e);
        }
    }

    public ResponseUsuarioDto updateUsuario(UpdateUsuarioDto dto) throws Exception {
        try {
            UsuarioModel usuario = new UsuarioModel();
            usuario.setUsuarioId(dto.getUsuarioId());
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(dto.getSenha());
            usuario.setDataNascimento(dto.getDataNascimento());
            usuario.setSaldo(dto.getSaldo());
            usuario.setTipoUsuario(dto.getTipoUsuario());

            var usuarioResponse = new ResponseUsuarioDto(usuarioDAO.save(usuario));
            return usuarioResponse;
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao atualizar usuário: " + e.getMessage());
            throw new SQLException("Erro de banco de dados ao atualizar as informações do usuário.", e);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao atualizar usuário: " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado ao atualizar as informações do usuário.", e);
        }
    }

    public List<ResponseUsuarioDto> getUsuarios() throws RuntimeException { // Pode lançar RuntimeException
        try {
            List<UsuarioModel> usuariosModel = usuarioDAO.findAll(); // Chama o método findAll do DAO

            return usuariosModel.stream()
                    .map(ResponseUsuarioDto::new)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao buscar todos os usuários: " + e.getMessage());
            throw new RuntimeException("Erro ao carregar a lista de usuários.", e);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao buscar todos os usuários: " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado ao carregar a lista de usuários.", e);
        }
    }

    public UsuarioModel getUsuarioByNome(String nomeUsuario) throws Exception {
        try {
            Optional<UsuarioModel> usuarioOptional = usuarioDAO.findByNome(nomeUsuario);

            return usuarioOptional.orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o nome: " + nomeUsuario));
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao buscar usuário por nome: " + e.getMessage());
            throw new SQLException("Erro de banco de dados ao validar o usuário pelo nome.", e);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Erro inesperado ao buscar usuário por nome: " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado ao buscar o usuário pelo nome.", e);
        }
    }
}