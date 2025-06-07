package org.cefet.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.cefet.contracts.BaseRepositoryImpl;
import org.cefet.contracts.base.BaseRepository;
import org.cefet.models.JogoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class JogoDAO extends BaseRepositoryImpl<JogoModel, Long> implements BaseRepository<JogoModel, Long> {

    private final ObjectMapper objectMapper;

    public JogoDAO(Connection connection) {
        super("jogo", "JogoId");
        this.connection = connection;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected JogoModel mapResultSetToObject(ResultSet rs) throws SQLException {
        JogoModel jogo = new JogoModel();

        jogo.setJogoId(rs.getLong("jogo_id"));
        jogo.setNome(rs.getString("nome"));

        // Lidar com o campo 'odds' que é um JSON
        String oddsJson = rs.getString("odds");
        if (oddsJson != null && !oddsJson.isEmpty()) {
            try {
                // Desserializar o JSON para HashMap
                jogo.setOdds(objectMapper.readValue(oddsJson, new TypeReference<HashMap<String, Double>>() {}));
            } catch (JsonProcessingException e) {
                // Log the error or rethrow as SQLException
                System.err.println("Erro ao desserializar Odds JSON: " + e.getMessage());
                throw new SQLException("Erro ao processar dados JSON para Odds", e);
            }
        } else {
            jogo.setOdds(new HashMap<>());
        }

        return jogo;
    }

    @Override
    protected Long getIdValue(JogoModel entity) {
        return entity.getJogoId();
    }

    @Override
    protected void setIdValue(JogoModel entity, Long id) {
        entity.setJogoId(id);
    }

    @Override
    protected void setStatementParams(PreparedStatement stmt, JogoModel entity, boolean forUpdate) throws SQLException {
        int paramIndex = 1;

        stmt.setString(paramIndex++, entity.getNome());

        // Lidar com o campo 'odds' que é um HashMap e precisa ser serializado para JSON
        try {
            String oddsJson = objectMapper.writeValueAsString(entity.getOdds());
            stmt.setString(paramIndex++, oddsJson);
        } catch (JsonProcessingException e) {
            // Log the error or rethrow as SQLException
            System.err.println("Erro ao serializar Odds para JSON: " + e.getMessage());
            throw new SQLException("Erro ao preparar dados JSON para Odds", e);
        }

        if (forUpdate) {
            stmt.setLong(paramIndex++, entity.getJogoId());
        }
    }
}