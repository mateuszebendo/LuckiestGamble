package org.cefet.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.cefet.dtos.ResponseUsuarioDto;

import java.io.IOException;

public class UserSession {

    public static void setUsuario(HttpServletRequest request, ResponseUsuarioDto usuario) {
        HttpSession session = request.getSession();
        session.setAttribute("currentUser", usuario);
    }

    public static ResponseUsuarioDto getUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // Não cria nova sessão se não existir

        ResponseUsuarioDto responseUsuarioDto = null;
        if (session != null) {
            responseUsuarioDto = (ResponseUsuarioDto) session.getAttribute("currentUser");
        }

        if (responseUsuarioDto == null) {
            response.sendRedirect(request.getContextPath() + "/app/usuario/login");
            return null;
        }

        return responseUsuarioDto;
    }
}