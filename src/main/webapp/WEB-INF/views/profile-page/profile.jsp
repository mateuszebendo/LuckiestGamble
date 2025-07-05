<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layouts" prefix="my-l"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>

<my-l:_baseLayout pageTitle="Home" scriptFiles="${pageScripts}" styleFiles="${pageStyles}">
  <main id="profile-main-container">
    <%@include file="/WEB-INF/shared/sideBar.jspf" %>
    <div class="profile-sub-container">
      <%@include file="components/_profile-info.jsf" %>
      <%@include file="components/_history-info.jsf"%>
      <div class="profile-button-container">
        <my-c:submitButton buttonId="profileSwitchButton" text="Ver histórico" customClass="custom-btn button-secondary"/>
      </div>
    </div>
  </main>
  <script>
    const CONTEXT_PATH = "${pageContext.request.contextPath}";
  </script>
</my-l:_baseLayout>