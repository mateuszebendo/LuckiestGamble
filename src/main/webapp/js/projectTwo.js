$(document).ready(function() {
    $("#form-new-user").on("submit", function(event) {
        event.preventDefault();

        const newUser = {
            username: $("#username").val(),
            email: $("#email").val(),
            birthday: $("#birthday").val(),
            password: $("#password").val()
        };

        $.ajax({
            url: $(this).attr("action"),
            type: $(this).attr("method"),
            contentType: "application/json",
            data: JSON.stringify(newUser),
            success: function(response) {
                alert("Usuário criado com sucesso!");
                window.location.href = CONTEXT_PATH + "/app/portal/projeto_dois";
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
});