$(document).ready(function() {
    $("#form-new-user").on("submit", function(event) {
        event.preventDefault();

        const newUser = {
            Nome: $("#username-input").val(),
            Email: $("#email-input").val(),
            DataNascimento: $("#birthday-input").val(),
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

    $("#switch-project-two").click(function (event) {
        event.preventDefault();

        const originalMain = $("#project-two-main-container.original");
        const secondaryMain = $("#project-two-main-container.secondary");

        originalMain.toggle();
        secondaryMain.toggle();

        if (secondaryMain.is(":visible") && !usersTableLoaded) {
            loadUsersTable();
            usersTableLoaded = true;
        }
    });

    $("#switch-project-two-secondary").click(function (event) {
        event.preventDefault();

        const originalMain = $("#project-two-main-container.original");
        const secondaryMain = $("#project-two-main-container.secondary");

        originalMain.toggle();
        secondaryMain.toggle();
    });

    function loadUsersTable() {
        const tableBody = $("#project-two-main-container.secondary table tbody");
        tableBody.empty();

        tableBody.append('<tr><td colspan="3">Carregando usuários...</td></tr>');

        $.ajax({
            url: CONTEXT_PATH + "/app/usuario/recuperar-usuarios",
            type: "GET",
            dataType: "json",
            success: function(users) {
                tableBody.empty();

                if (users && users.length > 0) {
                    $.each(users, function(index, user) {
                        const row = `
                            <tr>
                                <td>${user.Nome}</td>
                                <td>${user.Email}</td>
                                <td>${user.DataNascimento}</td>
                            </tr>
                        `;
                        tableBody.append(row);
                    });
                } else {
                    tableBody.append('<tr><td colspan="3">Nenhum usuário encontrado.</td></tr>');
                }
            },
            error: function(xhr, status, error) {
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
    }
});