package org.cefet.dao;

import org.cefet.contracts.BaseRepositoryImpl;
import org.cefet.contracts.base.BaseRepository;
import org.cefet.models.ApostaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApostaDAO extends BaseRepositoryImpl<ApostaModel, Long> implements BaseRepository<ApostaModel, Long> {

    public ApostaDAO(Connection connection) {
        super("transacao", "TransacaoId");
        this.connection = connection;
    }

    @Override
    protected ApostaModel mapResultSetToObject(ResultSet rs) throws SQLException {
        ApostaModel aposta = new ApostaModel();

        aposta.setApostaId(rs.getLong("aposta_id"));
        aposta.setValor(rs.getDouble("valor"));
        aposta.setDataAposta(rs.getDate("data_aposta"));
        aposta.setResultado(rs.getString("resultado"));
        aposta.setTipoAposta(rs.getString("tipo_aposta"));
        aposta.setUsuarioId(rs.getLong("usuario_id"));
        aposta.setJogoId(rs.getLong("jogo_id"));

        return aposta;
    }

    @Override
    protected Long getIdValue(ApostaModel entity) {
        return entity.getApostaId();
    }

    @Override
    protected void setIdValue(ApostaModel entity, Long aLong) {
        entity.setApostaId(aLong);
    }

    @Override
    protected void setStatementParams(PreparedStatement stmt, ApostaModel entity, boolean forUpdate) throws SQLException {
        int paramIndex = 1;

        stmt.setDouble(paramIndex++, entity.getValor());
        stmt.setDate(paramIndex++, new java.sql.Date(entity.getDataAposta().getTime()));
        stmt.setString(paramIndex++, entity.getResultado());
        stmt.setString(paramIndex++, entity.getTipoAposta());
        stmt.setLong(paramIndex++, entity.getUsuarioId());
        stmt.setLong(paramIndex++, entity.getJogoId());

        if (forUpdate) {
            stmt.setLong(paramIndex++, entity.getApostaId());
        }
    }
}
