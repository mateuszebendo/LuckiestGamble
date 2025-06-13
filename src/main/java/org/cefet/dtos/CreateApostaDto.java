package org.cefet.dtos;

import java.util.Date;

public class CreateApostaDto {
    private double Valor;
    private Date DataAposta;
    private String Resultado;
    private String TipoAposta;
    private long UsuarioId;
    private long JogoId;

    public CreateApostaDto() {
    }

    public CreateApostaDto(double valor, String resultado, String tipoAposta, long usuarioId, long jogoId) {
        Valor = valor;
        DataAposta = new Date();
        Resultado = resultado;
        TipoAposta = tipoAposta;
        UsuarioId = usuarioId;
        JogoId = jogoId;
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
}
