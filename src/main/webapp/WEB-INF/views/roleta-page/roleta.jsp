<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layouts" prefix="my-l"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>

<my-l:_baseLayout pageTitle="Home" scriptFiles="${pageScripts}" styleFiles="${pageStyles}">
    <main id="roleta-main-container">
        <%@include file="/WEB-INF/shared/sideBar.jspf" %>
        <div class="roleta-sub-container">
            <div id="roleta-canvas">
                <h1>Gire a Roleta!</h1>
                <canvas id="canvas" width="500" height="500"></canvas>
                <div id="roleta-options-container">
                    <my-c:dinamicSelect id="bet-type-select-container" selectId="bet-type-select" name="apostas" options="${roleta.odds.keySet()}" label="Aposta: "/>
                    <my-c:dinamicApostaGroup/>
                    <button id="spin">GIRAR</button>
                    <div id="result" style="display: none"></div>
                </div>
            </div>
        </div>
    </main>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script> <%-- Inclua jQuery aqui se não estiver no _baseLayout --%>
    <script>
        // Garante que o DOM esteja totalmente carregado antes de executar o script
        $(document).ready(function() {
            var options = ["0", "32", "15", "19", "4", "21", "2", "25", "17", "34", "6", "27", "13", "36", "11", "30", "8", "23", "10", "5", "24", "16", "33", "1", "20", "14", "31", "9", "22", "18", "29", "7", "28", "12", "35", "3", "26"];
            var startAngle = 0;
            var arc = Math.PI / (options.length / 2);
            var spinTimeout = null;
            var spinArcStart = 10;
            var spinTime = 0;
            var spinTimeTotal = 0;
            var ctx;

            // Pega um número (um componente de cor, de 0 a 255) e o converte para sua representação hexadecimal de dois dígitos.
            function byte2Hex(n) {
                var nybHexString = "0123456789ABCDEF";
                return String(nybHexString.substr((n >> 4) & 0x0F,1)) + nybHexString.substr(n & 0x0F,1);
            }

            // Recebe os três componentes RGB e os une, usando byte2Hex para cada um, para formar a string de cor hexadecimal completa.
            function RGB2Color(r,g,b) {
                return '#' + byte2Hex(r) + byte2Hex(g) + byte2Hex(b);
            }

            // ---
            // ### Função `getColor` para Roleta Europeia
            // Esta função foi modificada para aplicar as cores tradicionais da roleta europeia:
            // - O número "0" (zero) é sempre verde.
            // - Os outros números (1-36) alternam entre vermelho e preto.
            // ---
            function getColor(item) {
                var value = options[item]; // Obtém o valor do número na fatia atual.

                // Se o valor for "0", a cor é verde.
                if (value === "0") {
                    return "#006400"; // Verde escuro para o zero.
                }

                // Converte o valor para um número inteiro para determinar a cor (par/ímpar).
                var num = parseInt(value);

                // Determina se o número é par ou ímpar (exceto o zero, já tratado).
                // Para roletas europeias, a sequência de cores é baseada nos números.
                // Os números em vermelho são: 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36
                // Os números em preto são: 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35
                // É mais robusto definir a cor baseada no próprio número, em vez do índice, pois a ordem no array pode variar.
                var redNumbers = [1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36];

                if (redNumbers.includes(num)) {
                    return "#FF0000"; // Vermelho
                } else {
                    return "#000000"; // Preto
                }
            }

            // ---
            // ### Função `drawRouletteWheel()`
            // Esta é a função central que desenha a roleta no elemento `<canvas>`.
            // Ela é chamada repetidamente durante o giro para atualizar a visualização.
            // ---
            function drawRouletteWheel() {
                var canvas = document.getElementById("canvas");
                if (canvas.getContext) {
                    // Define os raios dos círculos que compõem a roleta.
                    var outsideRadius = 200;
                    var textRadius = 160;
                    var insideRadius = 125;

                    // Obtém o contexto de desenho 2D do canvas.
                    ctx = canvas.getContext("2d");
                    ctx.clearRect(0,0,500,500); // Limpa todo o conteúdo anterior do canvas antes de redesenhar a roleta.

                    // Define o estilo da borda das fatias.
                    ctx.strokeStyle = "white"; // Borda branca para as fatias.
                    ctx.lineWidth = 1; // Largura da borda.

                    // Define a fonte e o estilo do texto dos números.
                    ctx.font = 'bold 20px Arial'; // Fonte para o texto das opções, aumentada para melhor visibilidade.

                    // Loop para desenhar cada fatia da roleta.
                    for(var i = 0; i < options.length; i++) {
                        var angle = startAngle + i * arc; // Calcula o ângulo inicial da fatia.
                        ctx.fillStyle = getColor(i); // Usa a função `getColor` para preencher a fatia com a cor correta (verde, vermelho ou preto).

                        // Inicia um novo caminho de desenho.
                        ctx.beginPath();
                        // Desenha o arco externo da fatia.
                        ctx.arc(250, 250, outsideRadius, angle, angle + arc, false);
                        // Desenha o arco interno da fatia, conectando-o ao externo para formar a fatia.
                        ctx.arc(250, 250, insideRadius, angle + arc, angle, true);
                        ctx.stroke(); // Desenha a borda da fatia.
                        ctx.fill();   // Preenche a fatia com a cor definida.

                        // Desenha o texto (número) da opção no centro da fatia.
                        ctx.save(); // Salva o estado atual do contexto (para isolar transformações).
                        ctx.shadowOffsetX = 1; // Deslocamento da sombra no eixo X.
                        ctx.shadowOffsetY = 1; // Deslocamento da sombra no eixo Y.
                        ctx.shadowBlur    = 2; // Intensidade do desfoque da sombra.
                        ctx.shadowColor   = "rgba(0,0,0,0.5)"; // Cor da sombra (preto com 50% de opacidade).

                        // Define a cor do texto. Para a roleta europeia, números pretos em fatias vermelhas, e números brancos em fatias pretas e verdes.
                        if (options[i] === "0" || getColor(i) === "#000000") { // Se for o zero ou uma fatia preta
                            ctx.fillStyle = "white"; // Cor do texto branca.
                        } else {
                            ctx.fillStyle = "black"; // Cor do texto preta para fatias vermelhas.
                        }


                        // Move o ponto de origem para o centro da roleta para facilitar a rotação do texto.
                        ctx.translate(250 + Math.cos(angle + arc / 2) * textRadius,
                            250 + Math.sin(angle + arc / 2) * textRadius);
                        // Gira o texto para que ele se alinhe com a fatia.
                        ctx.rotate(angle + arc / 2 + Math.PI / 2);
                        var text = options[i]; // Obtém o texto (número) da opção atual.
                        // Desenha o texto centralizado na fatia.
                        ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
                        ctx.restore(); // Restaura o estado anterior do contexto.
                    }

                    // ---
                    // ### Desenha a Flecha Indicadora
                    // Esta seção desenha a flecha que aponta para o número sorteado.
                    // ---
                    ctx.fillStyle = "gold"; // Cor da flecha para combinar com o tema do cassino.
                    ctx.beginPath();
                    // Desenha os pontos da flecha para formar sua forma triangular.
                    ctx.moveTo(250 - 10, 250 - (outsideRadius + 15)); // Base esquerda
                    ctx.lineTo(250 + 10, 250 - (outsideRadius + 15)); // Base direita
                    ctx.lineTo(250 + 10, 250 - (outsideRadius - 5));  // Lado direito inferior
                    ctx.lineTo(250 + 20, 250 - (outsideRadius - 5));  // Ponta externa direita
                    ctx.lineTo(250 + 0, 250 - (outsideRadius - 25)); // Ponta superior
                    ctx.lineTo(250 - 20, 250 - (outsideRadius - 5));  // Ponta externa esquerda
                    ctx.lineTo(250 - 10, 250 - (outsideRadius - 5));  // Lado esquerdo inferior
                    ctx.lineTo(250 - 10, 250 - (outsideRadius + 15)); // Volta para a base esquerda.
                    ctx.fill(); // Preenche a flecha com a cor definida.

                    // Desenha um pequeno círculo central para o "miolo" da roleta, tipicamente dourado.
                    ctx.beginPath();
                    ctx.arc(250, 250, insideRadius - 10, 0, Math.PI * 2, true); // Círculo interno um pouco menor.
                    ctx.fillStyle = "darkred"; // Cor para o miolo.
                    ctx.fill();
                    ctx.stroke(); // Borda para o miolo.

                    // Adiciona um pequeno pino no centro para a bolinha.
                    ctx.beginPath();
                    ctx.arc(250, 250, insideRadius - 40, 0, Math.PI * 2, true);
                    ctx.fillStyle = "silver"; // Cor para o pino central.
                    ctx.fill();
                    ctx.lineWidth = 1;
                    ctx.strokeStyle = "gray";
                    ctx.stroke();

                }
            }

            // ---
            // ### Função `spin()`
            // Esta função é acionada quando o botão "spin" é clicado.
            // ---
            function spin() {
                // Desabilita o botão para evitar múltiplos giros enquanto a roleta está em movimento.
                document.getElementById("spin").disabled = true;
                document.getElementById("result").innerHTML = ""; // Limpa qualquer resultado anterior exibido.

                // Define o ângulo inicial aleatório do giro para cada rotação.
                spinAngleStart = Math.random() * 10 + 10;
                spinTime = 0; // Zera o contador de tempo do giro.
                // Define a duração total do giro (entre 4 e 7 segundos), tornando cada giro único.
                spinTimeTotal = Math.random() * 3000 + 4000;
                rotateWheel(); // Inicia a animação de giro.
            }

            // ---
            // ### Função `rotateWheel()`
            // Esta função é o coração da animação do giro. Ela é chamada recursivamente
            // através de `setTimeout` para criar a sequência de movimento.
            // ---
            function rotateWheel() {
                spinTime += 30; // Incrementa o tempo decorrido do giro em milissegundos.
                // Verifica se o tempo total de giro foi atingido. Se sim, para a roleta.
                if(spinTime >= spinTimeTotal) {
                    stopRotateWheel();
                    return;
                }
                // Calcula o ângulo de giro atual usando a função `easeOut`, que cria uma desaceleração suave.
                var spinAngle = spinAngleStart - easeOut(spinTime, 0, spinAngleStart, spinTimeTotal);
                startAngle += (spinAngle * Math.PI / 180); // Atualiza o ângulo de início da roleta, fazendo-a girar.
                drawRouletteWheel(); // Redesenha a roleta com o novo ângulo, criando a ilusão de movimento.
                spinTimeout = setTimeout(rotateWheel, 30); // Agenda a próxima chamada de `rotateWheel()` após 30ms.
            }

            // ---
            // ### Função `stopRotateWheel()`
            // Esta função é chamada quando o giro da roleta termina.
            // Ela determina qual opção foi sorteada e exibe o resultado.
            // ---
            function stopRotateWheel() {
                clearTimeout(spinTimeout); // Interrompe o timer, parando as chamadas recursivas de `rotateWheel()`.

                // Converte o `startAngle` (em radianos) para graus e ajusta para o cálculo da fatia.
                var degrees = startAngle * 180 / Math.PI + 90;
                // Calcula a largura de cada arco (fatia) em graus.
                var arcd = arc * 180 / Math.PI;
                // Calcula o índice da opção sorteada.
                // A lógica (360 - degrees % 360) / arcd garante que a flecha aponte para a fatia correta.
                var index = Math.floor((360 - degrees % 360) / arcd);

                ctx.save(); // Salva o estado do contexto.
                ctx.font = 'bold 30px Arial'; // Define a fonte para o texto do resultado.
                var text = options[index]; // Obtém o texto (número) da opção sorteada.

                // Define a cor do texto do resultado no centro da roleta.
                if (text === "0" || getColor(index) === "#000000") { // Se for o zero ou uma fatia preta
                    ctx.fillStyle = "white"; // Cor do texto branca.
                } else {
                    ctx.fillStyle = "black"; // Cor do texto preta para fatias vermelhas.
                }

                // Exibe o texto da opção sorteada no centro da roleta.
                ctx.fillText(text, 250 - ctx.measureText(text).width / 2, 250 + 10);
                ctx.restore(); // Restaura o estado anterior do contexto.

                // Exibe o resultado também na div HTML com o ID "result".
                document.getElementById("result").innerHTML = `Você ganhou: ${text}!`;
                document.getElementById("spin").disabled = false; // Reabilita o botão "spin" para permitir um novo giro.
            }

            // ---
            // ### Função `easeOut(t, b, c, d)`
            // Esta é uma função de **easing** (atenuação), especificamente `easeOutCubic`.
            // Ela controla a velocidade da animação, fazendo com que a roleta comece rápido e desacelere suavemente.
            // `t`: tempo atual, `b`: valor inicial, `c`: mudança no valor, `d`: duração total.
            // ---
            function easeOut(t, b, c, d) {
                var ts = (t/=d)*t;
                var tc = ts*t;
                return b+c*(tc + -3*ts + 3*t);
            }

            // Desenha a roleta pela primeira vez ao carregar a página, garantindo que ela seja visível imediatamente.
            drawRouletteWheel();

            // Adiciona um event listener para o clique no botão de girar
            $('#spin').click(function(){
                spin();
            });
        });
    </script>
</my-l:_baseLayout>