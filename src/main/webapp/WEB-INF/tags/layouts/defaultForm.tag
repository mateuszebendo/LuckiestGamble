<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@ attribute name="endpoint" type="java.lang.String" required="true"%>
<%@ attribute name="method" type="java.lang.String" required="true"%>
<%@ attribute name="title" type="java.lang.String" required="false"%>
<%@ attribute name="formId" type="java.lang.String" required="false"%>
<%@ attribute name="errorMessage" type="java.lang.String" required="false"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<form method="${method}" action="${contextPath}/${endpoint}" id="${formId}" class="default-form">
    <c:if test="${not empty errorMessage}">
        <p class="default-form-error-message"><img src="${contextPath}/svgs/info.svg" alt=""/> - ${errorMessage}</p>
    </c:if>
    <c:if test="${not empty title}">
        <h1>${title}</h1>
    </c:if>
    <jsp:doBody/>
</form>