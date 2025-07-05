package org.cefet.controller.base;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.utils.UserSession;

import java.io.IOException;

public abstract class BaseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String httpMethod = request.getMethod();
        String pathInfo = request.getPathInfo();
        String action = (pathInfo == null || pathInfo.isEmpty()) ? "/" : pathInfo;

        String fullRequestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        boolean isPublicPath = fullRequestURI.startsWith(contextPath + "/app/usuario/login") ||
                fullRequestURI.startsWith(contextPath + "/app/usuario/cadastro") ||
                fullRequestURI.startsWith(contextPath + "/app/usuario/criarJson");

        ResponseUsuarioDto usuarioDto = null;
        usuarioDto = UserSession.getUsuario(request, response);

        if (usuarioDto == null && !isPublicPath) {
            response.sendRedirect(request.getContextPath() + "/app/usuario/login");
            return;
        }

        if (usuarioDto != null && (fullRequestURI.startsWith(contextPath + "/app/usuario/login") || fullRequestURI.startsWith(contextPath + "/app/usuario/cadastro"))) {
            response.sendRedirect(request.getContextPath() + "/app/portal/home");
            return;
        }

        if (usuarioDto != null) {
            request.setAttribute("usuarioRequest", usuarioDto);
        }

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

    protected void handleGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method not supported for this action.");
    }

    protected void handlePostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Método POST não implementado para esta ação.");
    }

    protected void handlePutRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "PUT method not supported for this action.");
    }

    protected void handleDeleteRequest(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "DELETE method not supported for this action.");
    }
}
