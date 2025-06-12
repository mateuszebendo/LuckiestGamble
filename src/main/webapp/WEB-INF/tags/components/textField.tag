<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="label" type="java.lang.String" required="true" %>
<%@ attribute name="type" type="java.lang.String" required="false" %>
<%@ attribute name="value" type="java.lang.String" required="false" %>
<%@ attribute name="placeholder" type="java.lang.String" required="false" %>
<%@ attribute name="required" type="java.lang.Boolean" required="false" %>
<%@ attribute name="readonly" type="java.lang.Boolean" required="false" %>
<%@ attribute name="minlength" type="java.lang.Integer" required="false" %>
<%@ attribute name="maxlength" type="java.lang.Integer" required="false" %>
<%@ attribute name="inputClass" type="java.lang.String" required="false" %>
<%@ attribute name="min" type="java.lang.Number" required="false" %>
<%@ attribute name="max" type="java.lang.Number" required="false" %>
<%@ attribute name="containerClass" type="java.lang.String" required="false" %> <%-- NOVO ATRIBUTO --%>


<div id="${id}" class="form-group ${containerClass}">
    <label for="${id}">${label}</label>
    <input type="${type != null ? type : 'text'}"
           class="default-input ${inputClass}"
           name="${name}"
           value="${value}"
           placeholder="${placeholder}"
           <c:if test="${required}">required</c:if>
           <c:if test="${readonly}">readonly</c:if>
           <c:if test="${min}">min="${min}" </c:if>
           <c:if test="${max}">max="${max}" </c:if>
           <c:if test="${not empty minlength}">minlength="${minlength}"</c:if>
           <c:if test="${not empty maxlength}">maxlength="${maxlength}"</c:if>
    >
</div>