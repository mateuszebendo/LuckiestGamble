<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layouts" prefix="my-l"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>

<my-l:_baseLayout pageTitle="Home" scriptFiles="${pageScripts}" styleFiles="${pageStyles}">
    <main id="games-main-container">
        <%@include file="/WEB-INF/shared/sideBar.jspf" %>
        <my-l:gamesCardsLayout games="${games}"/>
    </main>
</my-l:_baseLayout>