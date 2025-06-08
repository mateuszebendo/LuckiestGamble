package org.cefet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession; // Importar HttpSession
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.utils.UserSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name="PortalController", urlPatterns = { "/app/portal", "/app/portal/*"})
public class PortalController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String httpMethod = request.getMethod();

        String pathInfo = request.getPathInfo();
        String action = (pathInfo == null || pathInfo.isEmpty()) ? "/home" : pathInfo;

        ResponseUsuarioDto usuarioDto = UserSession.getUsuario(request, response);
        request.setAttribute("usuarioRequest", usuarioDto);

        if("GET".equalsIgnoreCase(httpMethod)){
            if(action.equals("/home")){
                getHomePage(request, response);
            } else if(action.equals("/profile")) {
                getProfilePage(request, response);
            } else if(action.equals("/logout")) {
                doLogout(request, response);
            } else if(action.equals("/games")) {
                getGamesPage(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else if("POST".equalsIgnoreCase(httpMethod)) {
            if(action.equals("/home")){
                getHomePage(request, response);
            } else if(action.equals("/profile")) {
                getProfilePage(request, response);
            } else if(action.equals("/logout")) {
                doLogout(request, response);
            } else if(action.equals("/games")) {
                getGamesPage(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
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

        request.getRequestDispatcher("/WEB-INF/views/profile-page/profile.jsp").forward(request, response);
    }

    protected void getGamesPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> pageStyles = new ArrayList<>();
        HashMap<String, String> games = new HashMap<>();

        pageStyles.add("games");
        pageStyles.add("sideBar");
        request.setAttribute("pageStyles", pageStyles);

        games.put("roleta", "The Account SAS grants access to multiple storage services within a single Azure Storage account, including Blob, Queue, Table, and File Storage. This SAS type is useful when multiple services need to be accessed under a single SAS token, eliminating the need to generate multiple tokens for different services. Since it applies to the entire storage account, it offers broad access and should be used cautiously to limit exposure.");
        games.put("blackjack", "The Account SAS grants access to multiple storage services within a single Azure Storage account, including Blob, Queue, Table, and File Storage. This SAS type is useful when multiple services need to be accessed under a single SAS token, eliminating the need to generate multiple tokens for different services. Since it applies to the entire storage account, it offers broad access and should be used cautiously to limit exposure.");
        games.put("dados", "The Account SAS grants access to multiple storage services within a single Azure Storage account, including Blob, Queue, Table, and File Storage. This SAS type is useful when multiple services need to be accessed under a single SAS token, eliminating the need to generate multiple tokens for different services. Since it applies to the entire storage account, it offers broad access and should be used cautiously to limit exposure.");
        request.setAttribute("games", games);

        request.getRequestDispatcher("/WEB-INF/views/games-page/games.jsp").forward(request, response);
    }

    protected void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalida a sessão
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Isso remove todos os atributos da sessão, incluindo "currentUser"
        }

        // Remove o cookie de autenticação
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
}