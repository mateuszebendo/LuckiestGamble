<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layouts" prefix="my-l"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>

<my-l:_baseLayout pageTitle="Home" scriptFiles="${pageScripts}" styleFiles="${pageStyles}">
  <main id="profile-main-container">
    <%@include file="/WEB-INF/shared/sideBar.jspf" %>
    <div class="profile-sub-container">
      <my-l:defaultForm endpoint="" method="" title="Informações" formId="profile-user-info" message="">
        <my-c:textField id="username" name="username" label="Usuário" type="text" value="${usuarioRequest.nome}" inputClass="default-input" readonly="${true}"/>
        <my-c:textField id="email" name="email" label="Email" type="email" value="${usuarioRequest.email}" inputClass="default-input" readonly="${true}"/>
        <my-c:textField id="tipoUsuario" name="tipoUsuario" label="Cargo" type="text" value="${usuarioRequest.tipoUsuario}" inputClass="default-input" readonly="${true}"/>
        <my-c:textField id="birthday" name="birthday" label="Data de Nascimento" type="text" value="${usuarioRequest.dataNascimento}" inputClass="default-input" readonly="${true}"/>
      </my-l:defaultForm>
      <my-l:defaultForm endpoint="app/transacao/deposito" method="post" title="Saldo - R$${usuarioRequest.saldo}" formId="profile-saldo-form" message="${message}">
        <my-c:textField id="newAmount" name="newAmount" label="Depósito" type="number" inputClass="default-input"/>
        <my-c:submitButton text="Depositar" customClass="custom-btn button-primary"/>
        <hr id="profile-saldo-hr"/>
        <my-c:textField id="decreaseAmount" name="decreaseAmount" label="Saque" type="number" inputClass="default-input" readonly="${usuarioRequest.saldo <= 0}"/>
        <my-c:submitButton text="Sacar" customClass="custom-btn button-secondary" disabled="${usuarioRequest.saldo <= 0}"/>
      </my-l:defaultForm>
    </div>
  </main>
</my-l:_baseLayout>