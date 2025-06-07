package org.cefet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.cefet.dtos.ResponseUsuarioDto;

import java.io.IOException;

@WebFilter("/app/portal/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        HttpSession session = httpRequest.getSession(false);

        Cookie[] cookies = httpRequest.getCookies();
        boolean cookieExists = false;
        String usernameFromCookie = null;
        String roleFromCookie = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("loggedInUser".equals(cookie.getName())) {
                    cookieExists = true;
                    String[] userData = cookie.getValue().split(":");
                    if (userData.length == 2) {
                        usernameFromCookie = userData[0];
                        roleFromCookie = userData[1];
                    }
                    break;
                }
            }
        }

        if (session == null || session.getAttribute("currentUser") == null || !cookieExists) {
            // Não autenticado
            httpResponse.sendRedirect(contextPath + "/app/usuario/login");
            return;
        }

        ResponseUsuarioDto currentUser = (ResponseUsuarioDto) session.getAttribute("currentUser");
        if (currentUser != null) {
            request.setAttribute("username", currentUser.getNome());
            request.setAttribute("role", currentUser.getTipoUsuario().toString());
        }

        chain.doFilter(request, response);
    }

}