package org.cefet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.cefet.controller.base.BaseController;
import org.cefet.dtos.CreateUsuarioDto;
import org.cefet.dtos.LoginUsuarioDto;
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.services.UsuarioService;
import org.cefet.utils.UserSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name="UserController", urlPatterns = { "/app/usuario", "/app/usuario/*"})
public class UserController extends BaseController {

    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.usuarioService = new UsuarioService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String httpMethod = request.getMethod();
        String pathInfo = request.getPathInfo();
        String action = (pathInfo == null || pathInfo.isEmpty()) ? "/login" : pathInfo;

        if ("GET".equalsIgnoreCase(httpMethod)) {
            handleGetRequest(request, response, action);
        } else if ("POST".equalsIgnoreCase(httpMethod)) {
            handlePostRequest(request, response, action);
        } else if ("PUT".equalsIgnoreCase(httpMethod)) {
            handlePutRequest(request, response, action);
        } else if ("DELETE".equalsIgnoreCase(httpMethod)) {
            handleDeleteRequest(request, response, action);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método HTTP não permitido.");
        }
    }

    @Override
    protected void handleGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        if (action.equals("/login")) {
            sendLoginPage(request, response, null);
        } else {
            super.handleGetRequest(request, response, action);
        }
    }

    @Override
    protected void handlePostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        if (action.equals("/cadastro")) {
            createNewAccount(request, response);
        } else if (action.equals("/entrar")) {
            signIn(request, response);
        } else {
            super.handlePostRequest(request, response, action);
        }
    }

    private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("username");
        String senha = request.getParameter("password");

        LoginUsuarioDto loginUsuarioDto = new LoginUsuarioDto(nome, senha);

        try {
            ResponseUsuarioDto usuarioResponse = usuarioService.login(loginUsuarioDto);
            UserSession.setUsuario(request, usuarioResponse);

            Cookie userCookie = new Cookie("loggedInUser", usuarioResponse.getNome() + ":" + usuarioResponse.getTipoUsuario().toString());
            userCookie.setMaxAge(60 * 30);
            userCookie.setPath("/");
            userCookie.setHttpOnly(true);
            response.addCookie(userCookie);

            response.sendRedirect(request.getContextPath() + "/app/portal/home");

        } catch (Exception e) {
            sendLoginPage(request, response, e.getMessage());
        }
    }

    private void createNewAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("username");
        String email = request.getParameter("email");
        String senha = request.getParameter("password");
        String dataNascimentoStr = request.getParameter("birthday");

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dataNascimento = formatter.parse(dataNascimentoStr);

            CreateUsuarioDto createUsuarioDto = new CreateUsuarioDto(nome, email, senha, dataNascimento);
            usuarioService.saveUsuario(createUsuarioDto);

            sendLoginPage(request, response, "Conta criada com sucesso! Faça login.");
        } catch (Exception e) {
            sendLoginPage(request, response, e.getMessage());
        }
    }

    private void sendLoginPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        List<String> pageScripts = new ArrayList<>();
        List<String> pageStyles = new ArrayList<>();
        pageScripts.add("login");
        pageStyles.add("login");

        request.setAttribute("pageScripts", pageScripts);
        request.setAttribute("pageStyles", pageStyles);
        request.setAttribute("message", message);

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}