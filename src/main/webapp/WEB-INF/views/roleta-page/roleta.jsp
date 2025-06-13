<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layouts" prefix="my-l"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<my-l:_baseLayout pageTitle="Home" scriptFiles="${pageScripts}" styleFiles="${pageStyles}">
    <main id="roleta-main-container">
        <%@include file="/WEB-INF/shared/sideBar.jspf" %>
        <div class="roleta-sub-container">
            <div id="roleta-canvas">
                <h1>Gire a Roleta!</h1>
                <canvas id="canvas" width="500" height="500"></canvas>
                <div id="result"></div>
                <button id="spin">GIRAR</button>
            </div>
            <div id="roleta-options-container">
                <h4>Saldo - R$${usuarioRequest.saldo}</h4>
                <p id="roleta-message" class="default-form-message" style="display: none"><img src="${contextPath}/svgs/info.svg" alt=""/></p>
                <my-c:textField id="valor-aposta" name="valor-aposta" label="Valor" type="number" inputClass="default-input"/>
                <my-c:dinamicSelect id="bet-type-select-container" selectId="bet-type-select" name="apostas" options="${roleta.odds.keySet()}" label="Aposta: "/>
                <my-c:dinamicApostaGroup/>
                <my-c:submitButton isNotSubmit="true" buttonId="btn-criar-aposta" text="Criar nova aposta!" customClass="custom-btn button-primary"/>
            </div>
        </div>
    </main>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script> <%-- Inclua jQuery aqui se não estiver no _baseLayout --%>
    <script>
        const SALDO_USUARIO = ${usuarioRequest.saldo};
        const USUARIO_ID = ${usuarioRequest.usuarioId};
        const CONTEXT_PATH = "${contextPath}";
        const JOGO_ID = 1;
        const ROLETA_ODDS = {
            <c:forEach var="entry" items="${roleta.odds}" varStatus="loop">
            "${entry.key}": ${entry.value}<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
        };
        let validateResultado = () => {};
        let dadosParaServlet = null;
    </script>
</my-l:_baseLayout>