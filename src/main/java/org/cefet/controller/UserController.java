package org.cefet.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name="UserController", urlPatterns = { "/app/usuario", "/app/usuario/*"})
public class UserController extends BaseController {

    private UsuarioService usuarioService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.usuarioService = new UsuarioService();
            this.gson = new Gson();
        } catch (SQLException e) {
            throw new ServletException("Erro ao inicializar UsuarioService: problema de SQL.", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado na inicialização do UsuarioService.", e);
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
        } else if (action.equals("/recuperar-usuarios")) {
            getAllUsers(request, response);
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
        } else if (action.equals("/novo-usuario")) {
            createNewAccountFromJson(request, response);
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

    private void createNewAccountFromJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json"); // Define o tipo de resposta como JSON
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try (BufferedReader reader = request.getReader()) {
            CreateUsuarioDto createUsuarioDto = gson.fromJson(reader, CreateUsuarioDto.class);

            if (createUsuarioDto == null || createUsuarioDto.getNome() == null || createUsuarioDto.getNome().isEmpty() ||
                    createUsuarioDto.getEmail() == null || createUsuarioDto.getEmail().isEmpty() ||
                    createUsuarioDto.getSenha() == null || createUsuarioDto.getSenha().isEmpty() ||
                    createUsuarioDto.getDataNascimento() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Status 400
                out.print("{\"message\": \"Dados de usuário incompletos ou inválidos.\"}");
                out.flush();
                return;
            }
            usuarioService.saveUsuario(createUsuarioDto);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"message\": \"Usuário criado com sucesso via JSON!\"}");
            out.flush();

        } catch (JsonSyntaxException e) {
            System.err.println("Erro de sintaxe JSON ao criar usuário: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Status 400
            out.print("{\"message\": \"Requisição inválida: JSON malformado.\"}");
            out.flush();
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao criar usuário via JSON: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"message\": \"Erro ao salvar usuário no banco de dados: " + e.getMessage() + "\"}");
            out.flush();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao criar usuário via JSON: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"message\": \"Ocorreu um erro inesperado ao criar o usuário.\"}");
            out.flush();
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

    protected void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ResponseUsuarioDto> listaUsuarios = null;

        try {
            listaUsuarios = usuarioService.getUsuarios();
            String json = gson.toJson(listaUsuarios);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (RuntimeException e) {
            System.err.println("Erro ao carregar lista de usuários para JSON: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"Erro ao carregar a lista de usuários: " + e.getMessage() + "\"}");
            out.flush();
        } catch (Exception e) {
            System.err.println("Erro inesperado no controller ao gerar JSON de usuários: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"Ocorreu um erro inesperado ao carregar os usuários.\"}");
            out.flush();
        }
    }
}