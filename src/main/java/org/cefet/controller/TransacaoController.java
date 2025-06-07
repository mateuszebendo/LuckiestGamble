package org.cefet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cefet.dtos.CreateTransacaoDto;
import org.cefet.dtos.ResponseTransacaoDto;
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.services.TransacaoService;
import org.cefet.utils.UserSession;

import java.io.IOException;

@WebServlet(name="TransacaoController", urlPatterns = { "/app/transacao", "/app/transacao/*"})
public class TransacaoController extends HttpServlet {

    private TransacaoService transacaoService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.transacaoService = new TransacaoService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String httpMethod = request.getMethod();
        String pathInfo = request.getPathInfo();
        String action = (pathInfo == null || pathInfo.isEmpty()) ? "/" : pathInfo;

        UserSession.getUsuario(request, response);

        if(httpMethod.equals("POST")) {
            if(action.equals("/deposito")) {
                depositInAccount(request, response);
            }
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
        } catch (Exception e)
        {
            request.setAttribute("message", "Erro ao efetuar o deposito: " + e.getMessage());
        }
        request.getRequestDispatcher("/app/portal/profile").forward(request, response);
    }
}
