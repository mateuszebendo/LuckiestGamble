package org.cefet.dao;

import org.cefet.contracts.BaseRepositoryImpl;
import org.cefet.contracts.base.BaseRepository;
import org.cefet.models.EstatiscasJogoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstatiscasJogoDAO extends BaseRepositoryImpl<EstatiscasJogoModel, Long> implements BaseRepository<EstatiscasJogoModel, Long> {

    public EstatiscasJogoDAO(Connection connection) {
        super("estatiscas_jogo", "EstatiscasJogoId");
        this.connection = connection;
    }

    @Override
    protected EstatiscasJogoModel mapResultSetToObject(ResultSet rs) throws SQLException {
        EstatiscasJogoModel estatisticas = new EstatiscasJogoModel();

        estatisticas.setEstatiscasJogoId(rs.getLong("estatiscas_jogo_id"));
        estatisticas.setTotalApostas(rs.getLong("total_apostas"));
        estatisticas.setJogadoresAtivos(rs.getLong("jogadores_ativos"));
        estatisticas.setJogoId(rs.getLong("jogo_id"));
        estatisticas.setLucroCasa(rs.getDouble("lucro_casa"));

        return estatisticas;
    }

    @Override
    protected Long getIdValue(EstatiscasJogoModel entity) {
        return entity.getEstatiscasJogoId();
    }

    @Override
    protected void setIdValue(EstatiscasJogoModel entity, Long aLong) {
        entity.setEstatiscasJogoId(aLong);
    }

    @Override
    protected void setStatementParams(PreparedStatement stmt, EstatiscasJogoModel entity, boolean forUpdate) throws SQLException {
        int paramIndex = 1;

        stmt.setLong(paramIndex++, entity.getTotalApostas());
         stmt.setDouble(paramIndex++, entity.getLucroCasa());
        stmt.setLong(paramIndex++, entity.getJogadoresAtivos());
        stmt.setLong(paramIndex++, entity.getJogoId());

        if (forUpdate) {
            stmt.setLong(paramIndex++, entity.getEstatiscasJogoId());
        }
    }
}