package org.cefet.dao;

import org.cefet.contracts.BaseRepositoryImpl;
import org.cefet.contracts.base.BaseRepository;
import org.cefet.models.TransacaoModel;
import org.cefet.enums.TipoTransacao;
import org.cefet.enums.StatusTransacao;
import org.cefet.models.UsuarioModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO extends BaseRepositoryImpl<TransacaoModel, Long> implements BaseRepository<TransacaoModel, Long> {

    public TransacaoDAO(Connection connection) {
        super("transacao", "TransacaoId");
        this.connection = connection;
    }

    public List<TransacaoModel> getAllTransacaoByUserId(long userId) throws SQLException {
        List<TransacaoModel> transacoes = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM transacao WHERE usuario_id = ?");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transacoes.add(mapResultSetToObject(rs));
                }
            }
        }
        return transacoes;
    }

    @Override
    protected TransacaoModel mapResultSetToObject(ResultSet rs) throws SQLException {
        TransacaoModel transacao = new TransacaoModel();

        transacao.setTransacaoId(rs.getLong("transacao_id"));
        transacao.setUsuarioId(rs.getLong("usuario_id"));

        String tipoTransacaoStr = rs.getString("tipo_transacao");
        if (tipoTransacaoStr != null) {
            transacao.setTipoTransacao(TipoTransacao.valueOf(tipoTransacaoStr.toUpperCase()));
        }

        transacao.setValor(rs.getDouble("valor"));

        Timestamp dataHoraTs = rs.getTimestamp("data_hora");
        if (dataHoraTs != null) {
            transacao.setDataHora(new java.util.Date(dataHoraTs.getTime()));
        } else {
            transacao.setDataHora(null);
        }

        transacao.setDescricao(rs.getString("descricao"));

        String statusTransacaoStr = rs.getString("status");
        if (statusTransacaoStr != null) {
            transacao.setStatus(StatusTransacao.valueOf(statusTransacaoStr.toUpperCase()));
        }

        Timestamp dataCriacaoTs = rs.getTimestamp("data_criacao");
        if (dataCriacaoTs != null) {
            transacao.setDataCriacao(new java.util.Date(dataCriacaoTs.getTime()));
        } else {
            transacao.setDataCriacao(null);
        }

        Timestamp dataAtualizacaoTs = rs.getTimestamp("data_atualizacao");
        if (dataAtualizacaoTs != null) {
            transacao.setDataAtualizacao(new java.util.Date(dataAtualizacaoTs.getTime()));
        } else {
            transacao.setDataAtualizacao(null);
        }

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