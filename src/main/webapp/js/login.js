$("#btn-cadastro").click(function (event) {
    event.preventDefault()
    $("#form-cadastro").show();
    $("#form-login").hide();
});

$("#btn-login").click(function (event) {
    event.preventDefault()
    $("#form-cadastro").hide();
    $("#form-login").show();
});

$("#btn-criar-conta").click(function (event) {

})