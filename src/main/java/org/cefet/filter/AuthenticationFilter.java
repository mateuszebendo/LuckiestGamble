package org.cefet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.cefet.dtos.ResponseUsuarioDto;
import org.cefet.services.UsuarioService;
import org.cefet.utils.UserSession;
import org.cefet.models.UsuarioModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@WebFilter("/app/*")
public class AuthenticationFilter implements Filter {

    private UsuarioService usuarioService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            usuarioService = new UsuarioService();
        } catch (SQLException e) {
            throw new ServletException("Erro ao inicializar UsuarioService no filtro.", e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        HttpSession session = httpRequest.getSession(false);

        ResponseUsuarioDto currentUser = null;
        if (session != null) {
            currentUser = (ResponseUsuarioDto) session.getAttribute("currentUser");
        }

        if (currentUser == null) {
            Cookie[] cookies = httpRequest.getCookies();
            String usernameFromCookie = null;
            String roleFromCookie = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("loggedInUser".equals(cookie.getName())) {
                        String[] userData = cookie.getValue().split(":");
                        if (userData.length == 2) {
                            usernameFromCookie = userData[0];
                            roleFromCookie = userData[1];
                        }

                        if (usernameFromCookie != null) {
                            try {
                                UsuarioModel userFromDb = usuarioService.getUsuarioByNome(usernameFromCookie);

                                if (userFromDb != null && userFromDb.getTipoUsuario().toString().equals(roleFromCookie)) {
                                    currentUser = new ResponseUsuarioDto(userFromDb);

                                    HttpSession newSession = httpRequest.getSession(true);
                                    UserSession.setUsuario(httpRequest, currentUser);
                                    System.out.println("Sessão restaurada para usuário: " + currentUser.getNome());
                                } else {
                                    System.out.println("Cookie 'loggedInUser' inválido: usuário não encontrado ou role mismatch.");
                                    expireCookie(httpResponse, cookie);
                                }
                            } catch (NoSuchElementException e) {
                                System.err.println("Usuário do cookie '" + usernameFromCookie + "' não encontrado no BD. Expirando cookie.");
                                expireCookie(httpResponse, cookie);
                            } catch (Exception e) {
                                System.err.println("Erro inesperado ao validar cookie de autenticação: " + e.getMessage());
                                expireCookie(httpResponse, cookie);
                            }
                        } else {
                            System.out.println("Cookie 'loggedInUser' malformado. Expirando.");
                            expireCookie(httpResponse, cookie);
                        }
                        break;
                    }
                }
            }
        }

        boolean isPublicLoginPath = requestURI.startsWith(contextPath + "/app/usuario/login");
        boolean isPublicCadastroPath = requestURI.startsWith(contextPath + "/app/usuario/cadastro");
        boolean isPublicCriarJsonPath = requestURI.startsWith(contextPath + "/app/usuario/criarJson");
        boolean isPublicEntrarPath = requestURI.startsWith(contextPath + "/app/usuario/entrar");

        if (currentUser == null) {
            if (!isPublicLoginPath && !isPublicCadastroPath && !isPublicCriarJsonPath && !isPublicEntrarPath) {
                httpResponse.sendRedirect(contextPath + "/app/usuario/login");
                return;
            }
        }

        if (currentUser != null && (isPublicLoginPath || isPublicCadastroPath || isPublicEntrarPath)) {
            httpResponse.sendRedirect(contextPath + "/app/portal/home");
            return;
        }

        if (currentUser != null) {
            httpRequest.setAttribute("usuarioRequest", currentUser);

            String userColorPreference = null;
            Cookie[] cookiesAfterLogin = httpRequest.getCookies();
            if (cookiesAfterLogin != null) {
                for (Cookie cookie : cookiesAfterLogin) {
                    if ("userColorPreference".equals(cookie.getName())) {
                        userColorPreference = cookie.getValue();
                        break;
                    }
                }
            }
            httpRequest.setAttribute("userColorPreference", userColorPreference);
        }

        chain.doFilter(request, response);
    }

    private void expireCookie(HttpServletResponse response, Cookie cookie) {
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void destroy() {
    }
}