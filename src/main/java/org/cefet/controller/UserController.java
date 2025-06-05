package org.cefet.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cefet.dtos.usuario.CreateUsuarioDto;
import org.cefet.dtos.usuario.LoginUsuarioDto;
import org.cefet.services.UsuarioService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name="UserController", urlPatterns = { "/app/usuario", "/app/usuario/*"})
public class UserController extends HttpServlet {

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
        String action = (pathInfo == null || pathInfo.isEmpty()) ? "/login" : pathInfo;

        if("GET".equalsIgnoreCase(httpMethod)){
            if(action.equals("/login")){
                getPage(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else if("POST".equalsIgnoreCase(httpMethod)){
            if(action.equals("/cadastro")){
                cadastrarUsuario(request, response);
            } else if(action.equals("/login")) {
                login(request, response);
            }  else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private void getPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/login-page/login.jsp");
        view.forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");

        LoginUsuarioDto loginUsuarioDto = new LoginUsuarioDto(nome, senha);

        try {
            var usuarioResponse = usuarioService.login(loginUsuarioDto);
            request.setAttribute("usuario", usuarioResponse);
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/home-page/home.jsp");
            view.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("mensagemErro", e.getMessage());
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/login-page/login.jsp");
            view.forward(request, response);
        }
    }

    private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senhaPrincipal");
        String dataNascimentoStr = request.getParameter("dataNascimento");

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dataNascimento = formatter.parse(dataNascimentoStr);

            CreateUsuarioDto createUsuarioDto = new CreateUsuarioDto(nome, email, senha, dataNascimento);
            usuarioService.saveUsuario(createUsuarioDto);
        } catch (Exception e) {
            request.setAttribute("mensagemErro", e.getMessage());
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/login-page/login.jsp");
            view.forward(request, response);
        }

        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/login-page/login.jsp");
        view.forward(request, response);
    }
}