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

$(document).ready(function() {
    $('#form-cadastro form').on('submit', function(event) {
        const $nomeInput = $("#floatingInputUser");
        const $emailInput = $("#floatingInputEmail");
        const $aniversarioInput = $("#floatingInputDate");
        const $senhaInput = $("#floatingPassword");
        const $confirmarSenhaInput = $("#floatingPasswordConfirm");

        const $erroNomeSpan = $("#erroNome");
        const $erroEmailSpan = $("#erroEmail");
        const $erroAniversarioSpan = $("#erroDataNascimento");
        const $erroSenhaSpan = $("#erroSenha");
        const $erroConfirmarSenhaSpan = $("#erroConfirmarSenha");

        let isValid = true; // Flag geral de validação

        $nomeInput.removeClass('is-invalid is-valid');
        $emailInput.removeClass('is-invalid is-valid');
        $aniversarioInput.removeClass('is-invalid is-valid');
        $senhaInput.removeClass('is-invalid is-valid');
        $confirmarSenhaInput.removeClass('is-invalid is-valid');

        $erroNomeSpan.text('');
        $erroEmailSpan.text('');
        $erroAniversarioSpan.text('');
        $erroSenhaSpan.text('');
        $erroConfirmarSenhaSpan.text('');

        if ($nomeInput.val().trim() === '') {
            $nomeInput.addClass('is-invalid');
            $erroNomeSpan.text('O campo Nome de Usuário é obrigatório.');
            isValid = false;
        } else {
            $nomeInput.addClass('is-valid');
        }

        if ($emailInput.val().trim() === '') {
            $emailInput.addClass('is-invalid');
            $erroEmailSpan.text('O campo Email é obrigatório.');
            isValid = false;
        } else if (!validarEmailFormato($emailInput.val())) {
            $emailInput.addClass('is-invalid');
            $erroEmailSpan.text('Por favor, insira um endereço de e-mail válido.');
            isValid = false;
        } else {
            $emailInput.addClass('is-valid');
        }

        const dataNascimentoStr = $aniversarioInput.val();
        if (dataNascimentoStr === '') {
            $aniversarioInput.addClass('is-invalid');
            $erroAniversarioSpan.text('A data de nascimento é obrigatória.');
            isValid = false;
        } else {
            const dataNascimento = new Date(dataNascimentoStr + 'T00:00:00');
            const hoje = new Date();

            hoje.setHours(0, 0, 0, 0);

            let idade = hoje.getFullYear() - dataNascimento.getFullYear();
            const mesAtual = hoje.getMonth();
            const diaAtual = hoje.getDate();
            const mesNascimento = dataNascimento.getMonth();
            const diaNascimento = dataNascimento.getDate();

            if (mesAtual < mesNascimento || (mesAtual === mesNascimento && diaAtual < diaNascimento)) {
                idade--;
            }

            if (idade < 18) {
                $aniversarioInput.addClass('is-invalid');
                $erroAniversarioSpan.text('Você deve ter pelo menos 18 anos.');
                isValid = false;
            } else {
                $aniversarioInput.addClass('is-valid');
            }
        }

        if ($senhaInput.val().trim() === '') {
            $senhaInput.addClass('is-invalid');
            $erroSenhaSpan.text('O campo Senha é obrigatório.');
            isValid = false;
        } else if ($senhaInput.val().length < 6) {
            $senhaInput.addClass('is-invalid');
            $erroSenhaSpan.text('A senha deve ter no mínimo 6 caracteres.');
            isValid = false;
        } else {
            $senhaInput.addClass('is-valid');
        }

        if ($confirmarSenhaInput.val().trim() === '') {
            $confirmarSenhaInput.addClass('is-invalid');
            $erroConfirmarSenhaSpan.text('A confirmação de senha é obrigatória.');
            isValid = false;
        } else if ($senhaInput.val().trim() !== $confirmarSenhaInput.val().trim()) {
            $confirmarSenhaInput.addClass('is-invalid');
            $erroConfirmarSenhaSpan.text('As senhas não coincidem.');
            isValid = false;
        } else {
            $confirmarSenhaInput.addClass('is-valid');
        }


        // if (!isValid) {
        //     event.preventDefault();
        // }
    });

    function validarEmailFormato(email) {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }
});