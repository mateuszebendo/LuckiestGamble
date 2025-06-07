package org.cefet.dtos;

import org.cefet.enums.StatusTransacao;
import org.cefet.enums.TipoTransacao;

import java.util.Date;

public class CreateTransacaoDto {
    private TipoTransacao TipoTransacao;
    private Double Valor;
    private String Descricao;
    private StatusTransacao Status;
    private long UsuarioId;

    public CreateTransacaoDto() {
    }

    public TipoTransacao getTipoTransacao() {
        return TipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        TipoTransacao = tipoTransacao;
    }

    public Double getValor() {
        return Valor;
    }

    public void setValor(Double valor) {
        Valor = valor;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public StatusTransacao getStatus() {
        return Status;
    }

    public void setStatus(StatusTransacao status) {
        Status = status;
    }

    public long getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        UsuarioId = usuarioId;
    }
}
