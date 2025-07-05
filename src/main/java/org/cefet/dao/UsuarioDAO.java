package org.cefet.dao;

import org.cefet.contracts.BaseRepositoryImpl;
import org.cefet.contracts.base.BaseRepository;
import org.cefet.dtos.FindUsuarioDto;
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.enums.TipoUsuario;
import org.cefet.models.UsuarioModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO extends BaseRepositoryImpl<UsuarioModel, Long> implements BaseRepository<UsuarioModel, Long> {

    public UsuarioDAO(Connection connection) {
        super("usuarios", "UsuarioId");
        this.connection = connection;
    }

    public UsuarioDAO(String tableName, String idColumnName, Connection connection) {
        super(tableName, idColumnName);
        this.connection = connection;
    }

    public UsuarioModel login(String nome, String senha) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM usuarios WHERE nome = ? AND senha = ?");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            stmt.setString(1, nome);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UsuarioModel usuario = mapResultSetToObject(rs);
                return usuario;
            }
        }

        return null;
    }

    public UsuarioModel changeSaldo(double novoValor, long usuarioId) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE usuarios SET saldo = saldo + ?, data_atualizacao = ? WHERE usuario_id = ?");
        UsuarioModel usuario;

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            stmt.setDouble(1, novoValor);
            stmt.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
            stmt.setLong(3, usuarioId);

            stmt.executeUpdate();
            usuario = findById(usuarioId).orElse(null);
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return usuario;
    }

    public Optional<UsuarioModel> findByNome(String nome) throws SQLException {
        String sql = "SELECT usuario_id, nome, email, senha, data_nascimento, saldo, tipo_usuario, data_criacao, data_atualizacao FROM usuarios WHERE nome = ?";
        UsuarioModel usuario = null; // Inicializa como null
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = mapResultSetToObject(rs);
                }
            }
        }
        // Retorna um Optional.of(usuario) se encontrado, ou Optional.empty() se null
        return Optional.ofNullable(usuario); // Usa ofNullable para lidar com 'usuario' sendo null
    }

    public List<UsuarioModel> findUsers(FindUsuarioDto findUsuarioDto) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT usuario_id, nome, email, senha, data_nascimento, saldo, tipo_usuario, data_criacao, data_atualizacao FROM usuarios WHERE 1=1"); // Inicia com WHERE 1=1 para facilitar a concatenação
        List<UsuarioModel> usuarios = new ArrayList<>();

        String nome = findUsuarioDto.getNome();
        String email = findUsuarioDto.getEmail();
        java.util.Date dataNascimento = findUsuarioDto.getDataNascimento();
        TipoUsuario tipoUsuario = findUsuarioDto.getTipoUsuario();

        if (nome != null && !nome.isEmpty()) {
            sql.append(" AND nome LIKE ?");
        }

        if (email != null && !email.isEmpty()) {
            sql.append(" AND email LIKE ?");
        }

        if (dataNascimento != null) {
            sql.append(" AND data_nascimento = ?");
        }

        if (tipoUsuario != null) {
            sql.append(" AND tipo_usuario = ?");
        }

        int index = 1;

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            if (nome != null && !nome.isEmpty()) {
                stmt.setString(index, "%" + nome + "%");
                index++;
            }

            if (email != null && !email.isEmpty()) {
                stmt.setString(index, "%" + email + "%");
                index++;
            }

            if (dataNascimento != null) {
                stmt.setTimestamp(index, new java.sql.Timestamp(dataNascimento.getTime()));
                index++;
            }

            if (tipoUsuario != null) {
                stmt.setString(index, tipoUsuario.toString());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapResultSetToObject(rs));
                }
            }
        }
        return usuarios;
    }

    @Override
    protected UsuarioModel mapResultSetToObject(ResultSet rs) throws SQLException {
        UsuarioModel usuario = new UsuarioModel();

        usuario.setUsuarioId(rs.getLong("usuario_id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setSaldo(rs.getDouble("saldo"));
        usuario.setDataNascimento(rs.getDate("data_nascimento"));
        usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));

        return usuario;
    }

    @Override
    protected Long getIdValue(UsuarioModel usuarioModel) {
        return usuarioModel.getUsuarioId();
    }

    @Override
    protected void setIdValue(UsuarioModel entity, Long id) {
        entity.setUsuarioId(id);
    }

    @Override
    protected void setStatementParams(PreparedStatement stmt, UsuarioModel entity, boolean forUpdate) throws SQLException {
        int paramIndex = 1;

        stmt.setString(paramIndex++, entity.getNome());
        stmt.setString(paramIndex++, entity.getEmail());
        stmt.setString(paramIndex++, entity.getSenha());
        stmt.setDate(paramIndex++, new java.sql.Date(entity.getDataNascimento().getTime()));
        stmt.setString(paramIndex++, entity.getTipoUsuario().toString());
        stmt.setDouble(paramIndex++, entity.getSaldo());

        if (forUpdate) {
            stmt.setLong(paramIndex, entity.getUsuarioId());
        }
    }

}