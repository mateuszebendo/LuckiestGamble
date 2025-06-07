<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layouts" prefix="my-l"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>

<my-l:_baseLayout pageTitle="Home" scriptFiles="${pageScripts}" styleFiles="${pageStyles}">
    <main id="home-main-container">
        <%@include file="/WEB-INF/shared/sideBar.jspf" %>
        <div id="home-info-container">

            <c:if test="${not empty requestScope.usuarioRequest}">
                <h1>Bem vindo, ${requestScope.usuarioRequest.nome}!</h1>
                <hr id="home-line"/>

                <c:if test="${requestScope.usuarioRequest.tipoUsuario == 'COMUM'}">
                    <p>
                        <strong>Texto para usuario comum</strong>
                        Integer id nulla eget turpis rutrum ultrices scelerisque a orci. Etiam at nunc iaculis, blandit nisi ac, pharetra diam. Sed ornare erat felis, in sagittis dui consequat vitae. Donec sagittis lorem id pellentesque consequat. Nam malesuada quam nulla, sed vehicula risus cursus sit amet. Mauris nec nisl varius, auctor nunc non, tempor metus. Integer sed ultricies sem. Integer ac massa sodales elit mattis feugiat. Integer condimentum dignissim leo id posuere. Nam ut rhoncus nisi, id placerat metus. Etiam sagittis lacus et fringilla egestas. Nam sit amet ornare nisl. Nam nunc dui, varius eu leo eu, vulputate gravida augue. Etiam eget eros dolor. Donec consectetur justo lectus, convallis rhoncus justo sodales faucibus.
                    </p>
                </c:if>

                <c:if test="${requestScope.usuarioRequest.tipoUsuario == 'ADMIN'}">
                    <p>
                    <p><strong>Texto para admnistrador</strong></p>
                    Nunc in tortor eget sapien fermentum sollicitudin sed vel turpis. Vivamus venenatis ipsum nec nulla luctus, mollis auctor massa malesuada. Aenean pulvinar, lacus nec aliquet porta, urna purus laoreet justo, consectetur tincidunt justo arcu vel sem. Nunc id justo lacinia, vestibulum risus non, ullamcorper eros. Praesent rhoncus dui quis lectus convallis semper. Quisque id nulla felis. Suspendisse sit amet magna id quam tincidunt faucibus.
                    </p>
                </c:if>
            </c:if>

            <c:if test="${empty requestScope.usuarioRequest}">
                <p>Nenhum usuário encontrado. Por favor, faça login.</p>
            </c:if>

        </div>
    </main>
</my-l:_baseLayout>
