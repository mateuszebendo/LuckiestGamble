package org.cefet.dao;

import org.cefet.contracts.BaseRepositoryImpl;
import org.cefet.contracts.base.BaseRepository;
import org.cefet.enums.TipoUsuario;
import org.cefet.models.UsuarioModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class UsuarioDAO extends BaseRepositoryImpl<UsuarioModel, Long> implements BaseRepository<UsuarioModel, Long> {

    public UsuarioDAO(Connection connection) {
        super("usuarios", "usuario_id");
        this.connection = connection;
    }

    public UsuarioDAO(String tableName, String idColumnName, Connection connection) {
        super(tableName, idColumnName);
        this.connection = connection;
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
        stmt.setDouble(paramIndex++, entity.getSaldo());
        stmt.setDate(paramIndex++, new java.sql.Date(entity.getDataNascimento().getTime()));
        stmt.setString(paramIndex++, entity.getTipoUsuario().toString());

        if (forUpdate) {
            stmt.setLong(paramIndex, entity.getUsuarioId());
        }
    }

}