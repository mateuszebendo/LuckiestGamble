package org.cefet.services;

import org.cefet.config.ConnectionFactory;
import org.cefet.dao.TransacaoDAO;
import org.cefet.dao.UsuarioDAO;
import org.cefet.dtos.CreateTransacaoDto;
import org.cefet.dtos.ResponseTransacaoDto;
import org.cefet.enums.StatusTransacao;
import org.cefet.enums.TipoTransacao;
import org.cefet.models.TransacaoModel;
import org.cefet.models.UsuarioModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class TransacaoService {
    private TransacaoDAO transacaoDAO;
    private UsuarioDAO usuarioDAO;

    public TransacaoService() throws SQLException {
        Connection conexao = ConnectionFactory.getConnection();

        transacaoDAO = new TransacaoDAO(conexao);
        usuarioDAO = new UsuarioDAO(conexao);
    }

    public ResponseTransacaoDto deposit(CreateTransacaoDto createTransacaoDto) throws SQLException {
        TransacaoModel transacaoFinal = null;
        double valor = createTransacaoDto.getValor();
        long usuarioId = createTransacaoDto.getUsuarioId();

        try {
            createTransacaoDto.setTipoTransacao(TipoTransacao.DEPOSITO);
            createTransacaoDto.setStatus(StatusTransacao.CONCLUIDO);
            createTransacaoDto.setDescricao("Depósito de " + valor + " realizado com sucesso.");

            TransacaoModel transacaoTemporaria = new TransacaoModel(createTransacaoDto);
            transacaoTemporaria = transacaoDAO.save(transacaoTemporaria);
            transacaoFinal = transacaoTemporaria;

            usuarioDAO.changeSaldo(valor, usuarioId);

            Optional<UsuarioModel> usuarioOptional = usuarioDAO.findById(usuarioId);

            if (usuarioOptional.isEmpty()) {
                throw new IllegalArgumentException("Usuário não encontrado.");
            }

            UsuarioModel usuarioModel = usuarioOptional.get();
            transacaoFinal.setUsuario(usuarioModel);
        } catch (SQLException e) {
            throw new SQLException("Erro de banco de dados durante o depósito: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            createTransacaoDto.setTipoTransacao(TipoTransacao.DEPOSITO);
            createTransacaoDto.setStatus(StatusTransacao.CANCELADO);
            createTransacaoDto.setDescricao("Transação cancelada: " + e.getMessage());
            TransacaoModel transacaoCancelada = new TransacaoModel(createTransacaoDto);
            try {
                transacaoFinal = transacaoDAO.save(transacaoCancelada);
            } catch (SQLException ex) {
                System.err.println("Erro ao registrar transação cancelada: " + ex.getMessage());
            }
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado durante o depósito: " + e.getMessage());
            createTransacaoDto.setTipoTransacao(TipoTransacao.DEPOSITO);
            createTransacaoDto.setStatus(StatusTransacao.CANCELADO);
            createTransacaoDto.setDescricao("Transação cancelada devido a erro interno: " + e.getMessage());
            TransacaoModel transacaoCancelada = new TransacaoModel(createTransacaoDto);
            try {
                transacaoFinal = transacaoDAO.save(transacaoCancelada);
            } catch (SQLException ex) {
                System.err.println("Erro ao registrar transação cancelada por erro inesperado: " + ex.getMessage());
            }
            throw new RuntimeException("Ocorreu um erro inesperado ao processar o depósito.", e);
        }

        return new ResponseTransacaoDto(transacaoFinal);
    }

    public ResponseTransacaoDto withdrawal(CreateTransacaoDto createTransacaoDto) throws SQLException {
        TransacaoModel transacaoFinal = null;
        double valor = createTransacaoDto.getValor();
        long usuarioId = createTransacaoDto.getUsuarioId();

        try {
            Optional<UsuarioModel> usuarioOptional = usuarioDAO.findById(usuarioId);
            if (usuarioOptional.isEmpty()) {
                throw new IllegalArgumentException("Usuário não encontrado.");
            }
            UsuarioModel usuarioModel = usuarioOptional.get();

            if (usuarioModel.getSaldo() - valor < 0) {
                throw new IllegalArgumentException("Saldo insuficiente.");
            }

            createTransacaoDto.setTipoTransacao(TipoTransacao.SAQUE);
            createTransacaoDto.setStatus(StatusTransacao.CONCLUIDO);
            createTransacaoDto.setDescricao("Saque de " + valor + " realizado com sucesso.");

            TransacaoModel transacaoTemporaria = new TransacaoModel(createTransacaoDto);

            transacaoTemporaria = transacaoDAO.save(transacaoTemporaria);
            usuarioModel = usuarioDAO.changeSaldo(-valor, usuarioId);
            transacaoTemporaria.setUsuario(usuarioModel);
            transacaoFinal = transacaoTemporaria;
        } catch (SQLException e) {
            throw new SQLException("Erro de banco de dados durante o saque: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            createTransacaoDto.setTipoTransacao(TipoTransacao.SAQUE);
            createTransacaoDto.setStatus(StatusTransacao.CANCELADO);
            createTransacaoDto.setDescricao("Transação cancelada: " + e.getMessage());

            TransacaoModel transacaoCancelada = new TransacaoModel(createTransacaoDto);
            try {
                transacaoFinal = transacaoDAO.save(transacaoCancelada);
            } catch (SQLException ex) {
                System.err.println("Erro ao registrar transação cancelada: " + ex.getMessage());
            }
            throw new IllegalArgumentException(e.getMessage());

        } catch (Exception e) {
            System.err.println("Erro inesperado durante o saque: " + e.getMessage());
            createTransacaoDto.setTipoTransacao(TipoTransacao.SAQUE);
            createTransacaoDto.setStatus(StatusTransacao.CANCELADO);
            createTransacaoDto.setDescricao("Transação cancelada devido a erro interno: " + e.getMessage());
            TransacaoModel transacaoCancelada = new TransacaoModel(createTransacaoDto);
            try {
                transacaoFinal = transacaoDAO.save(transacaoCancelada);
            } catch (SQLException ex) {
                System.err.println("Erro ao registrar transação cancelada por erro inesperado: " + ex.getMessage());
            }
            throw new RuntimeException("Ocorreu um erro inesperado ao processar o saque.", e);
        }

        return new ResponseTransacaoDto(transacaoFinal);
    }
}
