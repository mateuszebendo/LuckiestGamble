<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layouts" prefix="my-l"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>

<my-l:_baseLayout pageTitle="Home" scriptFiles="${pageScripts}" styleFiles="${pageStyles}">
    <main id="roleta-main-container">
        <%@include file="/WEB-INF/shared/sideBar.jspf" %>
        <div class="roleta-sub-container">
            <div id="roleta-canvas">
                <h1>Gire a Roleta da Sorte!</h1>
                <canvas id="canvas" width="500" height="500"></canvas>
                <button id="spin">GIRAR</button>
                <div id="result"></div>
            </div>
        </div>
    </main>
    <script>
        var options = ["$100", "$10", "$25", "$250", "$30", "$1000", "$1", "$200", "$45", "$500", "$5", "$20", "Lose", "$1000000", "Lose", "$350", "$5", "$99"];

        var startAngle = 0;
        var arc = Math.PI / (options.length / 2);
        var spinTimeout = null;

        var spinArcStart = 10;
        var spinTime = 0;
        var spinTimeTotal = 0;

        var ctx;

        // O evento do botão já está no seu script, então apenas garanta que o ID "spin" esteja no HTML.
        document.getElementById("spin").addEventListener("click", spin);

        function byte2Hex(n) {
            var nybHexString = "0123456789ABCDEF";
            return String(nybHexString.substr((n >> 4) & 0x0F,1)) + nybHexString.substr(n & 0x0F,1);
        }

        function RGB2Color(r,g,b) {
            return '#' + byte2Hex(r) + byte2Hex(g) + byte2Hex(b);
        }

        // Esta função gera cores dinâmicas para as fatias
        function getColor(item, maxitem) {
            var phase = 0;
            var center = 128;
            var width = 127;
            var frequency = Math.PI*2/maxitem;

            red   = Math.sin(frequency*item+2+phase) * width + center;
            green = Math.sin(frequency*item+0+phase) * width + center;
            blue  = Math.sin(frequency*item+4+phase) * width + center;

            return RGB2Color(red,green,blue);
        }

        function drawRouletteWheel() {
            var canvas = document.getElementById("canvas");
            if (canvas.getContext) {
                var outsideRadius = 200;
                var textRadius = 160;
                var insideRadius = 125;

                ctx = canvas.getContext("2d");
                ctx.clearRect(0,0,500,500); // Limpa o canvas

                ctx.strokeStyle = "black";
                ctx.lineWidth = 2;

                ctx.font = 'bold 12px Helvetica, Arial'; // Fonte para o texto das opções

                for(var i = 0; i < options.length; i++) {
                    var angle = startAngle + i * arc;
                    ctx.fillStyle = getColor(i, options.length); // Usa a função getColor para preencher

                    ctx.beginPath();
                    ctx.arc(250, 250, outsideRadius, angle, angle + arc, false);
                    ctx.arc(250, 250, insideRadius, angle + arc, angle, true);
                    ctx.stroke();
                    ctx.fill();

                    // Desenha o texto da opção
                    ctx.save();
                    ctx.shadowOffsetX = -1;
                    ctx.shadowOffsetY = -1;
                    ctx.shadowBlur    = 0;
                    ctx.shadowColor   = "rgb(220,220,220)";
                    ctx.fillStyle = "black";
                    ctx.translate(250 + Math.cos(angle + arc / 2) * textRadius,
                        250 + Math.sin(angle + arc / 2) * textRadius);
                    ctx.rotate(angle + arc / 2 + Math.PI / 2);
                    var text = options[i];
                    ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
                    ctx.restore();
                }

                // Desenha a flecha indicadora
                ctx.fillStyle = "black";
                ctx.beginPath();
                ctx.moveTo(250 - 4, 250 - (outsideRadius + 5));
                ctx.lineTo(250 + 4, 250 - (outsideRadius + 5));
                ctx.lineTo(250 + 4, 250 - (outsideRadius - 5));
                ctx.lineTo(250 + 9, 250 - (outsideRadius - 5));
                ctx.lineTo(250 + 0, 250 - (outsideRadius - 13));
                ctx.lineTo(250 - 9, 250 - (outsideRadius - 5));
                ctx.lineTo(250 - 4, 250 - (outsideRadius - 5));
                ctx.lineTo(250 - 4, 250 - (outsideRadius + 5));
                ctx.fill();
            }
        }

        function spin() {
            // Desabilita o botão para evitar múltiplos giros
            document.getElementById("spin").disabled = true;
            document.getElementById("result").innerHTML = ""; // Limpa o resultado anterior

            spinAngleStart = Math.random() * 10 + 10; // Ângulo inicial de giro
            spinTime = 0;
            spinTimeTotal = Math.random() * 3000 + 4000; // Duração total do giro (4-7 segundos)
            rotateWheel();
        }

        function rotateWheel() {
            spinTime += 30; // Incrementa o tempo do giro
            if(spinTime >= spinTimeTotal) {
                stopRotateWheel();
                return;
            }
            // Calcula o ângulo de giro usando a função easeOut
            var spinAngle = spinAngleStart - easeOut(spinTime, 0, spinAngleStart, spinTimeTotal);
            startAngle += (spinAngle * Math.PI / 180); // Atualiza o ângulo de início da roleta
            drawRouletteWheel(); // Redesenha a roleta
            spinTimeout = setTimeout('rotateWheel()', 30); // Chama a próxima rotação após 30ms
        }

        function stopRotateWheel() {
            clearTimeout(spinTimeout); // Para o giro
            var degrees = startAngle * 180 / Math.PI + 90; // Converte radianos para graus
            var arcd = arc * 180 / Math.PI; // Largura de cada arco em graus
            // Calcula o índice da opção sorteada
            var index = Math.floor((360 - degrees % 360) / arcd);

            ctx.save();
            ctx.font = 'bold 30px Helvetica, Arial';
            var text = options[index]; // Obtém o texto da opção sorteada

            // Exibe o texto da opção sorteada no centro da roleta
            ctx.fillText(text, 250 - ctx.measureText(text).width / 2, 250 + 10);
            ctx.restore();

            // Exibe o resultado também na div "result"
            document.getElementById("result").innerHTML = `Você ganhou: ${text}!`;
            document.getElementById("spin").disabled = false; // Habilita o botão novamente
        }

        // Função de easing para um movimento de parada mais natural
        function easeOut(t, b, c, d) {
            var ts = (t/=d)*t;
            var tc = ts*t;
            return b+c*(tc + -3*ts + 3*t);
        }

        // Desenha a roleta pela primeira vez ao carregar a página
        drawRouletteWheel();

        // SEU CÓDIGO JAVASCRIPT TERMINA AQUI
    </script>
</my-l:_baseLayout>