$(document).ready(function() {
    $("#btn-depositar").click(function (event) {
        event.preventDefault();
        let currentAction = $("#profile-saldo-form").attr("action");
        if (!currentAction.endsWith('/')) {
            currentAction += '/';
        }
        let newAction = currentAction + "app/transacao/deposito";

        $("#profile-saldo-form").attr("action", newAction);
        $("#profile-saldo-form").submit();
    });

    $("#btn-sacar").click(function (event) {
        event.preventDefault();

        let currentAction = $("#profile-saldo-form").attr("action");
        if (!currentAction.endsWith('/')) {
            currentAction += '/';
        }
        let newAction = currentAction + "app/transacao/saque";

        $("#profile-saldo-form").attr("action", newAction);
        $("#profile-saldo-form").submit();
    });
});