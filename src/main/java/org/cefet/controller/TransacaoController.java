package org.cefet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cefet.controller.base.BaseController;
import org.cefet.dtos.CreateTransacaoDto;
import org.cefet.dtos.ResponseTransacaoDto;
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.services.TransacaoService;
import org.cefet.utils.UserSession;

import java.io.IOException;

@WebServlet(name="TransacaoController", urlPatterns = { "/app/transacao", "/app/transacao/*"})
public class TransacaoController extends BaseController {

    private TransacaoService transacaoService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.transacaoService = new TransacaoService();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize TransacaoService", e);
        }
    }

    @Override
    protected void handlePostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        if(action.contains("/deposito")){
            depositInAccount(request, response);
        } else if(action.contains("/saque")){
            withdrawalCash(request, response);
        } else if(action.contains("/deletar")){
            deleteTransaction(request, response);
        }
    }

    private void depositInAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Double value = Double.parseDouble(request.getParameter("newAmount"));
        ResponseUsuarioDto responseUsuarioDto = UserSession.getUsuario(request, response);

        try {
            CreateTransacaoDto createTransacaoDto = new CreateTransacaoDto();
            createTransacaoDto.setValor(value);
            createTransacaoDto.setUsuarioId(responseUsuarioDto.getUsuarioId());
            ResponseTransacaoDto responseTransacaoDto = transacaoService.deposit(createTransacaoDto);

            UserSession.setUsuario(request, responseTransacaoDto.getResponseUsuario());
            request.setAttribute("transacaoResponse", responseTransacaoDto);
            request.setAttribute("message", "Depósito efetuado com sucesso!");
        } catch (Exception e) {
            request.setAttribute("message", "Erro ao efetuar o depósito: " + e.getMessage());
        }
        request.getRequestDispatcher("/app/portal/profile").forward(request, response);
    }

    private void withdrawalCash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Double value = Double.parseDouble(request.getParameter("decreaseAmount"));
        ResponseUsuarioDto responseUsuarioDto = UserSession.getUsuario(request, response);

        try {
            CreateTransacaoDto createTransacaoDto = new CreateTransacaoDto();
            createTransacaoDto.setValor(value);
            createTransacaoDto.setUsuarioId(responseUsuarioDto.getUsuarioId());
            ResponseTransacaoDto responseTransacaoDto = transacaoService.withdrawal(createTransacaoDto);

            UserSession.setUsuario(request, responseTransacaoDto.getResponseUsuario());
            request.setAttribute("transacaoResponse", responseTransacaoDto);
            request.setAttribute("message", "Saque efetuado com sucesso!");
        } catch (Exception e) {
            request.setAttribute("message", "Erro ao efetuar o saque: " + e.getMessage());
        }
        request.getRequestDispatcher("/app/portal/profile").forward(request, response);
    }

    private void deleteTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long transacaoId = Long.parseLong(request.getParameter("transacaoId"));

        try {
            transacaoService.deleteTransaction(transacaoId);
            request.setAttribute("message", "Transação removida com sucesso!");
        } catch (Exception e) {
            request.setAttribute("message", "Erro ao deletar transação: " + e.getMessage());
        }
        request.getRequestDispatcher("/app/portal/projeto_um").forward(request, response);
    }
}