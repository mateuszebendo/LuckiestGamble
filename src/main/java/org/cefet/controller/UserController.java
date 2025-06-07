package org.cefet.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cefet.dtos.usuario.CreateUsuarioDto;
import org.cefet.dtos.usuario.LoginUsuarioDto;
import org.cefet.dtos.usuario.ResponseUsuarioDto;
import org.cefet.services.UsuarioService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                sendLoginPage(request, response, null);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else if("POST".equalsIgnoreCase(httpMethod)){
            if(action.equals("/login")){
                sendLoginPage(request, response, null);
            } else if(action.equals("/cadastro")){
                cadastrarUsuario(request, response);
            } else if(action.equals("/entrar")) {
                login(request, response);
            }  else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("username");
        String senha = request.getParameter("password");

        LoginUsuarioDto loginUsuarioDto = new LoginUsuarioDto(nome, senha);

        try {
            List<String> pageStyles = new ArrayList<>();
            ResponseUsuarioDto usuarioResponse = usuarioService.login(loginUsuarioDto);

            pageStyles.add("home");
            pageStyles.add("sideBar");

            request.setAttribute("usuarioRequest", usuarioResponse);
            request.setAttribute("pageStyles", pageStyles);
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/home-page/home.jsp");
            view.forward(request, response);
        } catch (Exception e) {
            sendLoginPage(request, response, e);
        }
    }

    private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("username");
        String email = request.getParameter("email");
        String senha = request.getParameter("username");
        String dataNascimentoStr = request.getParameter("birthday");

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dataNascimento = formatter.parse(dataNascimentoStr);

            CreateUsuarioDto createUsuarioDto = new CreateUsuarioDto(nome, email, senha, dataNascimento);
            usuarioService.saveUsuario(createUsuarioDto);
        } catch (Exception e) {
            sendLoginPage(request, response, e);
        }

        sendLoginPage(request, response, null);
    }

    private void sendLoginPage(HttpServletRequest request, HttpServletResponse response, Exception error) throws ServletException, IOException {
        List<String> pageScripts = new ArrayList<>();
        List<String> pageStyles = new ArrayList<>();
        pageScripts.add("login");
        pageStyles.add("login");

        request.setAttribute("pageScripts", pageScripts);
        request.setAttribute("pageStyles", pageStyles);

        if(error != null){
            request.setAttribute("mensagemErro", error.getMessage());
        }

        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/login-page/login.jsp");
        view.forward(request, response);
    }
}