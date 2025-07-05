<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layouts" prefix="my-l"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>

<my-l:_baseLayout pageTitle="Home" scriptFiles="${pageScripts}" styleFiles="${pageStyles}">
    <main id="user-manager-main-container">
        <%@include file="/WEB-INF/shared/sideBar.jspf" %>
        <div class="user-manager-sub-container">
         <%@include file="components/_user-form.jsf"%>
         <%@include file="components/_user-history.jsf"%>
        </div>
        <div class="user-manager-button-container">
            <my-c:submitButton buttonId="userManagerSwitchButton" text="Ver usuários" customClass="custom-btn button-secondary"/>
        </div>
    </main>
  <script>
    const CONTEXT_PATH = "${pageContext.request.contextPath}";
  </script>
</my-l:_baseLayout>