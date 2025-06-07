package org.cefet.contracts.base;

import org.cefet.models.base.BaseModel;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseModel, ID> {
    void setConnection(Connection connection); // Ou use um DataSource

    T save(T entity) throws SQLException;
    Optional<T> findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    void delete(T entity) throws SQLException;
    void deleteById(ID id) throws SQLException;
    long count() throws SQLException;
}
