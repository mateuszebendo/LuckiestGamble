package org.cefet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.cefet.controller.base.BaseController;
import org.cefet.dtos.ResponseTransacaoDto;
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.services.TransacaoService;
import org.cefet.utils.UserSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name="PortalController", urlPatterns = { "/app/portal", "/app/portal/*"})
public class PortalController extends BaseController {

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

    private void handleGenericRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        switch (action) {
            case "/home":
                getHomePage(request, response);
                break;
            case "/logout":
                doLogout(request, response);
                break;
            case "/profile":
                getProfilePage(request, response);
                break;
            case "/games":
                getGamesPage(request, response);
                break;
            case "/projeto_dois":
                getProjectTwoPage(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void handleGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        handleGenericRequest(request, response, action);
    }

    @Override
    protected void handlePostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        handleGenericRequest(request, response, action);
    }

    protected void getHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> pageStyles = new ArrayList<>();
        pageStyles.add("home");
        pageStyles.add("sideBar");
        request.setAttribute("pageStyles", pageStyles);

        request.getRequestDispatcher("/WEB-INF/views/home-page/home.jsp").forward(request, response);
    }

    protected void getProfilePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> pageStyles = new ArrayList<>();
        List<String> pageScripts = new ArrayList<>();

        pageStyles.add("profile");
        pageStyles.add("sideBar");
        request.setAttribute("pageStyles", pageStyles);

        pageScripts.add("profile");
        request.setAttribute("pageScripts", pageScripts);

        ResponseUsuarioDto currentUser = UserSession.getUsuario(request, response);
        try {
            List<ResponseTransacaoDto> transacaoList = transacaoService.getAllTransacaoByUser(currentUser.getUsuarioId());
            request.setAttribute("transacaoList", transacaoList);
        } catch (RuntimeException e) {
            System.err.println("Erro ao carregar histórico de transações: " + e.getMessage());
            request.setAttribute("message", "Erro ao carregar histórico de transações: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado no controller ao carregar histórico: " + e.getMessage());
            request.setAttribute("message", "Ocorreu um erro inesperado.");
        }

        request.getRequestDispatcher("/WEB-INF/views/profile-page/profile.jsp").forward(request, response);
    }

    protected void getGamesPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> pageStyles = new ArrayList<>();
        HashMap<String, String> games = new HashMap<>();

        pageStyles.add("games");
        pageStyles.add("sideBar");
        request.setAttribute("pageStyles", pageStyles);

        games.put("roleta", "The Account SAS grants access to multiple storage services within a single Azure Storage account, including Blob, Queue, Table, and File Storage. This SAS type is useful when multiple services need to be accessed under a single SAS token, eliminating the need to generate multiple tokens for different services. Since it applies to the entire storage account, it offers broad access and should be used cautiously to limit exposure.");
//        games.put("blackjack", "The Account SAS grants access to multiple storage services within a single Azure Storage account, including Blob, Queue, Table, and File Storage. This SAS type is useful when multiple services need to be accessed under a single SAS token, eliminating the need to generate multiple tokens for different services. Since it applies to the entire storage account, it offers broad access and should be used cautiously to limit exposure.");
//        games.put("dados", "The Account SAS grants access to multiple storage services within a single Azure Storage account, including Blob, Queue, Table, and File Storage. This SAS type is useful when multiple services need to be accessed under a single SAS token, eliminating the need to generate multiple tokens for different services. Since it applies to the entire storage account, it offers broad access and should be used cautiously to limit exposure.");
        request.setAttribute("games", games);

        request.getRequestDispatcher("/WEB-INF/views/games-page/games.jsp").forward(request, response);
    }

    protected void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("loggedInUser".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                    break;
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/app/usuario/login");
    }

    protected void getProjectTwoPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> pageStyles = new ArrayList<>();
        List<String> pageScripts = new ArrayList<>();

        pageStyles.add("project-two");
        pageStyles.add("sideBar");

        pageScripts.add("projectTwo");

        request.setAttribute("pageStyles", pageStyles);
        request.setAttribute("pageScripts", pageScripts);

        String userColorPreference = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userColorPreference".equals(cookie.getName())) {
                    userColorPreference = cookie.getValue();
                    break;
                }
            }
        }

        request.setAttribute("userColorPreference", userColorPreference);

        request.getRequestDispatcher("/WEB-INF/views/project-two-page/project_two.jsp").forward(request, response);
    }
}