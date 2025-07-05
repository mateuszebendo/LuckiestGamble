<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>
<%@ attribute name="games" type="java.util.HashMap" required="true"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="card-container">
    <c:forEach var="game" items="${games}" varStatus="loopStatus">
        <div class="card-component">
            <c:set var="upperIcon" value="${fn:toUpperCase(game.key)}"/>
            <img src="${contextPath}/svgs/${game.key}.svg" alt="${upperIcon} Icon" class="card-icon"/>
            <hr class="card-hr"/>
            <p class="card-title">${upperIcon}</p>
            <c:if test="${not empty game.value}">
                <p class="card-description">${game.value}</p>
            </c:if>
            <c:if test="${loopStatus.index % 2 != 0}">
                <my-c:submitButton onClick="window.location.href = '${contextPath}/app/games/${game.key}';" customClass="button-primary" text="Jogar"/>
            </c:if>
            <c:if test="${loopStatus.index % 2 == 0}">
                <my-c:submitButton onClick="window.location.href = '${contextPath}/app/games/${game.key}';" customClass="button-secondary" text="Jogar"/>
            </c:if>
        </div>
    </c:forEach>
</div>