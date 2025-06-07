<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ attribute name="scriptFiles" type="java.util.List" required="false"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%-- Scripts JavaScript Comuns --%>
<script src="${contextPath}/webjars/jquery/3.7.1/jquery.min.js"></script>
<script src="${contextPath}/webjars/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>

<%-- Scripts JavaScript Variáveis --%>
<c:forEach var="script" items="${scriptFiles}">
    <script src="${contextPath}/js/${script}.js"></script>
</c:forEach>
