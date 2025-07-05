package org.cefet.services;

import org.cefet.config.ConnectionFactory;
import org.cefet.dao.JogoDAO;
import org.cefet.dtos.ResponseJogoDto;
import org.cefet.enums.StatusTransacao;
import org.cefet.enums.TipoTransacao;
import org.cefet.models.JogoModel;
import org.cefet.models.TransacaoModel;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class JogoService {
    private JogoDAO jogoDAO;

    public JogoService() throws SQLException {
        jogoDAO = new JogoDAO(ConnectionFactory.getConnection());
    }

    public ResponseJogoDto getJogo(long id) {
        try {
            JogoModel jogoModel = jogoDAO.findById(id).orElseThrow(
                    () -> new NoSuchElementException("Nenhum jogo encontrado com o ID: " + id)
            );
            return new ResponseJogoDto(jogoModel);

        } catch (NoSuchElementException e) {
            System.err.println("Jogo não encontrado: " + e.getMessage());
            throw e;
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao recuperar jogo (ID: " + id + "): " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro de banco de dados ao recuperar o jogo.", e);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao recuperar jogo (ID: " + id + "): " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado ao recuperar o jogo.", e);
        }
    }
}
