package org.cefet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cefet.controller.base.BaseController;
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.utils.UserSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="GamesController", urlPatterns = { "/app/games", "/app/games/*"})
public class GamesController extends BaseController {

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

        pageStyles.add("roleta");
        pageStyles.add("sideBar");
        request.setAttribute("pageStyles", pageStyles);

        request.getRequestDispatcher("/WEB-INF/views/roleta-page/roleta.jsp").forward(request, response);
    }
}