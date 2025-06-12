<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/tlds/stringFunctions" prefix="strfn" %>

<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="options" type="java.util.Collection" required="true" %>
<%@ attribute name="label" type="java.lang.String" required="true" %>
<%@ attribute name="selectId" type="java.lang.String" required="false" %>
<%@ attribute name="selectClass" type="java.lang.String" required="false" %>
<%@ attribute name="containerClass" type="java.lang.String" required="false" %>


<div id="${id}" class="form-group ${containerClass}"> <%-- Div pai com ID e classe de contêiner --%>
    <label for="${selectId != null ? selectId : name}" class="dynamic-label">${label}</label>
    <select name="${name}" id="${selectId != null ? selectId : name}" class="dynamic-select ${selectClass}">
        <c:forEach var="option" items="${options}">
            <option value="${fn:toLowerCase(option)}" class="dynamic-option">${strfn:toNormalCase(option)}</option>
        </c:forEach>
    </select>
</div>