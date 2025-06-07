<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ attribute name="text" type="java.lang.String" required="false" %>
<%@ attribute name="customClass" type="java.lang.String" required="false" %>
<%@ attribute name="buttonId" type="java.lang.String" required="false" %>
<%@ attribute name="onClick" type="java.lang.String" required="false" %>
<%@ attribute name="disabled" type="java.lang.Boolean" required="false" %>
<%@ attribute name="iconClass" type="java.lang.String" required="false" %>

<button type="submit"
        class="${customClass}"
        id="${buttonId}"
        <c:if test="${not empty onClick}">onclick="${onClick}"</c:if>
        <c:if test="${disabled}">disabled</c:if>
>
    <c:if test="${not empty iconClass}">
        <i class="${iconClass}"></i>
    </c:if>
    ${text != null ? text : 'Enviar'}
</button>