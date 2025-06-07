<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:set var="tituloPagina" value="Home - Luckiest Gamble"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@include file="/WEB-INF/shared/pageConfigHeader.jspf" %>

<main>
    <%@include file="/WEB-INF/shared/sideBar.jspf" %>
</main>

<%@include file="/WEB-INF/shared/pageConfigFooter.jspf" %>