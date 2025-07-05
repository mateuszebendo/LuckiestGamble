$(document).ready(function() {
    const HIDE_CLASS = 'dinamic-hidden-input';

    const betTypeMap = {
        'aposta_direta': 'aposta_direta',
        'split': 'aposta_split',
        'street': 'aposta_street',
        'corner': 'aposta_corner',
        'six_line': 'aposta_six_line',
        'duzia': 'aposta_duzia',
        'coluna': 'aposta_coluna',
        'alto_baixo': 'aposta_alto_baixo',
        'par_impar': 'aposta_par_impar',
        'vermelho_preto': 'aposta_vermelho_preto'
    };

    function toggleBetInputs(selectedBetType) {
        $('.form-group').addClass(HIDE_CLASS);
        $('#spin').addClass(HIDE_CLASS);
        $('#bet-type-select-container').removeClass(HIDE_CLASS);
        $('#valor-aposta').removeClass(HIDE_CLASS);

        if (selectedBetType && betTypeMap[selectedBetType]) {
            const targetId = betTypeMap[selectedBetType];
            $('#' + targetId).removeClass(HIDE_CLASS);
        }
    }

    function createAposta(){
        $.ajax({
            url: CONTEXT_PATH + '/app/aposta/roleta',
            method: 'POST',
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(dadosParaServlet),
            dataType: 'json',
            beforeSend: function() {
                $('#btn-criar-aposta').prop('disabled', true);
                $('#spin').prop('disabled', true);
                $("#roleta-message").text("Registrando aposta...").show();
            },
            success: function(response) {
                if (response.status === 'sucesso') {
                    $("#roleta-message").text("Aposta registrada! Saldo atualizado.").show();
                } else {
                    $("#roleta-message").text("Erro ao registrar aposta: " + response.mensagem).show();
                    $('#btn-criar-aposta').prop('disabled', false);
                    $('#spin').prop('disabled', false);
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $("#roleta-message").text("Erro de comunicação com o servidor.").show();
                $('#btn-criar-aposta').prop('disabled', false);
                $('#spin').prop('disabled', false);
            }
        });
    }

     validateResultado = (resultadoRoleta) => {
        const tipoApostaSelecionado = $('#bet-type-select').val();
        const tipoApostaValue = betTypeMap[tipoApostaSelecionado];
        const $actualInputElement = findInputApostaDetalhe(tipoApostaSelecionado);
        const valorApostaDetalhe = $actualInputElement.val();
        const apostaSplitType = ['aposta_split', 'aposta_corner', 'aposta_street', 'aposta_six_line'];
        const apostaGroupType = ['aposta_alto_baixo', 'aposta_coluna', 'aposta_duzia'];
        const apostaOneOfAKindType = ['aposta_vermelho_preto', 'aposta_par_impar'];
        let text = "";

        if(tipoApostaValue === 'aposta_direta'){
            text = `RESULTADO ${resultadoRoleta}, APOSTA ${valorApostaDetalhe}`;
            if(valorApostaDetalhe === resultadoRoleta){
                dadosParaServlet.resultado = `VITÓRIA. ${text}`;
                dadosParaServlet.valor = ROLETA_ODDS[tipoApostaSelecionado];
            } else {
                dadosParaServlet.resultado = `DERROTA. ${text}`;
            }
        } else if(apostaSplitType.some(value => value === tipoApostaValue)){
            text = `RESULTADO ${resultadoRoleta}, APOSTA ${valorApostaDetalhe}`;
            if(verifyNumberInSplit(valorApostaDetalhe, resultadoRoleta)){
                dadosParaServlet.resultado = `VITÓRIA. ${text}`;
            } else {
                dadosParaServlet.resultado = `DERROTA. ${text}`;
            }
        } else if(apostaGroupType.some(value => value === tipoApostaValue)){
            text = `RESULTADO ${resultadoRoleta}, APOSTA ${valorApostaDetalhe}`;
            if(verifyNumberInGroup(tipoApostaValue, valorApostaDetalhe, resultadoRoleta)){
                dadosParaServlet.resultado = `VITÓRIA. ${text}`;
            } else {
                dadosParaServlet.resultado = `DERROTA. ${text}`;
            }
        } else if(apostaOneOfAKindType.some(value => value === tipoApostaValue)){
            text = `RESULTADO ${resultadoRoleta}, APOSTA ${valorApostaDetalhe}`;
            if(verifyNumberInOneOfAKind(tipoApostaValue, valorApostaDetalhe, resultadoRoleta)){
                dadosParaServlet.resultado = `VITÓRIA. ${text}`;
            } else {
                dadosParaServlet.resultado = `DERROTA. ${text}`;
            }
        }

        return text;
    }

    function verifyNumberInSplit(split, number){
        const values = split.split("-");
        return values.some(value => Number.parseInt(value) === number);
    }

    function verifyNumberInGroup(groupType, selectGroup, result){
        switch (groupType){
            case 'aposta_alto_baixo':
                if(selectGroup === 'baixo (1-18)'){
                    return result >= 1 && result <= 18;
                } else if(selectGroup === 'alto (19-36)'){
                    return result >= 19 && result <= 36;
                }
                break;
            case 'aposta_coluna':
                if(selectGroup === '1ª coluna (1,4,...,34)'){
                    return getNumerosColunaRoleta(1).some(result);
                } else if(selectGroup === '2ª coluna (2,5,...,35)'){
                    return getNumerosColunaRoleta(2).some(result);
                } else if(selectGroup === '3ª coluna (3,6,...,36)'){
                    return getNumerosColunaRoleta(3).some(result);
                }
                break;
            case 'aposta_duzia':
                if(selectGroup === '1ª dúzia (1-12)'){
                    return result >= 1 && result <= 12;
                } else if(selectGroup === '2ª dúzia (13-24)'){
                    return result >= 13 && result <= 24;
                } else if(selectGroup === '3ª dúzia (25-36)'){
                    return result >= 25 && result <= 36;
                }
                break;
        }
    }

    function verifyNumberInOneOfAKind(kind, selectedKind, result){
        switch (kind){
            case 'aposta_vermelho_preto':
                return getNumerosPorCorRoleta(selectedKind).some(value => value === result);
            case 'aposta_par_impar':
                if(selectedKind === 'par'){
                    return result % 2 === 0;
                } else if(selectedKind === 'impar'){
                    return result % 2 !== 0;
                }
                break;
        }
    }

    function getNumerosPorCorRoleta(cor) {
        const numerosVermelhos = [
            1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36
        ];
        const numerosPretos = [
            2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35
        ];

        const corFormatada = cor.toLowerCase().trim();

        if (corFormatada === 'vermelho') {
            return numerosVermelhos;
        } else if (corFormatada === 'preto') {
            return numerosPretos;
        } else {
            console.error("Cor inválida. Por favor, insira 'vermelho' ou 'preto'.");
            return null;
        }
    }

    function getNumerosColunaRoleta(coluna) {
        if (coluna < 1 || coluna > 3) {
            console.error("Número de coluna inválido. Por favor, insira 1, 2 ou 3.");
            return null;
        }

        const numerosColuna = [];

        for (let i = coluna; i <= 36; i += 3) {
            numerosColuna.push(i);
        }

        return numerosColuna;
    }

    function findInputApostaDetalhe(tipoApostaSelecionado)
    {
        const inputDetalheId = betTypeMap[tipoApostaSelecionado];
        const $inputDetalheElement = $('#' + inputDetalheId);
        let $actualInputElement = $inputDetalheElement.find('input, select').first();

        if ($actualInputElement.length === 0 && ($inputDetalheElement.is('input') || $inputDetalheElement.is('select'))) {
            $actualInputElement = $inputDetalheElement;
        }

        return $actualInputElement;
    }

    $('#bet-type-select').on('change', function() {
        const selectedValue = $(this).val();
        toggleBetInputs(selectedValue);
    });

    const initialSelectedValue = $('#bet-type-select').val();
    toggleBetInputs(initialSelectedValue);

    $('#btn-criar-aposta').click(function (){
        $("#roleta-message").text("").hide();

        const valorAposta = $('#valor-aposta-input').val();

        if (!valorAposta || isNaN(valorAposta) || parseFloat(valorAposta) <= 0) {
            $("#roleta-message").text("Por favor, insira um valor de aposta válido.").show();
            return;
        }

        const valorApostaNum = parseFloat(valorAposta);

        if(valorApostaNum > SALDO_USUARIO){
            $("#roleta-message").text("Saldo insuficiente! Seu saldo atual: R$ " + SALDO_USUARIO.toFixed(2)).show();
            return;
        }

        const tipoApostaSelecionado = $('#bet-type-select').val();
        let valorApostaDetalhe = null;

        if (tipoApostaSelecionado && betTypeMap[tipoApostaSelecionado]) {
            const $actualInputElement = findInputApostaDetalhe(tipoApostaSelecionado);

            if ($actualInputElement.length > 0) {
                valorApostaDetalhe = $actualInputElement.val();
                if (tipoApostaSelecionado === 'aposta_direta' && (!valorApostaDetalhe || isNaN(valorApostaDetalhe))) {
                    $("#roleta-message").text("Por favor, escolha um número para a Aposta Direta.").show();
                    return;
                }
                if (tipoApostaSelecionado !== 'aposta_direta' && !valorApostaDetalhe) {
                    $("#roleta-message").text("Por favor, selecione uma opção para o tipo de aposta.").show();
                    return;
                }
            } else {
                $("#roleta-message").text("Erro interno: campo de aposta detalhada não encontrado.").show();
                return;
            }
        } else {
            $("#roleta-message").text("Por favor, selecione um tipo de aposta.").show();
            return;
        }

        dadosParaServlet = {
            valor: valorApostaNum,
            resultado: "PENDENTE",
            dataAposta: new Date(),
            tipoAposta: tipoApostaSelecionado + (valorApostaDetalhe ? ": " + valorApostaDetalhe : ""),
            jogoId: 1,
            usuarioId: USUARIO_ID
        };

        $('#spin').removeClass(HIDE_CLASS);
    });
});