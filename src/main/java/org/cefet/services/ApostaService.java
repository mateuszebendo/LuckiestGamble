package org.cefet.services;

import org.cefet.config.ConnectionFactory;
import org.cefet.dao.ApostaDAO;
import org.cefet.dao.UsuarioDAO;
import org.cefet.dtos.CreateApostaDto;
import org.cefet.dtos.ResponseApostaDto;
import org.cefet.models.ApostaModel;
import org.cefet.models.UsuarioModel;

import java.sql.SQLException;

public class ApostaService {
    private ApostaDAO apostaDAO;
    private UsuarioDAO usuarioDAO;

    public ApostaService() throws SQLException {
        try {
            apostaDAO = new ApostaDAO(ConnectionFactory.getConnection());
            usuarioDAO = new UsuarioDAO(ConnectionFactory.getConnection());
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados para o serviço de usuário.", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado na inicialização do serviço de usuário.", e);
        }
    }

    public ResponseApostaDto saveAposta(CreateApostaDto createApostaDto) throws SQLException {
        try {
            ApostaModel saveApostaModel = new ApostaModel(createApostaDto);
            ApostaModel resultApostaModel = apostaDAO.save(saveApostaModel);

            if(resultApostaModel == null) {
                throw new SQLException("Erro ao salvar aposta: o registro não foi retornado após a inserção.");
            }

            UsuarioModel usuarioModel = usuarioDAO.changeSaldo(-saveApostaModel.getValor(), saveApostaModel.getUsuarioId());

            if(usuarioModel == null) {
                throw new SQLException("Erro ao atualizar saldo do usuário após a aposta.");
            }

            resultApostaModel.setUsuario(usuarioModel);

            return new ResponseApostaDto(resultApostaModel);
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao salvar aposta ou alterar saldo: " + e.getMessage());
            throw new SQLException("Erro de banco de dados ao processar a aposta.", e);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar aposta: " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado ao salvar a aposta.", e);
        }
    }
}
