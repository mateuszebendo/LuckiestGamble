package org.cefet.contracts;

import org.cefet.contracts.base.BaseRepository;
import org.cefet.models.base.BaseModel;
import org.cefet.utils.StringConverter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoryImpl <T extends BaseModel, ID extends Serializable> implements BaseRepository<T, ID> {

    protected Connection connection;
    private Class<T> entityClass;
    private String tableName;
    private String idColumnName;

    @SuppressWarnings("unchecked")
    public BaseRepositoryImpl(String tableName, String idColumnName) {
        this.entityClass = (Class<T>) ((java.lang.reflect.ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.tableName = tableName;
        this.idColumnName = idColumnName;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    protected abstract T mapResultSetToObject(ResultSet rs) throws SQLException;
    protected abstract ID getIdValue(T entity);
    protected abstract void setIdValue(T entity, ID id);
    protected abstract void setStatementParams(PreparedStatement stmt, T entity, boolean forUpdate) throws SQLException;

    @Override
    public T save(T entity) throws SQLException {
        ID id = getIdValue(entity);
        if (id == null) {
            StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
            StringBuilder values = new StringBuilder("VALUES (");
            List<String> columns = new ArrayList<>();
            try {
                Field[] fields = entityClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().equalsIgnoreCase(idColumnName) || field.getType().equals(BaseModel.class)) continue;
                    sql.append(StringConverter.pascalToSnakeCase(field.getName())).append(",");
                    values.append("?,");
                    columns.add(field.getName());
                }
                sql.deleteCharAt(sql.length() - 1).append(") ");
                values.deleteCharAt(values.length() - 1).append(")");
                String insertSql = sql.toString() + values.toString();

                try (PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    setStatementParams(stmt, entity, false);
                    stmt.executeUpdate();

                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            if (idColumnName.equalsIgnoreCase("id")) {
                                setIdValue(entity, (ID) (Object) generatedKeys.getLong(1));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new SQLException("Erro ao salvar entidade: " + e.getMessage(), e);
            }
        } else {
            StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
            try (PreparedStatement stmt = connection.prepareStatement(sql.toString() + " WHERE " + idColumnName + " = ?")) {
                setStatementParams(stmt, entity, true);
                stmt.executeUpdate();
            } catch (Exception e) {
                throw new SQLException("Erro ao atualizar entidade: " + e.getMessage(), e);
            }
        }
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE " + idColumnName + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToObject(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll() throws SQLException {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                entities.add(mapResultSetToObject(rs));
            }
        }
        return entities;
    }

    @Override
    public void delete(T entity) throws SQLException {
        deleteById(getIdValue(entity));
    }

    @Override
    public void deleteById(ID id) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + idColumnName + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public long count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return 0;
    }
}
