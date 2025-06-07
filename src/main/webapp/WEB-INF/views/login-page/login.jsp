<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layouts" prefix="my-l"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>


<my-l:_baseLayout pageTitle="Login" scriptFiles="${pageScripts}" styleFiles="${pageStyles}">
    <main id="login-container">
        <my-l:defaultForm endpoint="app/usuario/entrar" method="post" title="Login" formId="form-login" errorMessage="${mensagemErro}">
            <my-c:textField id="username" name="username" label="Usuário" type="text" placeholder="Digite seu usuário" inputClass="default-input"/>
            <my-c:textField id="password" name="password" label="Senha" type="password" placeholder="Digite sua senha" inputClass="default-input"/>
            <my-c:submitButton text="Entrar" customClass="custom-btn button-primary"/>
            <p id="login-text">Não possui conta?</p>
            <my-c:submitButton text="Cadastrar-se" buttonId="btn-cadastro" customClass="custom-btn button-primary"/>
        </my-l:defaultForm>

        <my-l:defaultForm endpoint="app/usuario/cadastro" method="post" title="Cadastro" formId="form-cadastro" errorMessage="${mensagemErro}">
            <my-c:textField id="username" name="username" label="Usuário" type="text" placeholder="Digite seu usuário" inputClass="default-input"/>
            <my-c:textField id="email" name="email" label="Email" type="email" placeholder="Digite seu email" inputClass="default-input"/>
            <my-c:textField id="birthday" name="birthday" label="Data de Nascimento" type="date" inputClass="default-input"/>
            <my-c:textField id="password" name="password" label="Senha" type="password" placeholder="Digite sua senha" inputClass="default-input"/>
            <my-c:submitButton text="Criar conta" customClass="custom-btn button-primary"/>
            <p id="login-text">Já possui conta?</p>
            <my-c:submitButton text="Entrar" buttonId="btn-login" customClass="custom-btn button-primary"/>
        </my-l:defaultForm>
    </main>
</my-l:_baseLayout>

