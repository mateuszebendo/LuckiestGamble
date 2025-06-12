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
        $('#bet-type-select-container').removeClass(HIDE_CLASS);

        if (selectedBetType && betTypeMap[selectedBetType]) {
            const targetId = betTypeMap[selectedBetType];
            $('#' + targetId).removeClass(HIDE_CLASS);
        }
    }

    $('#bet-type-select').on('change', function() {
        const selectedValue = $(this).val();
        toggleBetInputs(selectedValue);
    });

    const initialSelectedValue = $('#bet-type-select').val();
    toggleBetInputs(initialSelectedValue);
});