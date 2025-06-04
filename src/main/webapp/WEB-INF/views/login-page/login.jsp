<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:set var="tituloPagina" value="Login - Luckiest Gamble"/>

<%@include file="/WEB-INF/shared/pageConfigHeader.jspf"%>

<main class="d-flex align-items-center justify-content-center min-vh-100">
    <%@include file="components/loginForm.jspf"%>
    <%@include file="components/cadastroForm.jspf"%>
</main>

<%@include file="/WEB-INF/shared/pageConfigFooter.jspf"%>