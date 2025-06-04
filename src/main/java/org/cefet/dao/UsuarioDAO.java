package org.cefet.dao;

import org.cefet.contracts.BaseRepositoryImpl;
import org.cefet.models.base.BaseModel;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioDAO extends BaseRepositoryImpl {

    public UsuarioDAO(String tableName, String idColumnName) {
        super("usuarios", "UsuarioId");
    }

    @Override
    protected BaseModel mapResultSetToObject(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected Serializable getIdValue(BaseModel entity) {
        return null;
    }

    @Override
    protected void setIdValue(BaseModel entity, Serializable serializable) {

    }

    @Override
    public Optional findById(Object o) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void deleteById(Object o) throws SQLException {

    }
}
