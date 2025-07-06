package org.cefet.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cefet.controller.base.BaseController;
import org.cefet.dtos.CreateApostaDto;
import org.cefet.dtos.ResponseApostaDto;
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.services.ApostaService;
import org.cefet.utils.UserSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ApostaController", urlPatterns = {"/app/aposta", "/app/aposta/*"})
public class ApostaController extends BaseController {
    private ApostaService apostaService;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.apostaService = new ApostaService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void handlePostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        switch (action){
            case "/roleta":
                saveAposta(request, response);
                break;
            default:
                super.handlePostRequest(request, response, action);
        }
    }

    private void saveAposta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonRequest = sb.toString();
        System.out.println("JSON recebido do cliente: " + jsonRequest);

        CreateApostaDto createApostaDto = null;
        try {
            createApostaDto = gson.fromJson(jsonRequest, CreateApostaDto.class);

            ResponseApostaDto responseApostaDto = apostaService.saveAposta(createApostaDto);
            UserSession.setUsuario(request, new ResponseUsuarioDto(responseApostaDto.getUsuario()));

            request.setAttribute("responseApostaDto", responseApostaDto);
        } catch (Exception e)
        {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/roleta-page/roleta.jsp").forward(request, response);
    }
}
