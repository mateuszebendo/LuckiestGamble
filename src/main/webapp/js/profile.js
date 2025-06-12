$("#btn-depositar").click(function (event) {
    event.preventDefault();

    let newAction = CONTEXT_PATH + "/app/transacao/deposito";

    $("#profile-saldo-form").attr("action", newAction);
    $("#profile-saldo-form").submit();
});

$("#btn-sacar").click(function (event) {
    event.preventDefault();

    let newAction = CONTEXT_PATH + "/app/transacao/saque";

    $("#profile-saldo-form").attr("action", newAction);
    $("#profile-saldo-form").submit();
});