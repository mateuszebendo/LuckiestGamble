<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layouts" prefix="my-l"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %> <%-- Added for fn: if needed --%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<my-l:_baseLayout pageTitle="Project One" scriptFiles="${pageScripts}" styleFiles="${pageStyles}">
    <main id="project-one-main-container">
        <%@include file="/WEB-INF/shared/sideBar.jspf" %>
        <div class="project-one-sub-container">
            <h1>Histórico de Transações</h1>
            <div class="table-scroll-container">
                <table>
                    <thead>
                    <tr>
                        <th>ID da Transação</th>
                        <th>Tipo</th>
                        <th>Valor</th>
                        <th>Data/Hora</th>
                        <th>Descrição</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${not empty transacaoList}">
                            <c:forEach var="transacao" items="${transacaoList}">
                                <tr>
                                    <td>${transacao.transacaoId}</td>
                                    <td>${transacao.tipoTransacao.name()}</td>
                                    <td>R$ ${transacao.valor}</td>
                                    <td>${transacao.dataHora}</td>
                                    <td>${transacao.descricao}</td>
                                    <td>${transacao.status.name()}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6">Nenhuma transação encontrada.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
        <my-l:defaultForm endpoint="app/transacao/deletar" method="post" title="Deletar Transação" formId="project-one-transacao-form" message="${message}">
            <my-c:textField id="transacaoId" name="transacaoId" label="ID da Transação" type="number" inputClass="default-input" required="true"/>
            <my-c:submitButton buttonId="btn-deletar-transacao" text="Deletar" customClass="custom-btn button-primary"/>
        </my-l:defaultForm>
    </main>
</my-l:_baseLayout>