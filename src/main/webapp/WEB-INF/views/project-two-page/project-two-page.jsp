<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layouts" prefix="my-l"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>

<my-l:_baseLayout pageTitle="Home" scriptFiles="${pageScripts}" styleFiles="${pageStyles}">
  <main id="project-two-main-container">
    <%@include file="/WEB-INF/shared/sideBar.jspf" %>
    <div id="cadastro-usuario" class="project-two-sub-container">
      <my-l:defaultForm endpoint="app/usuario/novo-usuario" method="post" title="Criar novo usuário" formId="form-new-user" message="${message}">
        <my-c:textField id="username" name="username" label="Usuário" type="text" placeholder="Usuário" inputClass="default-input" required="${true}"/>
        <my-c:textField id="email" name="email" label="Email" type="email" placeholder="Email" inputClass="default-input" required="${true}"/>
        <my-c:textField id="birthday" name="birthday" label="Data de Nascimento" type="date" inputClass="default-input" required="${true}"/>
        <my-c:textField id="password" name="password" label="Senha" type="password" placeholder="Senha" inputClass="default-input" required="${true}"/>
        <my-c:submitButton text="Criar usuário" customClass="custom-btn button-primary"/>
      </my-l:defaultForm>
    </div>
  </main>
  <script>
    const CONTEXT_PATH = "${pageContext.request.contextPath}";
  </script>
</my-l:_baseLayout>