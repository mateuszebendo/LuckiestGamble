<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="styleFiles" type="java.util.List" required="true" %>
<%@ attribute name="pageTitle" type="java.lang.String" required="true" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<head>
    <meta charset="UTF-8">
    <title>${pageTitle}</title>
    <link rel="icon" href="${contextPath}/svgs/poker.svg" type="image/svg+xml">

    <c:set var="contextPath" value="${pageContext.request.contextPath}" />

    <%-- Estilos CSS Variáveis --%>
    <c:forEach var="file" items="${styleFiles}">
        <link rel="stylesheet" href="${contextPath}/css/${file}.css" type="text/css"/>
    </c:forEach>

    <%-- Estilos CSS Comuns --%>
    <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/5.3.2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${contextPath}/css/style.css" type="text/css"/>
</head>