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

$("#profileSwitchButton").click(function (event) {
   event.preventDefault();

   const switchButton = $("#profileSwitchButton");
   const profileContainer = $(".profile-info-container");
   const historyContainer = $(".profile-history-container");

   profileContainer.toggle();
   historyContainer.toggle();

   if(historyContainer.is(":visible"))
   {
       switchButton.text("Ver perfil");
   } else {
       switchButton.text("Ver histórico");
   }
});