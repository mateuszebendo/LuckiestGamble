package org.cefet.dtos;

import org.cefet.models.ApostaModel;
import org.cefet.models.JogoModel;
import org.cefet.models.UsuarioModel;

import java.util.Date;

public class ResponseApostaDto {
    private long ApostaId;
    private double Valor;
    private Date DataAposta;
    private String Resultado;
    private String TipoAposta;
    private long UsuarioId;
    private long JogoId;

    private UsuarioModel Usuario;
    private JogoModel Jogo;

    public ResponseApostaDto() {}

    public ResponseApostaDto(ApostaModel aposta) {
        ApostaId = aposta.getApostaId();
        Valor = aposta.getValor();
        DataAposta = aposta.getDataAposta();
        Resultado = aposta.getResultado();
        TipoAposta = aposta.getTipoAposta();
        UsuarioId = aposta.getUsuarioId();
        JogoId = aposta.getJogoId();
        Usuario = aposta.getUsuario();
        Jogo = aposta.getJogo();
    }

    public long getApostaId() {
        return ApostaId;
    }

    public void setApostaId(long apostaId) {
        ApostaId = apostaId;
    }

    public double getValor() {
        return Valor;
    }

    public void setValor(double valor) {
        Valor = valor;
    }

    public Date getDataAposta() {
        return DataAposta;
    }

    public void setDataAposta(Date dataAposta) {
        DataAposta = dataAposta;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }

    public String getTipoAposta() {
        return TipoAposta;
    }

    public void setTipoAposta(String tipoAposta) {
        TipoAposta = tipoAposta;
    }

    public long getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        UsuarioId = usuarioId;
    }

    public long getJogoId() {
        return JogoId;
    }

    public void setJogoId(long jogoId) {
        JogoId = jogoId;
    }

    public UsuarioModel getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        Usuario = usuario;
    }

    public JogoModel getJogo() {
        return Jogo;
    }

    public void setJogo(JogoModel jogo) {
        Jogo = jogo;
    }
}
