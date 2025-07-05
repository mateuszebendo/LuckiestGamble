$(document).ready(function() {
    $("#form-new-user").on("submit", function(event) {
        event.preventDefault();

        const newUser = {
            Nome: $("#username-input").val(),
            Email: $("#email-input").val(),
            DataNascimento: $("#birthday-input").val(),
            TipoUsuario: $("#user-type-select").val().toUpperCase(),
            Senha: $("#password-input").val()
        };

        $.ajax({
            url: $(this).attr("action"),
            type: $(this).attr("method"),
            contentType: "application/json",
            data: JSON.stringify(newUser),
            success: function(response) {
                alert("Usuário criado com sucesso!");
                window.location.href = CONTEXT_PATH + "/app/usuario/login";
            },
            error: function(xhr, status, error) {
                let errorMessage = "Erro ao criar usuário.";
                if (xhr.responseText) {
                    try {
                        const errorResponse = JSON.parse(xhr.responseText);
                        if (errorResponse.message) {
                            errorMessage = errorResponse.message;
                        }
                    } catch (e) {
                        errorMessage += " Detalhes: " + xhr.responseText;
                    }
                } else {
                    errorMessage += " Status: " + status + ", Erro: " + error;
                }
                alert(errorMessage);
            }
        });
    });

    let usersTableLoaded = false;

    $("#userManagerSwitchButton").click(function (event) {
        event.preventDefault();

        const formContainer = $(".user-manager-form-container");
        const tableContainer = $(".user-manager-table-container");

        formContainer.toggle();
        tableContainer.toggle();

        if (tableContainer.is(":visible") && !usersTableLoaded) {
            loadUsersTable();
            usersTableLoaded = true;
        } else {
            usersTableLoaded = false;
        }
    });

    $("#findUserButton").click(function (event) {
        event.preventDefault();

        const username = $("#usernameInput-input").val();
        const email = $("#emailInput-input").val();
        const birthday = $("#dateInput-input").val();
        const userType = $("#user-type-select-filter").val().toUpperCase();

        const userFilter = {
            Nome: username === '' ? null : username,
            Email: email === '' ? null : email,
            DataNascimento: birthday === '' ? null : birthday,
            TipoUsuario: userType === '' ? null : userType,
        };


        const tableBody = $(".table-scroll-container table tbody");
        tableBody.empty();

        tableBody.append('<tr><td colspan="3">Carregando usuários...</td></tr>');

        $.ajax({
            url: CONTEXT_PATH + "/app/usuario/recuperar-usuarios-filtrados",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(userFilter),
            success: (users) => appendUsers(tableBody, users),
            error: (xhr, status, error) => appendError(xhr, status, error, tableBody)
        });
    });

    function loadUsersTable() {
        const tableBody = $(".table-scroll-container table tbody");
        tableBody.empty();

        tableBody.append('<tr><td colspan="3">Carregando usuários...</td></tr>');

        $.ajax({
            url: CONTEXT_PATH + "/app/usuario/recuperar-usuarios",
            type: "GET",
            dataType: "json",
            success: (users) => appendUsers(tableBody, users),
            error: (xhr, status, error) => appendError(xhr, status, error, tableBody)
        });
    }

    function appendUsers(tableBody, users){
        tableBody.empty();
        if (users && users.length > 0) {
            $.each(users, function(index, user) {
                const row = `
                            <tr>
                                <td>${user.Nome}</td>
                                <td>${user.Email}</td>
                                <td>${user.DataNascimento}</td>
                                <td>${user.TipoUsuario}</td>
                            </tr>
                        `;
                tableBody.append(row);
            });
        } else {
            tableBody.append('<tr><td colspan="3">Nenhum usuário encontrado.</td></tr>');
        }
    }

    function appendError(xhr, status, error, tableBody){
        tableBody.empty();
        let errorMessage = "Erro ao carregar usuários.";
        if (xhr.responseJSON && xhr.responseJSON.error) {
            errorMessage = xhr.responseJSON.error;
        } else if (xhr.responseText) {
            errorMessage += " Detalhes: " + xhr.responseText;
        } else {
            errorMessage += " Status: " + status + ", Erro: " + error;
        }
        tableBody.append(`<tr><td colspan="3" style="color: red;">${errorMessage}</td></tr>`);
    }
});