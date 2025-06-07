package org.cefet.services;

import org.cefet.config.ConnectionFactory;
import org.cefet.dao.TransacaoDAO;
import org.cefet.dao.UsuarioDAO;
import org.cefet.dtos.CreateTransacaoDto;
import org.cefet.dtos.ResponseTransacaoDto;
import org.cefet.enums.StatusTransacao;
import org.cefet.enums.TipoTransacao;
import org.cefet.models.TransacaoModel;

import java.sql.Connection;
import java.sql.SQLException;

public class TransacaoService {
    private TransacaoDAO transacaoDAO;
    private UsuarioDAO usuarioDAO;

    public TransacaoService() throws SQLException {
        Connection conexao = ConnectionFactory.getConnection();

        transacaoDAO = new TransacaoDAO(conexao);
        usuarioDAO = new UsuarioDAO(conexao);
    }

    public ResponseTransacaoDto deposit(CreateTransacaoDto createTransacaoDto) throws SQLException {
        double valor = createTransacaoDto.getValor();
        long usuarioId = createTransacaoDto.getUsuarioId();

        createTransacaoDto.setTipoTransacao(TipoTransacao.DEPOSITO);
        createTransacaoDto.setStatus(StatusTransacao.CONCLUIDO);
        createTransacaoDto.setDescricao("Depositado com sucesso");

        TransacaoModel transacaoModel = new TransacaoModel(createTransacaoDto);
        transacaoModel = transacaoDAO.save(transacaoModel);
        transacaoModel.setUsuario(usuarioDAO.addSaldo(valor, usuarioId));

        return new ResponseTransacaoDto(transacaoModel);
    }
}
