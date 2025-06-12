package org.cefet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cefet.controller.base.BaseController;
import org.cefet.dtos.ResponseJogoDto;
import org.cefet.services.JogoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="GamesController", urlPatterns = { "/app/games", "/app/games/*"})
public class GamesController extends BaseController {
    private JogoService jogoService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.jogoService = new JogoService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void handleGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        switch (action) {
            case "/roleta":
                getRoletaPage(request, response);
                break;
            default:
                super.handleGetRequest(request, response, action);
        }
    }

    private void getRoletaPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> pageStyles = new ArrayList<>();
        List<String> pageScripts = new ArrayList<>();

        pageStyles.add("roleta");
        pageStyles.add("sideBar");
        request.setAttribute("pageStyles", pageStyles);

        pageScripts.add("roleta");
        request.setAttribute("pageScripts", pageScripts);

        try {
            ResponseJogoDto jogoDto = jogoService.getJogo(1);
            request.setAttribute("roleta", jogoDto);
        } catch (Exception e)
        {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/roleta-page/roleta.jsp").forward(request, response);
    }
}