package org.cefet.dao;

import org.cefet.contracts.BaseRepositoryImpl;
import org.cefet.contracts.base.BaseRepository;
import org.cefet.models.TransacaoModel;
import org.cefet.enums.TipoTransacao;
import org.cefet.enums.StatusTransacao;

import java.sql.*;

public class TransacaoDAO extends BaseRepositoryImpl<TransacaoModel, Long> implements BaseRepository<TransacaoModel, Long> {

    public TransacaoDAO(Connection connection) {
        super("transacao", "TransacaoId");
        this.connection = connection;
    }

    @Override
    protected TransacaoModel mapResultSetToObject(ResultSet rs) throws SQLException {
        TransacaoModel transacao = new TransacaoModel();

        transacao.setTransacaoId(rs.getLong("transacao_id"));
        transacao.setUsuarioId(rs.getLong("usuario_id"));

        String tipoTransacaoStr = rs.getString("tipo");
        if (tipoTransacaoStr != null) {
            transacao.setTipoTransacao(TipoTransacao.valueOf(tipoTransacaoStr));
        }

        transacao.setValor(rs.getDouble("valor"));
        transacao.setDataHora(new java.util.Date(rs.getTimestamp("data_hora").getTime()));
        transacao.setDescricao(rs.getString("descricao"));

        String statusTransacaoStr = rs.getString("status");
        if (statusTransacaoStr != null) {
            transacao.setStatus(StatusTransacao.valueOf(statusTransacaoStr));
        }

        transacao.setDataCriacao(new java.util.Date(rs.getTimestamp("data_criacao").getTime()));
        transacao.setDataAtualizacao(new java.util.Date(rs.getTimestamp("data_atualizacao").getTime()));

        return transacao;
    }

    @Override
    protected Long getIdValue(TransacaoModel entity) {
        return entity.getTransacaoId();
    }

    @Override
    protected void setIdValue(TransacaoModel entity, Long aLong) {
        entity.setTransacaoId(aLong);
    }

    @Override
    protected void setStatementParams(PreparedStatement stmt, TransacaoModel entity, boolean forUpdate) throws SQLException {
        int paramIndex = 1;

        stmt.setString(paramIndex++, entity.getTipoTransacao().name());
        stmt.setDouble(paramIndex++, entity.getValor());

        if (entity.getDataHora() != null) {
            stmt.setTimestamp(paramIndex++, new Timestamp(entity.getDataHora().getTime()));
        } else {
            stmt.setNull(paramIndex++, Types.TIMESTAMP);
        }

        if (entity.getDescricao() != null) {
            stmt.setString(paramIndex++, entity.getDescricao());
        } else {
            stmt.setNull(paramIndex++, Types.VARCHAR);
        }

        stmt.setString(paramIndex++, entity.getStatus().name());

        if (entity.getDataCriacao() != null) {
            stmt.setTimestamp(paramIndex++, new Timestamp(entity.getDataCriacao().getTime()));
        } else {
            stmt.setNull(paramIndex++, Types.TIMESTAMP);
        }

        if (entity.getDataAtualizacao() != null) {
            stmt.setTimestamp(paramIndex++, new Timestamp(entity.getDataAtualizacao().getTime()));
        } else {
            stmt.setNull(paramIndex++, Types.TIMESTAMP);
        }

        stmt.setLong(paramIndex++, entity.getUsuarioId());

        if (forUpdate) {
            stmt.setLong(paramIndex++, entity.getTransacaoId());
        }
    }
}